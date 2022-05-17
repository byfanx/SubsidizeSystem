package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryRecruitForm;
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
	PageData<RecruitEntity> findByQuery(QueryRecruitForm recruitForm) throws SubsidizeSystemException;

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	RecruitEntity getById(Integer id) throws SubsidizeSystemException;

	/**
	 * @Description 审核招聘信息
	 * @Author byfan
	 * @Date 2022/4/12 11:27
	 * @param recruitId
	 * @param auditorId
	 * @param status
	 * @return com.byfan.subsidizesystem.model.RecruitEntity
	 * @throws SubsidizeSystemException
	 */
    RecruitEntity checkRecruitApprove(Integer recruitId, Integer auditorId, Integer status) throws SubsidizeSystemException;

	/**
	 * @Description 根据发布者id和发布者身份删除招聘信息
	 * @Author byfan
	 * @Date 2022/4/25 23:15
	 * @param userId
	 * @param level
	 * @return void
	 * @throws SubsidizeSystemException
	 */
	void deleteByUserIdAndLevel(Integer userId, Integer level) throws SubsidizeSystemException;
}