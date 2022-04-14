package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.bean.ContributionBean;
import com.byfan.subsidizesystem.common.*;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryContributionForm;
import com.byfan.subsidizesystem.model.ContributionEntity;
import com.byfan.subsidizesystem.dao.ContributionDao;
import com.byfan.subsidizesystem.model.SchoolEntity;
import com.byfan.subsidizesystem.model.StudentsEntity;
import com.byfan.subsidizesystem.model.UserEntity;
import com.byfan.subsidizesystem.service.ContributionService;
import com.byfan.subsidizesystem.service.SchoolService;
import com.byfan.subsidizesystem.service.StudentsService;
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
 * @Description Contribution ServiceImpl层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Service
public class ContributionServiceImpl implements ContributionService {

	@Autowired
	private ContributionDao contributionDao;

	@Autowired
	private UserService userService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private StudentsService studentsService;

	/**
	 * 新增/保存
	 * @param contribution
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public ContributionEntity save(ContributionEntity contribution) throws SubsidizeSystemException {
		if (contribution.getId() == null){
			contribution.setStatus(StatusEnum.USING.code);
			contribution.setAuditorId(0);
			contribution.setAuthorizeStatus(ApprovalStatusEnum.WAIT.code);
		}else {
			Optional<ContributionEntity> byId = contributionDao.findById(contribution.getId());
			if (byId.isPresent()){
				ContributionEntity ce = byId.get();
				MyUtils.copyPropertiesIgnoreNull(contribution, ce);
				contribution = ce;
			}
		}
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
		ContributionBean byId = getById(id);
		if (byId == null || byId.getStatus() == StatusEnum.DELETED.code){
			log.info("deleteById contribution is not exist!");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"捐款信息不存在");
		}
		byId.setStatus(StatusEnum.DELETED.code);
		save(byId);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public PageData<ContributionBean> findByQuery(QueryContributionForm queryContributionForm) throws SubsidizeSystemException {

		Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
		queryContributionForm.setCurrentPage(queryContributionForm.getCurrentPage() <= 0 ? 1 : queryContributionForm.getCurrentPage());
		queryContributionForm.setPageSize(queryContributionForm.getPageSize() <=0 ? 20 : queryContributionForm.getPageSize());

		Pageable pageable = PageRequest.of(queryContributionForm.getCurrentPage()-1, queryContributionForm.getPageSize(), sort);

		Specification<ContributionEntity> specification = new Specification<ContributionEntity>() {
			@SneakyThrows
			@Override
			public Predicate toPredicate(Root<ContributionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<>();
				predicateList.add(cb.equal(root.get("status"), StatusEnum.USING.code));

				// 查询条件：捐款人名称
				if (StringUtils.isNotBlank(queryContributionForm.getUserName())) {
					List<UserEntity> userList = userService.findByDisplayName(queryContributionForm.getUserName());
					if (!CollectionUtils.isEmpty(userList)) {
						List<Integer> userIdList = userList.stream().map(UserEntity::getId).collect(Collectors.toList());
						CriteriaBuilder.In<Object> in = cb.in(root.get("userId"));
						for (Integer userId : userIdList) {
							in.value(userId);
						}
						predicateList.add(in);
					}
				}

				// 查询条件：被捐款对象名称
				if (StringUtils.isNotBlank(queryContributionForm.getPayeeName())) {
					List<StudentsEntity> studentList = studentsService.findByName(queryContributionForm.getPayeeName());
					List<Integer> studentIdList = studentList.stream().map(StudentsEntity::getUserId).collect(Collectors.toList());

					List<SchoolEntity> schoolList = schoolService.findByName(queryContributionForm.getPayeeName());
					List<Integer> schoolIdList = schoolList.stream().map(SchoolEntity::getUserId).collect(Collectors.toList());

					List<Integer> payeeIdList = new ArrayList<>();
					if (!CollectionUtils.isEmpty(studentIdList))
						payeeIdList.addAll(studentIdList);
					if (!CollectionUtils.isEmpty(schoolIdList))
						payeeIdList.addAll(schoolIdList);

					if (!CollectionUtils.isEmpty(payeeIdList)) {
						CriteriaBuilder.In<Object> in = cb.in(root.get("payee"));
						for (Integer payeeId : payeeIdList) {
							in.value(payeeId);
						}
						predicateList.add(in);
					}
				}

				// 查询条件：被捐款对象身份  3 平台 4 贫困生 5 贫困学校
				if (queryContributionForm.getApproveIdentity() != null) {
					predicateList.add(cb.equal(root.get("approveIdentity"), queryContributionForm.getApproveIdentity()));
				}

				// 查询条件：审核人名称
				if (StringUtils.isNotBlank(queryContributionForm.getAuditorName())) {
					List<UserEntity> userList = userService.findByDisplayName(queryContributionForm.getAuditorName());
					if (!CollectionUtils.isEmpty(userList)) {
						CriteriaBuilder.In<Object> in = cb.in(root.get("auditorId"));
						for (UserEntity user : userList) {
							if (user.getRole() == AuthenticationEnum.ADMIN.code) {
								in.value(user.getId());
							}
						}
						predicateList.add(in);
					}
				}

				// 查询条件：审核状态 0 待审核  1 通过  2 拒绝
				if (queryContributionForm.getAuthorizeStatus() != null) {
					predicateList.add(cb.equal(root.get("authorizeStatus"), queryContributionForm.getAuthorizeStatus()));
				}


				query.distinct(true);

				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};

		Page<ContributionEntity> all = contributionDao.findAll(specification, pageable);

		PageData<ContributionBean> pageData = new PageData<>();
		pageData.setCurrentPage(queryContributionForm.getCurrentPage());
		pageData.setPageSize(queryContributionForm.getPageSize());
		pageData.setTotalPage(all.getTotalPages());
		pageData.setTotal((int) all.getTotalElements());
		// 拼装返回列表
		List<ContributionBean> contributionBeans = assembleContributionBean(all.getContent());

		pageData.setList(contributionBeans);

		return pageData;

	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @throws SubsidizeSystemException
	 */
	@Override
	public ContributionBean getById(Integer id) throws SubsidizeSystemException{
		if (id == null){
			log.error("getById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"id is null!");
		}
		Optional<ContributionEntity> optional = contributionDao.findById(id);
		ContributionBean bean = null;
		if (optional.isPresent()){
			List<ContributionEntity> entityList = new ArrayList<ContributionEntity>() {{
				add(optional.get());
			}};
			List<ContributionBean> contributionBeans = assembleContributionBean(entityList);
			bean = contributionBeans.get(0);
		}else {
			log.error("getById contribution is not exist");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"捐款信息不存在");
		}
		return bean;
	}

