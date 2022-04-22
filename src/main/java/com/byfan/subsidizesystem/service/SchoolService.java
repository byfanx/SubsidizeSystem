package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QuerySchoolForm;
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
	PageData<SchoolEntity> findByQuery(QuerySchoolForm schoolForm) throws SubsidizeSystemException;

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	SchoolEntity getById(Integer id) throws SubsidizeSystemException;

	/**
	 * @Description 根据名称查询学校
	 * @Author byfan
	 * @Date 2022/4/9 18:11
	 * @param name
	 * @return java.util.List<com.byfan.subsidizesystem.model.SchoolEntity>
	 * @throws SubsidizeSystemException
	 */
	List<SchoolEntity> findByName(String name) throws SubsidizeSystemException;

	/**
	 * @Description 根据id查询删除和未删除的学校
	 * @Author byfan
	 * @Date 2022/4/10 17:34
	 * @param id
	 * @return com.byfan.subsidizesystem.model.SchoolEntity
	 * @throws SubsidizeSystemException
	 */
    SchoolEntity getAllById(Integer id) throws SubsidizeSystemException;

	/**
	 * @Description 根据id审核学校状态
	 * @Author byfan
	 * @Date 2022/4/13 23:29
	 * @param schoolId
	 * @param authorizeStatus
	 * @param auditorId
	 * @return com.byfan.subsidizesystem.model.SchoolEntity
	 * @throws SubsidizeSystemException
	 */
	SchoolEntity checkSchoolApprove(Integer schoolId, Integer authorizeStatus, Integer auditorId) throws SubsidizeSystemException;

	/**
	 * @Description 根据用户ID查询学校信息
	 * @Author byfan
	 * @Date 2022/4/22 09:31
	 * @param userId
	 * @return com.byfan.subsidizesystem.model.SchoolEntity
	 * @throws SubsidizeSystemException
	 */
	SchoolEntity getByUserId(Integer userId) throws SubsidizeSystemException;
}