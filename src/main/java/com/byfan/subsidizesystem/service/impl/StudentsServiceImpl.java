package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.StudentsEntity;
import com.byfan.subsidizesystem.dao.StudentsDao;
import com.byfan.subsidizesystem.service.StudentsService;
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
		return studentsDao.findAll();
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
		Optional<StudentsEntity> optional = studentsDao.findById(id);
		return optional.orElse(null);
	}

}