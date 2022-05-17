package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.common.AuthenticationEnum;
import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryNoticeForm;
import com.byfan.subsidizesystem.model.NoticeEntity;

import java.util.List;

/**
 * @Description Notice Service层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
public interface NoticeService {

	/**
	 * 新增/保存
	 * @param notice
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	NoticeEntity save(NoticeEntity notice) throws SubsidizeSystemException;

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
	PageData<NoticeEntity> findByQuery(QueryNoticeForm noticeForm) throws SubsidizeSystemException;

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	NoticeEntity getById(Integer id) throws SubsidizeSystemException;

}