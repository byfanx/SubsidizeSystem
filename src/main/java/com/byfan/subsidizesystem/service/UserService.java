package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.bean.AuthenticationInfoBean;
import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryUserForm;
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
	 * @Description 条件查询
	 * @Author byfan
	 * @Date 2022/4/7 23:13
	 * @param queryUserForm
	 * @return com.byfan.subsidizesystem.common.PageData<com.byfan.subsidizesystem.model.UserEntity>
	 * @throws SubsidizeSystemException
	 */
	PageData<UserEntity> findByQuery(QueryUserForm queryUserForm) throws SubsidizeSystemException;

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	UserEntity getById(Integer id) throws SubsidizeSystemException;

	/**
	 * @Description 根据id和状态进行查询
	 * @Author byfan
	 * @Date 2022/4/10 17:16
	 * @param id
	 * @return com.byfan.subsidizesystem.model.UserEntity
	 * @throws SubsidizeSystemException
	 */
	UserEntity getAllById(Integer id) throws SubsidizeSystemException;

	/**
	 * @Description 用户登录
	 * @Author byfan
	 * @Date 2022/4/8 15:28
	 * @param userName
	 * @param passwd
	 * @return void
	 * @throws
	 */
	UserEntity login(String userName, String passwd) throws SubsidizeSystemException;

	/**
	 * @Description 找回密码
	 * @Author byfan
	 * @Date 2022/4/8 15:42
	 * @param userName
	 * @param telephone
	 * @return com.byfan.subsidizesystem.model.UserEntity
	 * @throws
	 */
	UserEntity retrievePasswd(String userName, String telephone) throws SubsidizeSystemException;

	/**
	 * @Description 根据昵称查询用户
	 * @Author byfan
	 * @Date 2022/4/9 18:11
	 * @param displayName
	 * @return java.util.List<com.byfan.subsidizesystem.model.UserEntity>
	 * @throws SubsidizeSystemException
	 */
	List<UserEntity> findByDisplayName(String displayName) throws SubsidizeSystemException;

	/**
	 * @Description 根据用户id获取认证信息
	 * @Author byfan
	 * @Date 2022/4/21 23:09
	 * @param userId
	 * @return com.byfan.subsidizesystem.bean.AuthenticationInfoBean
	 * @throws SubsidizeSystemException
	 */
	AuthenticationInfoBean getAuthenticationInfor(Integer userId) throws SubsidizeSystemException;
}