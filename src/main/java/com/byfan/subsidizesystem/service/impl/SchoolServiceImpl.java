package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.SchoolEntity;
import com.byfan.subsidizesystem.dao.SchoolDao;
import com.byfan.subsidizesystem.model.UserEntity;
import com.byfan.subsidizesystem.service.SchoolService;
import com.byfan.subsidizesystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description School ServiceImpl层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Service
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	private SchoolDao schoolDao;

	@Autowired
	private UserService userService;

	/**
	 * 新增/保存
	 * @param school
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public SchoolEntity save(SchoolEntity school) throws SubsidizeSystemException {
		return schoolDao.save(school);
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
		schoolDao.deleteById(id);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public List<SchoolEntity> getAll() throws SubsidizeSystemException {
		return schoolDao.findAll();
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public SchoolEntity getById(Integer id) throws SubsidizeSystemException{
		if (id == null){
			log.error("getById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getById id is null!");
		}
		Optional<SchoolEntity> optional = schoolDao.findById(id);
		SchoolEntity school = null;
		if (optional.isPresent()){
			school = optional.get();
			UserEntity user = userService.getById(school.getUserId());
			school.setUserEntity(user);
		}
		return optional.orElse(null);
	}

}