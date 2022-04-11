package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.bean.ContributionBean;
import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryContributionForm;
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
	PageData<ContributionBean> findByQuery(QueryContributionForm queryContributionForm) throws SubsidizeSystemException;

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	ContributionBean getById(Integer id) throws SubsidizeSystemException;

	/**
	 * @Description 根据id更改审核状态
	 * @Author byfan
	 * @Date 2022/4/10 18:41
	 * @param contributionId
	 * @param authorizeStatus
	 * @param auditorId
	 * @return com.byfan.subsidizesystem.bean.ContributionBean
	 * @throws SubsidizeSystemException
	 */
	ContributionBean checkRecruitApprove(Integer contributionId, Integer authorizeStatus, Integer auditorId) throws SubsidizeSystemException;

	/**
	 * @Description 根据捐款人id查询捐款信息
	 * @Author byfan
	 * @Date 2022/4/10 22:23
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return com.byfan.subsidizesystem.common.PageData<com.byfan.subsidizesystem.bean.ContributionBean>
	 * @throws SubsidizeSystemException
	 */
	PageData<ContributionBean> getByUserId(Integer userId, Integer currentPage, Integer pageSize) throws SubsidizeSystemException;

	/**
	 * @Description 根据被捐款人id获取捐信息
	 * @Author byfan
	 * @Date 2022/4/10 22:33
	 * @param payeeId
	 * @param currentPage
	 * @param pageSize
	 * @return com.byfan.subsidizesystem.common.PageData<com.byfan.subsidizesystem.bean.ContributionBean>
	 * @throws SubsidizeSystemException
	 */
	PageData<ContributionBean> getByPayeeId(Integer payeeId, Integer currentPage, Integer pageSize) throws SubsidizeSystemException;
}