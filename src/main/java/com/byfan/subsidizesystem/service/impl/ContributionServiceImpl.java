package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.ContributionEntity;
import com.byfan.subsidizesystem.dao.ContributionDao;
import com.byfan.subsidizesystem.service.ContributionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description Contribution ServiceImpl层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Service
public class ContributionServiceImpl implements ContributionService {

	@Autowired
	private ContributionDao contributionDao;

	/**
	 * 新增/保存
	 * @param contribution
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public ContributionEntity save(ContributionEntity contribution) throws SubsidizeSystemException {
		return contributionDao.save(contribution);
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
		contributionDao.deleteById(id);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public List<ContributionEntity> getAll() throws SubsidizeSystemException {
		return contributionDao.findAll();
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public ContributionEntity getById(Integer id) throws SubsidizeSystemException{
		if (id == null){
			log.error("getById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getById id is null!");
		}
		Optional<ContributionEntity> optional = contributionDao.findById(id);
		return optional.orElse(null);
	}

}