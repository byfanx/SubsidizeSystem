package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.*;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryAttentionForm;
import com.byfan.subsidizesystem.model.AttentionEntity;
import com.byfan.subsidizesystem.dao.AttentionDao;
import com.byfan.subsidizesystem.model.SchoolEntity;
import com.byfan.subsidizesystem.model.StudentsEntity;
import com.byfan.subsidizesystem.model.UserEntity;
import com.byfan.subsidizesystem.service.AttentionService;
import com.byfan.subsidizesystem.service.SchoolService;
import com.byfan.subsidizesystem.service.StudentsService;
import com.byfan.subsidizesystem.service.UserService;
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
 * @Description Attention ServiceImpl层
 * @Author: byfan
 * @Date: 2022/04/06 23:30
 */
@Slf4j
@Service
public class AttentionServiceImpl implements AttentionService {

	@Autowired
	private AttentionDao attentionDao;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private StudentsService studentsService;

	@Autowired
	private UserService userService;

	/**
	 * 新增/保存
	 * @param attention
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public AttentionEntity save(AttentionEntity attention) throws SubsidizeSystemException {
		if (attention.getApproveIdentity() == AuthenticationEnum.STUDENT.code){
			StudentsEntity student = studentsService.getById(attention.getFollowedUserId());
			attention.setFollowedUserName(student.getName());
		}else if (attention.getApproveIdentity() == AuthenticationEnum.SCHOOL.code){
			SchoolEntity school = schoolService.getById(attention.getFollowedUserId());
			attention.setFollowedUserName(school.getName());
		}
		UserEntity user = userService.getById(attention.getUserId());
		attention.setUserDisplayName(user.getDisplayName());
		attention.setStatus(StatusEnum.USING.code);
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
		AttentionEntity entity = getById(id);
		if (entity == null){
			log.error("deleteById attention is null");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"attention is null");
		}
		entity.setStatus(StatusEnum.DELETED.code);
		attentionDao.save(entity);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public PageData<AttentionEntity> findByQuery(QueryAttentionForm attentionForm) throws SubsidizeSystemException {
//		if (attentionForm.getApproveIdentity() == null){
//			log.error("findByQuery approveIdentity is null");
//			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR, "approveIdentity is null");
//		}

		Sort sort = Sort.by(Sort.Direction.DESC,"createTime");

		attentionForm.setCurrentPage(attentionForm.getCurrentPage() <= 0 ? 1 : attentionForm.getCurrentPage());
		attentionForm.setPageSize(attentionForm.getPageSize() <=0 ? 20 : attentionForm.getPageSize());
		Pageable pageable = PageRequest.of(attentionForm.getCurrentPage()-1,attentionForm.getPageSize(),sort);

		Specification<AttentionEntity> specification = new Specification<AttentionEntity>() {
			@SneakyThrows
			@Override
			public Predicate toPredicate(Root<AttentionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<>();
				predicateList.add(cb.equal(root.get("status"),StatusEnum.USING.code));

				// 查询条件：认证的身份 4 贫困生  5 贫困学校
				if (attentionForm.getApproveIdentity() != null){
					predicateList.add(cb.equal(root.get("approveIdentity"),attentionForm.getApproveIdentity()));
				}

				// 查询条件：用户id
				if (attentionForm.getUserId() != null){
					predicateList.add(cb.equal(root.get("userId"),attentionForm.getUserId()));
				}

				// 查询条件：用户名称
				if (!StringUtils.isBlank(attentionForm.getUserDisplayName())){
					List<UserEntity> userList = userService.findByDisplayName(attentionForm.getUserDisplayName());
					List<Integer> userIds = userList.stream().map(UserEntity::getId).collect(Collectors.toList());
					if (!CollectionUtils.isEmpty(userIds)){
						CriteriaBuilder.In<Object> in = cb.in(root.get("userId"));
						for (Integer userId : userIds){
							in.value(userId);
						}
						predicateList.add(in);
					}
				}

				// 查询条件：被关注对象名称
				if (!StringUtils.isBlank(attentionForm.getFollowedUserDisplayName())){
					// 最终根据被关注对象查询出来的id
					List<Integer> followedUserIds = new ArrayList<>();
					// 根据学生和学校不同对象去查询
//					if (attentionForm.getApproveIdentity() == AuthenticationEnum.STUDENT.code){
					List<StudentsEntity> studentList = studentsService.findByName(attentionForm.getFollowedUserDisplayName());
					List<Integer> stuIds = studentList.stream().map(StudentsEntity::getId).collect(Collectors.toList());
					followedUserIds.addAll(stuIds);
//					}else if (attentionForm.getApproveIdentity() == AuthenticationEnum.SCHOOL.code){
					List<SchoolEntity> schoolList = schoolService.findByName(attentionForm.getFollowedUserDisplayName());
					List<Integer> schoolIds = schoolList.stream().map(SchoolEntity::getId).collect(Collectors.toList());
					followedUserIds.addAll(schoolIds);
//					}

					if (!CollectionUtils.isEmpty(followedUserIds)){
						CriteriaBuilder.In<Object> in = cb.in(root.get("followedUserId"));
						for (Integer followedUserId : followedUserIds){
							in.value(followedUserId);
						}
						predicateList.add(in);
					}
				}

				query.distinct(true);
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};

		Page<AttentionEntity> all = attentionDao.findAll(specification, pageable);
		PageData<AttentionEntity> pageData = new PageData<>();
		pageData.setCurrentPage(attentionForm.getCurrentPage());
		pageData.setPageSize(attentionForm.getPageSize());
		pageData.setTotalPage(all.getTotalPages());
		pageData.setTotal((int) all.getTotalElements());

		// 拼装返回列表
		List<AttentionEntity> result = new ArrayList<>();
		for (AttentionEntity entity : all.getContent()){
			AttentionEntity attention = assembleAttention(entity);
			if (attention != null){
				result.add(attention);
			}
		}

		pageData.setList(result);

		return pageData;
	}

	/**
	 * @Description 根据用户id删除关注信息
	 * @Author byfan
	 * @Date 2022/4/25 23:00
	 * @param id
	 * @return void
	 * @throws SubsidizeSystemException
	 */
	@Override
	public void deleteByUserId(Integer id) throws SubsidizeSystemException {
		if (id == null){
			log.error("deleteByUserId id is null");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR, "deleteByUserId id is null");
		}
		attentionDao.deleteByUserId(id, StatusEnum.DELETED.code);
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
		AttentionEntity attention = new AttentionEntity();
		if (optional.isPresent()){
			AttentionEntity entity = optional.get();
			if (entity.getStatus() == StatusEnum.DELETED.code){
				log.error("getById attention is null");
				throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"attention is null");
			}else {
				attention = assembleAttention(entity);
			}
		}
		return attention;
	}

	/**
	 * @Description 组装关注信息
	 * @Author byfan
	 * @Date 2022/4/21 22:09
	 * @param attentionEntity
	 * @return com.byfan.subsidizesystem.model.AttentionEntity
	 * @throws SubsidizeSystemException
	 */
	public AttentionEntity assembleAttention(AttentionEntity attentionEntity) throws SubsidizeSystemException {
		if (attentionEntity == null){
			log.error("assembleAttention attentionEntity is null");
			return null;
		}
		if (attentionEntity.getApproveIdentity() == AuthenticationEnum.STUDENT.code){
			StudentsEntity student = studentsService.getAllById(attentionEntity.getFollowedUserId());
			if (student == null){
				log.error("assembleAttention student is null");
				return null;
			}else {
				attentionEntity.setFollowedUserName(student.getName());
			}
		}else if (attentionEntity.getApproveIdentity() == AuthenticationEnum.SCHOOL.code){
			SchoolEntity school = schoolService.getAllById(attentionEntity.getFollowedUserId());
			if (school == null){
				log.error("assembleAttention school is null");
				return null;
			}else {
				attentionEntity.setFollowedUserName(school.getName());
			}
		}
		UserEntity user = userService.getAllById(attentionEntity.getUserId());
		attentionEntity.setUserDisplayName(user.getDisplayName());
		return attentionEntity;
	}

}