	/**
	 * @param contributionId
	 * @param authorizeStatus
	 * @return com.byfan.subsidizesystem.bean.ContributionBean
	 * @throws SubsidizeSystemException
	 * @Description 根据id更改审核状态
	 * @Author byfan
	 * @Date 2022/4/10 18:41
	 */
	@Override
	public ContributionBean checkRecruitApprove(Integer contributionId, Integer authorizeStatus, Integer auditorId) throws SubsidizeSystemException {
		if (contributionId == null || authorizeStatus == null || auditorId == null){
			log.error("checkRecruitApprove contributionId or authorizeStatus or auditorId is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"contributionId or authorizeStatus or auditorId is null!");
		}
		ContributionEntity entity = contributionDao.getById(contributionId);
		entity.setAuthorizeStatus(authorizeStatus);
		entity.setAuditorId(auditorId);
		List<ContributionBean> contributionBeans = assembleContributionBean(
				new ArrayList<ContributionEntity>() {{
					add(save(entity));
				}}
		);
		return contributionBeans.get(0);
	}

	/**
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return com.byfan.subsidizesystem.common.PageData<com.byfan.subsidizesystem.bean.ContributionBean>
	 * @throws SubsidizeSystemException
	 * @Description 根据捐款人id查询捐款信息
	 * @Author byfan
	 * @Date 2022/4/10 22:23
	 */
	@Override
	public PageData<ContributionBean> getByUserId(Integer userId, Integer currentPage, Integer pageSize) throws SubsidizeSystemException {
		if (userId == null){
			log.error("getByUserId userId is null");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"userId is null");
		}
		currentPage = currentPage <= 0 ? 1 : currentPage;
		pageSize = pageSize <= 0 ? 20 : currentPage;

		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		Pageable pageable = PageRequest.of(currentPage-1, pageSize, sort);
		Page<ContributionEntity> all = contributionDao.findByUserId(userId, pageable);

		PageData<ContributionBean> pageData = new PageData<>();
		pageData.setCurrentPage(currentPage);
		pageData.setPageSize(pageSize);
		pageData.setTotal((int) all.getTotalElements());
		pageData.setTotalPage(all.getTotalPages());
		pageData.setList(assembleContributionBean(all.getContent()));

		return pageData;
	}

