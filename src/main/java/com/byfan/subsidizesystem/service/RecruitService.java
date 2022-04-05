package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.RecruitEntity;

import java.util.List;

/**
 * @Description Recruit Service层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
public interface RecruitService {

	/**
	 * 新增/保存
	 * @param recruit
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	RecruitEntity save(RecruitEntity recruit) throws SubsidizeSystemException;

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
	List<RecruitEntity> getAll() throws SubsidizeSystemException;

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	RecruitEntity getById(Integer id) throws SubsidizeSystemException;

}