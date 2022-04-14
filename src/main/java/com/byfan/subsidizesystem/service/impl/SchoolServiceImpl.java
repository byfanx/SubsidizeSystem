package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.bean.ContributionBean;
import com.byfan.subsidizesystem.common.*;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QuerySchoolForm;
import com.byfan.subsidizesystem.model.ContributionEntity;
import com.byfan.subsidizesystem.model.SchoolEntity;
import com.byfan.subsidizesystem.dao.SchoolDao;
import com.byfan.subsidizesystem.model.UserEntity;
import com.byfan.subsidizesystem.service.SchoolService;
import com.byfan.subsidizesystem.service.UserService;
import com.byfan.subsidizesystem.utils.MyUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@Value("${noPicturesYetPath}")
	private String noPicturesYetPath;

	/**
	 * 新增/保存
	 * @param school
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public SchoolEntity save(SchoolEntity school) throws SubsidizeSystemException {
		if (school== null){
			log.error("save user is null");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"user is null");
		}
		if (school.getId() != null){
			SchoolEntity sch = getById(school.getId());
			MyUtils.copyPropertiesIgnoreNull(school, sch);
			school = sch;
		}else {
			school.setStatus(StatusEnum.USING.code);
			school.setAuditorId(0);
			school.setAuthorizeStatus(ApprovalStatusEnum.WAIT.code);
		}
		if (StringUtils.isBlank(school.getImages())){
			school.setImages(noPicturesYetPath);
		}
		return assembleSchool(schoolDao.save(school));
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
		SchoolEntity byId = getById(id);
		byId.setStatus(StatusEnum.DELETED.code);
		schoolDao.save(byId);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public PageData<SchoolEntity> findByQuery(QuerySchoolForm schoolForm) throws SubsidizeSystemException {
		Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
		schoolForm.setCurrentPage(schoolForm.getCurrentPage() <=0 ? 1 : schoolForm.getCurrentPage());
		schoolForm.setPageSize(schoolForm.getPageSize() <=0 ? 20 : schoolForm.getPageSize());
		Pageable pageable = PageRequest.of(schoolForm.getCurrentPage()-1, schoolForm.getPageSize(),sort);

		Specification specification = new Specification() {
			@SneakyThrows
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<>();
				predicateList.add(cb.equal(root.get("status"),StatusEnum.USING.code));

				// 查询条件：用户昵称
				if (!StringUtils.isBlank(schoolForm.getUserDisplayNameName())){
					List<UserEntity> userList = userService.findByDisplayName(schoolForm.getUserDisplayNameName());
					List<Integer> userIds = userList.stream().map(UserEntity::getId).collect(Collectors.toList());
					if (!CollectionUtils.isEmpty(userIds)){
						CriteriaBuilder.In in = cb.in(root.get("userId"));
						for (Integer userId : userIds){
							in.value(userId);
						}
						predicateList.add(in);
					}
				}


				// 查询条件：学校名称
				if (!StringUtils.isBlank(schoolForm.getSchoolName())){
					predicateList.add(cb.like(root.get("name"),"%" + schoolForm.getSchoolName() + "%"));
				}

				// 查询条件：学校地址
				if (!StringUtils.isBlank(schoolForm.getAddress())){
					predicateList.add(cb.like(root.get("address"),"%" + schoolForm.getAddress() + "%"));
				}

				// 查询条件：学校联系人
				if (!StringUtils.isBlank(schoolForm.getContacts())){
					predicateList.add(cb.like(root.get("contacts"),"%" + schoolForm.getContacts() + "%"));
				}

				// 查询条件：学校联系电话
				if (!StringUtils.isBlank(schoolForm.getTelephone())){
					predicateList.add(cb.like(root.get("telephone"),"%" + schoolForm.getTelephone() + "%"));
				}

				// 查询条件：审核人名称
				if (!StringUtils.isBlank(schoolForm.getAuditorName())){
					List<UserEntity> userList = userService.findByDisplayName(schoolForm.getAuditorName());
					List<Integer> userIds = userList.stream().map(UserEntity::getId).collect(Collectors.toList());
					if (!CollectionUtils.isEmpty(userIds)){
						CriteriaBuilder.In in = cb.in(root.get("auditorId"));
						for (Integer userId : userIds){
							in.value(userId);
						}
						predicateList.add(in);
					}
				}

				// 查询条件：审核状态
				if (schoolForm.getAuthorizeStatus() != null){
					predicateList.add(cb.equal(root.get("authorizeStatus"),schoolForm.getAuthorizeStatus()));
				}

				query.distinct(true);
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};


		Page<SchoolEntity> all = schoolDao.findAll(specification, pageable);

		PageData<SchoolEntity> pageData = new PageData<>();
		pageData.setCurrentPage(schoolForm.getCurrentPage());
		pageData.setPageSize(schoolForm.getPageSize());
		pageData.setTotal((int) all.getTotalElements());
		pageData.setTotalPage(all.getTotalPages());
		List<SchoolEntity> schoolEntityList = all.getContent();
		if (!CollectionUtils.isEmpty(schoolEntityList)) {
			for (SchoolEntity school : schoolEntityList){
				assembleSchool(school);
			}
		}
		pageData.setList(schoolEntityList);
		return pageData;
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
		SchoolEntity school = getAllById(id);
		if (null == school || school.getStatus() == StatusEnum.DELETED.code){
			log.error("getById school is not exist");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"学校对象不存在");
		}
		return school;
	}

	/**
	 * @param name
	 * @return java.util.List<com.byfan.subsidizesystem.model.SchoolEntity>
	 * @throws SubsidizeSystemException
	 * @Description 根据名称查询学校
	 * @Author byfan
	 * @Date 2022/4/9 18:11
	 */
	@Override
	public List<SchoolEntity> findByName(String name) throws SubsidizeSystemException {
		if (StringUtils.isBlank(name)){
			log.error("findByName name is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"name is null");
		}
		List<SchoolEntity> byName = schoolDao.findByName(name);
		for (SchoolEntity school : byName){
			assembleSchool(school);
		}
		return byName;
	}

	/**
	 * @param id
	 * @return com.byfan.subsidizesystem.model.SchoolEntity
	 * @throws SubsidizeSystemException
	 * @Description 根据id查询删除和未删除的学校
	 * @Author byfan
	 * @Date 2022/4/10 17:34
	 */
	@Override
	public SchoolEntity getAllById(Integer id) throws SubsidizeSystemException {
		if (id == null){
			log.error("getAllById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getAllById id is null!");
		}
		Optional<SchoolEntity> optional = schoolDao.findById(id);
		SchoolEntity school = null;
		if (optional.isPresent()){
			school = assembleSchool(optional.get());
		}
		return school;
	}

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
	@Override
	public SchoolEntity checkSchoolApprove(Integer schoolId, Integer authorizeStatus, Integer auditorId) throws SubsidizeSystemException {
		if (schoolId == null || authorizeStatus == null || auditorId == null){
			log.error("checkSchoolApprove schoolId or authorizeStatus or auditorId is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"contributionId or authorizeStatus or auditorId is null!");
		}
		SchoolEntity entity = schoolDao.getById(schoolId);
		entity.setAuthorizeStatus(authorizeStatus);
		entity.setAuditorId(auditorId);
		SchoolEntity save = schoolDao.save(entity);
		return save;
	}


	/**
	 * @Description 组装学校信息
	 * @Author byfan
	 * @Date 2022/4/13 22:42
	 * @param school
	 * @return com.byfan.subsidizesystem.model.SchoolEntity
	 * @throws SubsidizeSystemException
	 */
	public SchoolEntity assembleSchool(SchoolEntity school) throws SubsidizeSystemException {
		String[] split = school.getImages().split(",");
		school.setImageList(Arrays.asList(split));

		UserEntity user = userService.getAllById(school.getUserId());
		if (user == null){
			school.setUserDisplayNameName("暂无");
		} else {
			school.setUserDisplayNameName(user.getDisplayName());
		}

		UserEntity auditor = userService.getAllById(school.getAuditorId());
		if (user == null){
			school.setAuditorName("暂无");
		}else {
			school.setAuditorName(auditor.getDisplayName());
		}
		return school;
	}

}