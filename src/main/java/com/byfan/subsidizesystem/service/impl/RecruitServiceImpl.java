package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.bean.ContributionBean;
import com.byfan.subsidizesystem.common.*;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryRecruitForm;
import com.byfan.subsidizesystem.model.RecruitEntity;
import com.byfan.subsidizesystem.dao.RecruitDao;
import com.byfan.subsidizesystem.model.SchoolEntity;
import com.byfan.subsidizesystem.model.UserEntity;
import com.byfan.subsidizesystem.service.RecruitService;
import com.byfan.subsidizesystem.service.SchoolService;
import com.byfan.subsidizesystem.service.UserService;
import com.byfan.subsidizesystem.utils.MyUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private UserService userService;


	/**
	 * 新增/保存
	 * @param recruit
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public RecruitEntity save(RecruitEntity recruit) throws SubsidizeSystemException {
		if (recruit == null){
			log.error("save recruit is null");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR);
		}

		if (recruit.getId() == null){
			recruit.setStatus(StatusEnum.USING.code);
			recruit.setAuthorizeStatus(ApprovalStatusEnum.WAIT.code);
			recruit.setAuditorId(0);
		}else {
			Optional<RecruitEntity> byId = recruitDao.findById(recruit.getId());
			if (byId.isPresent()){
				RecruitEntity rec = byId.get();
				MyUtils.copyPropertiesIgnoreNull(recruit,rec);
				recruit = rec;
			}
		}
		RecruitEntity save = recruitDao.save(recruit);

		return assembleRecruitEntity(save);
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
		RecruitEntity byId = getById(id);
		if (byId == null || byId.getStatus() == StatusEnum.DELETED.code){
			log.info("deleteById user is not exist!");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"招聘信息不存在");
		}
		recruitDao.deleteById(id);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public PageData<RecruitEntity> findByQuery(QueryRecruitForm recruitForm) throws SubsidizeSystemException {
		Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
		recruitForm.setCurrentPage(recruitForm.getCurrentPage() <= 0 ? 1 : recruitForm.getCurrentPage());
		recruitForm.setPageSize(recruitForm.getPageSize() <=0 ? 20 : recruitForm.getPageSize());
		Pageable pageable = PageRequest.of(recruitForm.getCurrentPage()-1, recruitForm.getPageSize(), sort);

		Specification<RecruitEntity> specification = new Specification<RecruitEntity>() {
			@SneakyThrows
			@Override
			public Predicate toPredicate(Root<RecruitEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<>();
				predicateList.add(cb.equal(root.get("status"), StatusEnum.USING.code));

				// 查询条件：招聘者id
				if (recruitForm.getUserId() != null){
					if (recruitForm.getLevel() == null)
					{
						log.error("findByQuery level is null");
						throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR, "level is null");
					}
					predicateList.add(cb.equal(root.get("userId"), recruitForm.getUserId()));
					predicateList.add(cb.equal(root.get("level"), recruitForm.getLevel()));
				}

				// 查询条件：招聘单位
				if (StringUtils.isNoneBlank(recruitForm.getRecruitmentUnit()) && (recruitForm.getLevel() == null || recruitForm.getLevel() == AuthenticationEnum.SCHOOL.code)){
					List<SchoolEntity> schoolList = schoolService.findByName(recruitForm.getRecruitmentUnit());
					List<Integer> schoolIds = schoolList.stream().map(SchoolEntity::getId).collect(Collectors.toList());
					if (!CollectionUtils.isEmpty(schoolIds)){
						CriteriaBuilder.In<Object> in = cb.in(root.get("userId"));
						for (Integer schoolId : schoolIds){
							in.value(schoolId);
						}
						predicateList.add(in);
					}
				}
				// 查询条件：招聘级别 1 系统 2 学校
				if (recruitForm.getLevel() != null){
					predicateList.add(cb.equal(root.get("level"), recruitForm.getLevel()));
				}

				// 查询条件：审核人名称
				if (StringUtils.isNoneBlank(recruitForm.getAuditorDisplayName())){
					List<UserEntity> userList = userService.findByDisplayName(recruitForm.getAuditorDisplayName());
					List<Integer> userIds = userList.stream().map(UserEntity::getId).collect(Collectors.toList());
					if (!CollectionUtils.isEmpty(userIds)){
						CriteriaBuilder.In<Object> in = cb.in(root.get("auditorId"));
						for (Integer id : userIds){
							in.value(id);
						}
						predicateList.add(in);
					}
				}

				// 查询条件：标题
				if (StringUtils.isNoneBlank(recruitForm.getTitle())){
					predicateList.add(cb.like(root.get("title"), "%" + recruitForm.getTitle() + "%"));
				}

				// 查询条件：岗位
				if (StringUtils.isNoneBlank(recruitForm.getPosition())){
					predicateList.add(cb.like(root.get("position"), "%" + recruitForm.getPosition() + "%"));
				}
				// 查询条件：薪资
				if (StringUtils.isNoneBlank(recruitForm.getSalary())){
					predicateList.add(cb.like(root.get("salary"), "%" + recruitForm.getSalary() + "%"));
				}
				// 查询条件：内容
				if (StringUtils.isNoneBlank(recruitForm.getContent())){
					predicateList.add(cb.like(root.get("content"), "%" + recruitForm.getContent() + "%"));
				}

				// 查询条件：审核状态 0 待审核  1 通过  2 拒绝
				if (recruitForm.getAuthorizeStatus() != null){
					predicateList.add(cb.equal(root.get("authorizeStatus"), recruitForm.getAuthorizeStatus()));
				}

				query.distinct(true);
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};

		Page<RecruitEntity> all = recruitDao.findAll(specification, pageable);

		PageData<RecruitEntity> pageData = new PageData<>();
		pageData.setCurrentPage(recruitForm.getCurrentPage());
		pageData.setPageSize(recruitForm.getPageSize());
		pageData.setTotalPage(all.getTotalPages());
		pageData.setTotal((int) all.getTotalElements());
		// 拼装返回列表
		List<RecruitEntity> recruitList = new ArrayList<>();
		for (RecruitEntity recruitEntity : all.getContent()){
			recruitList.add(assembleRecruitEntity(recruitEntity));
		}

		pageData.setList(recruitList);

		return pageData;
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
		RecruitEntity recruitEntity = optional.orElse(null);
		return recruitEntity == null ? null : assembleRecruitEntity(recruitEntity);
	}

	/**
	 * @Description 根据发布者id和发布者身份删除招聘信息
	 * @Author byfan
	 * @Date 2022/4/25 23:15
	 * @param userId
	 * @param level
	 * @return void
	 * @throws SubsidizeSystemException
	 */
	@Override
	public void deleteByUserIdAndLevel(Integer userId, Integer level) throws SubsidizeSystemException {
		if (userId == null || level == null){
			log.error("deleteByUserIdAndLevel userId or level is null");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR, "deleteByUserIdAndLevel userId or level is null");
		}
		recruitDao.deleteByUserIdAndLevel(userId, level, StatusEnum.DELETED.code);
	}

	/**
	 * @Description 审核招聘信息
	 * @Author byfan
	 * @Date 2022/4/12 11:27
	 * @param recruitId
	 * @param auditorId
	 * @param status
	 * @return com.byfan.subsidizesystem.model.RecruitEntity
	 * @throws SubsidizeSystemException
	 */
	@Override
	public RecruitEntity checkRecruitApprove(Integer recruitId, Integer auditorId, Integer status) throws SubsidizeSystemException {
		if (recruitId == null || auditorId== null || status == null){
			log.error("checkRecruitApprove recruitId or auditorId or status is null");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"recruitId or auditorId or status is null");
		}
		RecruitEntity re = getById(recruitId);
		if (re == null || re.getStatus() == StatusEnum.DELETED.code){
			log.error("checkRecruitApprove recruit is not exist");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"recruit is not exist");
		}
		re.setAuthorizeStatus(status);
		re.setAuditorId(auditorId);
		return recruitDao.save(re);
	}

	/**
	 * @Description 组装招聘信息
	 * @Author byfan
	 * @Date 2022/4/11 22:37
	 * @param save
	 * @return com.byfan.subsidizesystem.model.RecruitEntity
	 * @throws SubsidizeSystemException
	 */
	public RecruitEntity assembleRecruitEntity(RecruitEntity save) throws SubsidizeSystemException{

		UserEntity auditor = userService.getAllById(save.getAuditorId());
		if(auditor != null){
			save.setAuditorName(auditor.getDisplayName());
		}else {
			save.setAuditorName("暂无");
		}
		if (save.getLevel().equals(AuthenticationEnum.SYSTEM.code)){
			save.setRecruitmentUnit("系统");
		}else {
			SchoolEntity school = schoolService.getById(save.getUserId());
			if (school != null){
				save.setRecruitmentUnit(school.getName());
			}else {
				save.setRecruitmentUnit("学校");
			}
		}

		return save;
	}

}