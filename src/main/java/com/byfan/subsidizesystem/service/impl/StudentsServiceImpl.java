package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.StatusEnum;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.StudentsEntity;
import com.byfan.subsidizesystem.dao.StudentsDao;
import com.byfan.subsidizesystem.model.UserEntity;
import com.byfan.subsidizesystem.service.StudentsService;
import com.byfan.subsidizesystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description Students ServiceImpl层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Service
public class StudentsServiceImpl implements StudentsService {

	@Autowired
	private StudentsDao studentsDao;

	@Autowired
	private UserService userService;

	/**
	 * 新增/保存
	 * @param students
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public StudentsEntity save(StudentsEntity students) throws SubsidizeSystemException {
		return studentsDao.save(students);
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
		studentsDao.deleteById(id);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public List<StudentsEntity> getAll() throws SubsidizeSystemException {
		List<StudentsEntity> all = studentsDao.findAll();
//		for (StudentsEntity stu : all){
//
//		}

		return all;
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public StudentsEntity getById(Integer id) throws SubsidizeSystemException{
		if (id == null){
			log.error("getById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getById id is null!");
		}
		StudentsEntity entity = getAllById(id);
		if (null == entity || entity.getStatus() == StatusEnum.DELETED.code){
			log.info("getById student is not exist!");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"学生不存在");
		}
		return entity;
	}

	/**
	 * @param name
	 * @return java.util.List<com.byfan.subsidizesystem.model.StudentsEntity>
	 * @throws SubsidizeSystemException
	 * @Description 根据名称查询学生
	 * @Author byfan
	 * @Date 2022/4/9 18:11
	 */
	@Override
	public List<StudentsEntity> findByName(String name) throws SubsidizeSystemException {
		if (StringUtils.isBlank(name)){
			log.error("findByName name is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"name is null");
		}
		return studentsDao.findByName(name);
	}

	/**
	 * @param id
	 * @return com.byfan.subsidizesystem.model.StudentsEntity
	 * @throws SubsidizeSystemException
	 * @Description 根据id查询删除和未删除的学生
	 * @Author byfan
	 * @Date 2022/4/10 17:30
	 */
	@Override
	public StudentsEntity getAllById(Integer id) throws SubsidizeSystemException {
		if (id == null){
			log.error("getAllById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getAllById id is null!");
		}
		Optional<StudentsEntity> optional = studentsDao.findById(id);
		StudentsEntity student = null;
		if (optional.isPresent()){
			student = optional.get();
			UserEntity user = userService.getAllById(student.getUserId());
			student.setUserEntity(user);
		}
		return student;
	}

}