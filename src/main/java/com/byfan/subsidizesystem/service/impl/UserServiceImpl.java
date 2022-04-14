package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.common.StatusEnum;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryUserForm;
import com.byfan.subsidizesystem.model.UserEntity;
import com.byfan.subsidizesystem.dao.UserDao;
import com.byfan.subsidizesystem.service.UserService;
import com.byfan.subsidizesystem.utils.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Description User ServiceImpl层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	/**
	 * 新增/保存
	 * @param user
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public UserEntity save(UserEntity user) throws SubsidizeSystemException {
		List<UserEntity> byUserName = userDao.findByUserName(user.getUserName());
		if (!CollectionUtils.isEmpty(byUserName)){
			if (user.getId() == null || user.getId() != byUserName.get(0).getId()){
				log.error("save user the userName is exist");
				throw new SubsidizeSystemException(CommonResponse.REPEATED_RESOURCE_NAME,"该名称已经存在");
			}
		}
		if (user.getId() != null){
			Optional<UserEntity> byId = userDao.findById(user.getId());
			if (byId.isPresent()){
				UserEntity u = byId.get();
				MyUtils.copyPropertiesIgnoreNull(user,u);
				user = u;
			}else {
				log.error("save user is resource not exist!");
				throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST);
			}
		}
		user.setStatus(StatusEnum.USING.code);
		return userDao.save(user);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @throws SubsidizeSystemException
	 */
	@Override
	public void deleteById(Integer id) throws SubsidizeSystemException{
		if (id == null){
			log.error("deleteById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"id is null!");
		}
		UserEntity byId = getById(id);
		if (byId == null || byId.getStatus() == StatusEnum.DELETED.code){
			log.info("deleteById user is not exist!");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"用户不存在");
		}
		userDao.updateStatus(id, StatusEnum.DELETED.code);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public PageData<UserEntity> findByQuery(QueryUserForm queryUserForm) throws SubsidizeSystemException {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("userName", match -> match.contains())
				.withMatcher("displayName",match -> match.contains())
				.withMatcher("role",match -> match.equals(queryUserForm.getRole()))
				.withMatcher("gender",match -> match.equals(queryUserForm.getGender()))
				.withMatcher("telephone",match -> match.contains())
				.withMatcher("email", match -> match.contains())
				.withMatcher("address",match -> match.contains())
				.withMatcher("status",match -> match.equals(StatusEnum.USING.code))
				.withIgnoreCase()               // 忽略大小写
				.withIgnoreNullValues();        // 忽略空字段
		UserEntity user = new UserEntity();
		user.setUserName(queryUserForm.getUserName());
		user.setDisplayName(queryUserForm.getDisplayName());
		user.setRole(queryUserForm.getRole());
		user.setGender(queryUserForm.getGender());
		user.setTelephone(queryUserForm.getTelephone());
		user.setEmail(queryUserForm.getEmail());
		user.setAddress(queryUserForm.getAddress());
		user.setStatus(StatusEnum.USING.code);

		Example<UserEntity> example = Example.of(user,matcher);
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		queryUserForm.setCurrentPage(queryUserForm.getCurrentPage() <=0 ? 1 : queryUserForm.getCurrentPage());
		queryUserForm.setPageSize(queryUserForm.getPageSize() <=0 ? 20 : queryUserForm.getPageSize());
		Pageable pageable = PageRequest.of(queryUserForm.getCurrentPage() - 1, queryUserForm.getPageSize(), sort);
		Page<UserEntity> all = userDao.findAll(example, pageable);

		PageData<UserEntity> pageData = new PageData<>();
		pageData.setCurrentPage(queryUserForm.getCurrentPage());
		pageData.setPageSize(queryUserForm.getPageSize());
		pageData.setTotal((int) all.getTotalElements());
		pageData.setTotalPage(all.getTotalPages());
		pageData.setList(all.getContent());

		return pageData;
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public UserEntity getById(Integer id) throws SubsidizeSystemException{
		if (id == null){
			log.error("getById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getById id is null!");
		}
		Optional<UserEntity> optional = userDao.findById(id);
		if (!optional.isPresent() || optional.get().getStatus() == StatusEnum.DELETED.code){
			log.info("getById user is not exist!");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"用户不存在");
		}
		return optional.orElse(null);
	}

	/**
	 * @param id
	 * @return com.byfan.subsidizesystem.model.UserEntity
	 * @throws SubsidizeSystemException
	 * @Description 根据id查询删除和未删除的
	 * @Author byfan
	 * @Date 2022/4/10 17:16
	 */
	@Override
	public UserEntity getAllById(Integer id) throws SubsidizeSystemException {
		if (id == null){
			log.error("getAllById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getAllById id is null!");
		}
		return userDao.findAllById(id);
	}

	/**
	 * @Description 用户登录
	 * @Author byfan
	 * @Date 2022/4/8 15:28
	 * @param userName
	 * @param passwd
	 * @return void
	 * @throws
	 */
	@Override
	public UserEntity login(String userName, String passwd) throws SubsidizeSystemException {
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(passwd)) {
			log.error("login userName or passwd is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"userName or passwd is null!");
		}
		List<UserEntity> byUserName = userDao.findByUserName(userName);
		if (CollectionUtils.isEmpty(byUserName) || byUserName.get(0) == null){
			log.info("login 用户不存在");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"用户不存在");
		}else {
			if (!byUserName.get(0).getPassword().equals(passwd)){
				log.info("login 密码错误");
				throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"密码错误");
			}
		}
		return byUserName.get(0);
	}

	/**
	 * @Description 找回密码
	 * @Author byfan
	 * @Date 2022/4/8 15:42
	 * @param userName
	 * @param telephone
	 * @return com.byfan.subsidizesystem.model.UserEntity
	 * @throws
	 */
	@Override
	public UserEntity retrievePasswd(String userName, String telephone) throws SubsidizeSystemException {
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(telephone)){
			log.error("retrievePasswd userName or telephone or approveIdentity is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"userName or telephone or approveIdentity is null");
		}
		List<UserEntity> byUserName = userDao.findByUserName(userName);
		if (CollectionUtils.isEmpty(byUserName) || !byUserName.get(0).getTelephone().equals(telephone)){
			log.info("retrievePasswd user is not exist!");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"用户不存在");
		}
		return byUserName.get(0);
	}

	/**
	 * @param displayName
	 * @return java.util.List<com.byfan.subsidizesystem.model.UserEntity>
	 * @throws SubsidizeSystemException
	 * @Description 根据昵称查询用户
	 * @Author byfan
	 * @Date 2022/4/9 18:11
	 */
	@Override
	public List<UserEntity> findByDisplayName(String displayName) throws SubsidizeSystemException {
		if (StringUtils.isBlank(displayName)){
			log.error("findByDisplayName displayName is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"displayName is null");
		}
		return userDao.findByDisplayName(displayName);
	}

}