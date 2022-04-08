package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.common.StatusEnum;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryUserForm;
import com.byfan.subsidizesystem.model.UserEntity;
import com.byfan.subsidizesystem.dao.UserDao;
import com.byfan.subsidizesystem.service.UserService;
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
				user = byId.get();
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
		Pageable pageable = PageRequest.of(queryUserForm.getCurrentPage() - 1, queryUserForm.getPageSize(), sort);
		Page<UserEntity> all = userDao.findAll(example, pageable);

		PageData<UserEntity> pageData = new PageData<>();
		pageData.setCurrentPage(queryUserForm.getCurrentPage());
		pageData.setPageSize(queryUserForm.getPageSize());
		pageData.setTotal(pageData.getTotal());
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
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(passwd)){
			log.error("login userName or passwd is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"userName or passwd is null!");
		}
		List<UserEntity> byUserName = userDao.findByUserName(userName);
		if (!CollectionUtils.isEmpty(byUserName)){
			if (byUserName.get(0) == null || !byUserName.get(0).getPassword().equals(passwd)){
				log.info("login 密码错误");
				throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"密码错误");
			}
		}else {
			log.info("login 用户不存在");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"用户不存在");
		}
		return byUserName.get(0);
	}

	/**
	 * @Description 找回密码
	 * @Author byfan
	 * @Date 2022/4/8 15:42
	 * @param userName
	 * @return com.byfan.subsidizesystem.model.UserEntity
	 * @throws
	 */
	@Override
	public UserEntity retrievePasswd(String userName) throws SubsidizeSystemException {
		if (StringUtils.isBlank(userName)){
			log.error("retrievePasswd userName is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"userName is null");
		}
		List<UserEntity> byUserName = userDao.findByUserName(userName);
		if (CollectionUtils.isEmpty(byUserName)){
			log.info("retrievePasswd user is not exist!");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"用户不存在");
		}
		return byUserName.get(0);
	}

}