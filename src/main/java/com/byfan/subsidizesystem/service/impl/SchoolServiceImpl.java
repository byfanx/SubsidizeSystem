package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.StatusEnum;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.SchoolEntity;
import com.byfan.subsidizesystem.dao.SchoolDao;
import com.byfan.subsidizesystem.model.UserEntity;
import com.byfan.subsidizesystem.service.SchoolService;
import com.byfan.subsidizesystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description School ServiceImpl层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Service
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	private SchoolDao schoolDao;

	@Autowired
	private UserService userService;

	/**
	 * 新增/保存
	 * @param school
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public SchoolEntity save(SchoolEntity school) throws SubsidizeSystemException {
		return schoolDao.save(school);
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
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"deleteById id is null!");
		}
		schoolDao.deleteById(id);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public List<SchoolEntity> getAll() throws SubsidizeSystemException {
		return schoolDao.findAll();
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public SchoolEntity getById(Integer id) throws SubsidizeSystemException{
		if (id == null){
			log.error("getById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getById id is null!");
		}
		SchoolEntity school = getAllById(id);
		if (null == school || school.getStatus() == StatusEnum.DELETED.code){
			log.error("getById school is not exist");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"学校对象不存在");
		}
		return school;
	}

	/**
	 * @param name
	 * @return java.util.List<com.byfan.subsidizesystem.model.SchoolEntity>
	 * @throws SubsidizeSystemException
	 * @Description 根据名称查询学校
	 * @Author byfan
	 * @Date 2022/4/9 18:11
	 */
	@Override
	public List<SchoolEntity> findByName(String name) throws SubsidizeSystemException {
		if (StringUtils.isBlank(name)){
			log.error("findByName name is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"name is null");
		}
		return schoolDao.findByName(name);
	}

	/**
	 * @param id
	 * @return com.byfan.subsidizesystem.model.SchoolEntity
	 * @throws SubsidizeSystemException
	 * @Description 根据id查询删除和未删除的学校
	 * @Author byfan
	 * @Date 2022/4/10 17:34
	 */
	@Override
	public SchoolEntity getAllById(Integer id) throws SubsidizeSystemException {
		if (id == null){
			log.error("getAllById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getAllById id is null!");
		}
		Optional<SchoolEntity> optional = schoolDao.findById(id);
		SchoolEntity school = null;
		if (optional.isPresent()){
			school = optional.get();
			UserEntity user = userService.getAllById(school.getUserId());
			school.setUserEntity(user);
		}
		return school;
	}

}