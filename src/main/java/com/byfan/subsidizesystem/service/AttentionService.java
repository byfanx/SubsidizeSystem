package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryAttentionForm;
import com.byfan.subsidizesystem.model.AttentionEntity;

import java.util.List;

/**
 * @Description Attention Service层
 * @Author: byfan
 * @Date: 2022/04/06 23:30
 */
public interface AttentionService {

	/**
	 * 新增/保存
	 * @param attention
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	AttentionEntity save(AttentionEntity attention) throws SubsidizeSystemException;

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
	PageData<AttentionEntity> findByQuery(QueryAttentionForm attentionForm) throws SubsidizeSystemException;

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	AttentionEntity getById(Integer id) throws SubsidizeSystemException;

}