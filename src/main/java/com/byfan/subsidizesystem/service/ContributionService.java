package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.ContributionEntity;

import java.util.List;

/**
 * @Description Contribution Service层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
public interface ContributionService {

	/**
	 * 新增/保存
	 * @param contribution
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	ContributionEntity save(ContributionEntity contribution) throws SubsidizeSystemException;

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
	List<ContributionEntity> getAll() throws SubsidizeSystemException;

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	ContributionEntity getById(Integer id) throws SubsidizeSystemException;

}