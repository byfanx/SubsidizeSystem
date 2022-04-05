package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.SchoolEntity;

import java.util.List;

/**
 * @Description School Service层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
public interface SchoolService {

	/**
	 * 新增/保存
	 * @param school
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	SchoolEntity save(SchoolEntity school) throws SubsidizeSystemException;

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
	List<SchoolEntity> getAll() throws SubsidizeSystemException;

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	SchoolEntity getById(Integer id) throws SubsidizeSystemException;

}