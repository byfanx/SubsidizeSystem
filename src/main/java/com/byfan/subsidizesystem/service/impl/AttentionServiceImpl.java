package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.AttentionEntity;
import com.byfan.subsidizesystem.dao.AttentionDao;
import com.byfan.subsidizesystem.service.AttentionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description Attention ServiceImpl层
 * @Author: byfan
 * @Date: 2022/04/06 23:30
 */
@Slf4j
@Service
public class AttentionServiceImpl implements AttentionService {

	@Autowired
	private AttentionDao attentionDao;

	/**
	 * 新增/保存
	 * @param attention
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public AttentionEntity save(AttentionEntity attention) throws SubsidizeSystemException {
		return attentionDao.save(attention);
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
		attentionDao.deleteById(id);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public List<AttentionEntity> getAll() throws SubsidizeSystemException {
		return attentionDao.findAll();
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public AttentionEntity getById(Integer id) throws SubsidizeSystemException{
		if (id == null){
			log.error("getById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getById id is null!");
		}
		Optional<AttentionEntity> optional = attentionDao.findById(id);
		return optional.orElse(null);
	}

}