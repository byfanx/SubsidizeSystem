package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.StudentsEntity;

import java.util.List;

/**
 * @Description Students Service层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
public interface StudentsService {

	/**
	 * 新增/保存
	 * @param students
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	StudentsEntity save(StudentsEntity students) throws SubsidizeSystemException;

	/**
	 * 根据id删除
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	void deleteById(Integer id) throws SubsidizeSystemException;

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	List<StudentsEntity> getAll() throws SubsidizeSystemException;

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	StudentsEntity getById(Integer id) throws SubsidizeSystemException;

}