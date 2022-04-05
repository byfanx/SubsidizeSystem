package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.RecruitEntity;
import com.byfan.subsidizesystem.dao.RecruitDao;
import com.byfan.subsidizesystem.service.RecruitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description Recruit ServiceImpl层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Service
public class RecruitServiceImpl implements RecruitService {

	@Autowired
	private RecruitDao recruitDao;

	/**
	 * 新增/保存
	 * @param recruit
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public RecruitEntity save(RecruitEntity recruit) throws SubsidizeSystemException {
		return recruitDao.save(recruit);
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
		recruitDao.deleteById(id);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public List<RecruitEntity> getAll() throws SubsidizeSystemException {
		return recruitDao.findAll();
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public RecruitEntity getById(Integer id) throws SubsidizeSystemException{
		if (id == null){
			log.error("getById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getById id is null!");
		}
		Optional<RecruitEntity> optional = recruitDao.findById(id);
		return optional.orElse(null);
	}

}