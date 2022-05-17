package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.ApprovalStatusEnum;
import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.common.StatusEnum;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryStudentForm;
import com.byfan.subsidizesystem.model.StudentsEntity;
import com.byfan.subsidizesystem.dao.StudentsDao;
import com.byfan.subsidizesystem.model.UserEntity;
import com.byfan.subsidizesystem.service.StudentsService;
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
 * @Description Students ServiceImpl层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Service
public class StudentsServiceImpl implements StudentsService {

	@Autowired
	private StudentsDao studentsDao;

	@Autowired
	private UserService userService;

	@Value("${noPicturesYetPath}")
	private String noPicturesYetPath;

	/**
	 * 新增/保存
	 * @param students
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public StudentsEntity save(StudentsEntity students) throws SubsidizeSystemException {
		if (students== null){
			log.error("save students is null");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"students is null");
		}
		if (students.getId() != null){
			StudentsEntity stu = getById(students.getId());
			MyUtils.copyPropertiesIgnoreNull(students, stu);
			students = stu;
		}else {
			students.setStatus(StatusEnum.USING.code);
			students.setAuditorId(0);
		}
		if (StringUtils.isBlank(students.getImages())){
			students.setImages(noPicturesYetPath);
		}
		students.setAuthorizeStatus(ApprovalStatusEnum.WAIT.code);
		return assembleStudent(studentsDao.save(students));
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
		StudentsEntity byId = getById(id);
		byId.setStatus(StatusEnum.DELETED.code);
		studentsDao.save(byId);
	}

	/**
	 * 查询全部
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public PageData<StudentsEntity> findByQuery(QueryStudentForm studentForm) throws SubsidizeSystemException {
		Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
		studentForm.setCurrentPage(studentForm.getCurrentPage() <=0 ? 1 : studentForm.getCurrentPage());
		studentForm.setPageSize(studentForm.getPageSize() <=0 ? 20 : studentForm.getPageSize());
		Pageable pageable = PageRequest.of(studentForm.getCurrentPage()-1, studentForm.getPageSize(),sort);

		Specification specification = new Specification() {
			@SneakyThrows
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<>();
				predicateList.add(cb.equal(root.get("status"),StatusEnum.USING.code));

				// 查询条件：用户昵称
				if (!StringUtils.isBlank(studentForm.getUserDisplayName())){
					List<UserEntity> userList = userService.findByDisplayName(studentForm.getUserDisplayName());
					List<Integer> userIds = userList.stream().map(UserEntity::getId).collect(Collectors.toList());
					if (!CollectionUtils.isEmpty(userIds)){
						CriteriaBuilder.In in = cb.in(root.get("userId"));
						for (Integer userId : userIds){
							in.value(userId);
						}
						predicateList.add(in);
					}
				}
				// 查询条件：学生名称
				if (!StringUtils.isBlank(studentForm.getStudentName())){
					predicateList.add(cb.like(root.get("name"), "%" + studentForm.getStudentName() + "%"));
				}
				// 查询条件：性别
				if (studentForm.getGender() != null){
					predicateList.add(cb.equal(root.get("gender"),studentForm.getGender()));
				}
				// 查询条件：地址
				if (!StringUtils.isBlank(studentForm.getAddress())){
					predicateList.add(cb.like(root.get("address"), "%" + studentForm.getAddress() + "%"));
				}
				// 查询条件：联系电话
				if (!StringUtils.isBlank(studentForm.getTelephone())){
					predicateList.add(cb.like(root.get("telephone"), "%" + studentForm.getTelephone() + "%"));
				}

				// 查询条件：邮箱
				if (!StringUtils.isBlank(studentForm.getEmail())){
					predicateList.add(cb.like(root.get("email"), "%" + studentForm.getEmail() + "%"));
				}

				// 查询条件：审核人名称
				if (!StringUtils.isBlank(studentForm.getAuditorDisplayName())){
					List<UserEntity> userList = userService.findByDisplayName(studentForm.getAuditorDisplayName());
					List<Integer> userIds = userList.stream().map(UserEntity::getId).collect(Collectors.toList());
					if (!CollectionUtils.isEmpty(userIds)){
						CriteriaBuilder.In in = cb.in(root.get("auditorId"));
						for (Integer userId : userIds){
							in.value(userId);
						}
						predicateList.add(in);
					}
				}
				// 查询条件：认证状态 0 待审核  1 通过  2 拒绝
				if (studentForm.getAuthorizeStatus() != null){
					predicateList.add(cb.equal(root.get("authorizeStatus"),studentForm.getAuthorizeStatus()));
				}
				query.distinct(true);

				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};


		Page<StudentsEntity> all = studentsDao.findAll(specification, pageable);

		PageData<StudentsEntity> pageData = new PageData<>();
		pageData.setCurrentPage(studentForm.getCurrentPage());
		pageData.setPageSize(studentForm.getPageSize());
		pageData.setTotal((int) all.getTotalElements());
		pageData.setTotalPage(all.getTotalPages());
		List<StudentsEntity> studentEntityList = all.getContent();
		if (!CollectionUtils.isEmpty(studentEntityList)) {
			for (StudentsEntity student : studentEntityList){
				assembleStudent(student);
			}
		}
		pageData.setList(studentEntityList);

		return pageData;
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public StudentsEntity getById(Integer id) throws SubsidizeSystemException{
		if (id == null){
			log.error("getById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getById id is null!");
		}
		StudentsEntity entity = getAllById(id);
		if (null == entity || entity.getStatus() == StatusEnum.DELETED.code){
			log.info("getById student is not exist!");
			throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"学生不存在");
		}
		return entity;
	}

	/**
	 * @Description 根据名称查询学生
	 * @Author byfan
	 * @Date 2022/4/9 18:11
	 * @param name
	 * @return java.util.List<com.byfan.subsidizesystem.model.StudentsEntity>
	 * @throws SubsidizeSystemException
	 */
	@Override
	public List<StudentsEntity> findByName(String name) throws SubsidizeSystemException {
		if (StringUtils.isBlank(name)){
			log.error("findByName name is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"name is null");
		}
		return studentsDao.findByName(name);
	}

	/**
	 * @param id
	 * @return com.byfan.subsidizesystem.model.StudentsEntity
	 * @throws SubsidizeSystemException
	 * @Description 根据id查询删除和未删除的学生
	 * @Author byfan
	 * @Date 2022/4/10 17:30
	 */
	@Override
	public StudentsEntity getAllById(Integer id) throws SubsidizeSystemException {
		if (id == null){
			log.error("getAllById id is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"getAllById id is null!");
		}
		Optional<StudentsEntity> optional = studentsDao.findById(id);
		StudentsEntity student = null;
		if (optional.isPresent()){
			student = assembleStudent(optional.get());
		}
		return assembleStudent(student);
	}

	/**
	 * @Description 审核学生认证
	 * @Author byfan
	 * @Date 2022/4/14 16:07
	 * @param studentId
	 * @param authorizeStatus
	 * @param auditorId
	 * @return com.byfan.subsidizesystem.model.StudentsEntity
	 * @throws SubsidizeSystemException
	 */
	@Override
	public StudentsEntity checkStudentApprove(Integer studentId, Integer authorizeStatus, Integer auditorId) throws SubsidizeSystemException {
		if (studentId == null || authorizeStatus == null || auditorId == null){
			log.error("checkStudentApprove studentId or authorizeStatus or auditorId is null!");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"studentId or authorizeStatus or auditorId is null!");
		}
		StudentsEntity entity = studentsDao.getById(studentId);
		entity.setAuthorizeStatus(authorizeStatus);
		entity.setAuditorId(auditorId);
		StudentsEntity save = studentsDao.save(entity);
		return assembleStudent(save);
	}

	/**
	 * @Description 根据用户id查询认证信息
	 * @Author byfan
	 * @Date 2022/4/22 13:58
	 * @param userId
	 * @return com.byfan.subsidizesystem.model.StudentsEntity
	 * @throws SubsidizeSystemException
	 */
	@Override
	public StudentsEntity getByUserId(Integer userId) throws SubsidizeSystemException {
		if (userId == null){
			log.error("getByUserId userId is null");
		}
		StudentsEntity students = studentsDao.findAllByUserId(userId);
		if (students != null){
			students = assembleStudent(students);
		}
		return students;
	}

	/**
	 * @Description 组装学生信息
	 * @Author byfan
	 * @Date 2022/4/14 12:46
	 * @param student
	 * @return com.byfan.subsidizesystem.model.SchoolEntity
	 * @throws SubsidizeSystemException
	 */
	public StudentsEntity assembleStudent(StudentsEntity student) throws SubsidizeSystemException {
		String[] split = student.getImages().split(",");
		student.setImageList(Arrays.asList(split));

		UserEntity user = userService.getAllById(student.getUserId());
		if (user == null){
			student.setUserDisplayName("暂无");
		} else {
			student.setUserDisplayName(user.getDisplayName());
		}

		UserEntity auditor = userService.getAllById(student.getAuditorId());
		if (auditor == null){
			student.setAuditorName("暂无");
		}else {
			student.setAuditorName(auditor.getDisplayName());
		}
		return student;
	}
}