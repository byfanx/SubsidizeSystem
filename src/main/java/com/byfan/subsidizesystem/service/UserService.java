package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.UserEntity;

import java.util.List;

/**
 * @Description User Service层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
public interface UserService {

	/**
	 * 新增/保存
	 * @param user
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	UserEntity save(UserEntity user) throws SubsidizeSystemException;

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
	List<UserEntity> getAll() throws SubsidizeSystemException;

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	UserEntity getById(Integer id) throws SubsidizeSystemException;

}