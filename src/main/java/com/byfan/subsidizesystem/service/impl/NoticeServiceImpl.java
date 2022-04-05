package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.NoticeEntity;
import com.byfan.subsidizesystem.dao.NoticeDao;
import com.byfan.subsidizesystem.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description Notice ServiceImpl层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	/**
	 * 新增/保存
	 * @param notice
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public NoticeEntity save(NoticeEntity notice) throws SubsidizeSystemException {
		return noticeDao.save(notice);
	}

	/**
	 * 根据id删除
	 * @param id
	 * @throws SubsidizeSystemException
	 */
	@Override
	public void deleteById(Integer id) throws SubsidizeSystemException{
		if (id == null){
			log.error("deleteById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"deleteById id is null!");
		}
		noticeDao.deleteById(id);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public List<NoticeEntity> getAll() throws SubsidizeSystemException {
		return noticeDao.findAll();
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public NoticeEntity getById(Integer id) throws SubsidizeSystemException{
		if (id == null){
			log.error("getById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getById id is null!");
		}
		Optional<NoticeEntity> optional = noticeDao.findById(id);
		return optional.orElse(null);
	}

}