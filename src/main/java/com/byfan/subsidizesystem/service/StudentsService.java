package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryStudentForm;
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
	PageData<StudentsEntity> findByQuery(QueryStudentForm studentForm) throws SubsidizeSystemException;

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	StudentsEntity getById(Integer id) throws SubsidizeSystemException;

	/**
	 * @Description 根据名称查询学生
	 * @Author byfan
	 * @Date 2022/4/9 18:11
	 * @param name
	 * @return java.util.List<com.byfan.subsidizesystem.model.StudentsEntity>
	 * @throws SubsidizeSystemException
	 */
	List<StudentsEntity> findByName(String name) throws SubsidizeSystemException;

	/**
	 * @Description 根据id查询删除和未删除的学生
	 * @Author byfan
	 * @Date 2022/4/10 17:30
	 * @param id
	 * @return com.byfan.subsidizesystem.model.StudentsEntity
	 * @throws SubsidizeSystemException
	 */
    StudentsEntity getAllById(Integer id) throws SubsidizeSystemException;

	/**
	 * @Description 审核学生认证
	 * @Author byfan
	 * @Date 2022/4/14 16:07
	 * @param studentId
	 * @param authorizeStatus
	 * @param auditorId
	 * @return com.byfan.subsidizesystem.model.StudentsEntity
	 * @throws SubsidizeSystemException
	 */
	StudentsEntity checkStudentApprove(Integer studentId, Integer authorizeStatus, Integer auditorId) throws SubsidizeSystemException;
}