	/**
	 * @throws SubsidizeSystemException
	 * @Description 根据被捐款人id获取捐信息
	 * @Author byfan
	 * @Date 2022/4/10 22:33
	 * @param payeeId
	 * @param currentPage
	 * @param pageSize
	 * @return com.byfan.subsidizesystem.common.PageData<com.byfan.subsidizesystem.bean.ContributionBean>
	 */
	@Override
	public PageData<ContributionBean> getByPayeeId(Integer payeeId, Integer currentPage, Integer pageSize) throws SubsidizeSystemException {
		if (payeeId == null){
			log.error("getByPayeeId payeeId is null");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"payeeId is null");
		}
		currentPage = currentPage <= 0 ? 1 : currentPage;
		pageSize = pageSize <= 0 ? 20 : currentPage;

		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		Pageable pageable = PageRequest.of(currentPage-1, pageSize, sort);
		Page<ContributionEntity> all = contributionDao.findByPayeeId(payeeId, pageable);

		PageData<ContributionBean> pageData = new PageData<>();
		pageData.setCurrentPage(currentPage);
		pageData.setPageSize(pageSize);
		pageData.setTotal((int) all.getTotalElements());
		pageData.setTotalPage(all.getTotalPages());
		pageData.setList(assembleContributionBean(all.getContent()));

		return pageData;
	}


	/**
	 * @Description 拼装ContributionBean
	 * @Author byfan
	 * @Date 2022/4/10 17:50
	 * @param list
	 * @return java.util.List<com.byfan.subsidizesystem.bean.ContributionBean>
	 * @throws SubsidizeSystemException
	 */
	public List<ContributionBean> assembleContributionBean(List<ContributionEntity> list) throws SubsidizeSystemException {
		if(CollectionUtils.isEmpty(list)){
			return new ArrayList<>();
		}
		List<ContributionBean> result = new ArrayList<>();
		for (ContributionEntity entity : list){
			ContributionBean bean = new ContributionBean();
			// 拼装基础信息
			bean.setId(entity.getId());
			bean.setUserId(entity.getUserId());
			bean.setPayee(entity.getPayee());
			bean.setApproveIdentity(entity.getApproveIdentity());
			bean.setPaymentAmount(entity.getPaymentAmount());
			bean.setRemarks(entity.getRemarks());
			bean.setAuditorId(entity.getAuditorId());
			bean.setAuthorizeStatus(entity.getAuthorizeStatus());
			bean.setStatus(entity.getStatus());
			bean.setCreateTime(entity.getCreateTime());
			// 设置捐款人信息
			UserEntity donationUser = userService.getAllById(entity.getUserId());
			bean.setUser(donationUser);
			// 设置审核人信息
			UserEntity authorUser = userService.getAllById(entity.getAuditorId());
			bean.setAuthorUser(authorUser);
			log.error("entity.getApproveIdentity()={}",entity.getApproveIdentity());
			// 如果是收款人是学生，设置学生信息
			if (entity.getApproveIdentity().equals(AuthenticationEnum.STUDENT.code)){
				StudentsEntity student = studentsService.getAllById(entity.getPayee());
				bean.setStudents(student);
			}
			// 如果是收款人是学校，设置学校信息
			else if (entity.getApproveIdentity().equals(AuthenticationEnum.SCHOOL.code)){
				SchoolEntity school = schoolService.getAllById(entity.getPayee());
				bean.setSchool(school);
			}
			// 如果收款人是平台，则不做处理

			result.add(bean);
		}
		return result;
	}

}