package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.common.StatusEnum;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryNoticeForm;
import com.byfan.subsidizesystem.model.NoticeEntity;
import com.byfan.subsidizesystem.dao.NoticeDao;
import com.byfan.subsidizesystem.model.UserEntity;
import com.byfan.subsidizesystem.service.NoticeService;
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
 * @Description Notice ServiceImpl层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Autowired
	private UserService userService;
	/**
	 * 新增/保存
	 * @param notice
	 * @return 
	 * @throws SubsidizeSystemException
	 */
	@Override
	public NoticeEntity save(NoticeEntity notice) throws SubsidizeSystemException {
		if (notice == null){
			log.error("save notice is null");
			throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"notice is null");
		}
		if (notice.getId() == null){
			notice.setStatus(StatusEnum.USING.code);
		}else {
			NoticeEntity byId = getById(notice.getId());
			MyUtils.copyPropertiesIgnoreNull(notice, byId);
			notice=byId;
		}
		return assembleNotice(noticeDao.save(notice));
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
		NoticeEntity byId = getById(id);
		byId.setStatus(StatusEnum.DELETED.code);
		noticeDao.save(byId);
	}

	/**
	 * 查询全部
	 * @return
	 * @throws SubsidizeSystemException
	 */
	@Override
	public PageData<NoticeEntity> findByQuery(QueryNoticeForm noticeForm) throws SubsidizeSystemException {
		Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
		noticeForm.setCurrentPage(noticeForm.getCurrentPage() <= 0 ? 1 : noticeForm.getCurrentPage());
		noticeForm.setPageSize(noticeForm.getPageSize() <= 0 ? 1 : noticeForm.getPageSize());
		Pageable pageable = PageRequest.of(noticeForm.getCurrentPage()-1, noticeForm.getPageSize(),sort);

		Specification specification = new Specification() {
			@SneakyThrows
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<>();
				predicateList.add(cb.equal(root.get("status"),StatusEnum.USING.code));

				// 查询条件：发布者名称
				if (!StringUtils.isBlank(noticeForm.getUserDisplayName())){
					List<UserEntity> userList = userService.findByDisplayName(noticeForm.getUserDisplayName());
					List<Integer> userIds = userList.stream().map(UserEntity::getId).collect(Collectors.toList());
					if (!CollectionUtils.isEmpty(userIds)){
						CriteriaBuilder.In in = cb.in(root.get("userId"));
						for (Integer userId : userIds){
							in.value(userId);
						}
						predicateList.add(in);
					}
				}
				// 查询条件：标题
				if (!StringUtils.isBlank(noticeForm.getTitle())){
					predicateList.add(cb.like(root.get("title"),"%" + noticeForm.getTitle() + "%"));
				}
				// 查询条件：发布者内容
				if (!StringUtils.isBlank(noticeForm.getContent())){
					predicateList.add(cb.like(root.get("content"),"%" + noticeForm.getContent() + "%"));
				}

				query.distinct(true);
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};

		Page<NoticeEntity> all = noticeDao.findAll(specification, pageable);

		PageData<NoticeEntity> pageData = new PageData<>();
		pageData.setCurrentPage(noticeForm.getCurrentPage());
		pageData.setPageSize(noticeForm.getPageSize());
		pageData.setTotalPage(all.getTotalPages());
		pageData.setTotal((int) all.getTotalElements());

		List<NoticeEntity> list = all.getContent();
		if (!CollectionUtils.isEmpty(list)){
			for (NoticeEntity entity : list){
				assembleNotice(entity);
			}
		}
		pageData.setList(list);

		return pageData;
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
		NoticeEntity notice = null;
		if (optional.isPresent()){
			if (optional.get().getStatus() == StatusEnum.DELETED.code){
				log.error("getById notice is not exist");
				throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"notice is not exist");
			}else {
				notice = optional.get();
			}
		}
		return notice;
	}


	/**
	 * @Description 组装公告信息
	 * @Author byfan
	 * @Date 2022/4/15 16:52
	 * @param notice
	 * @return com.byfan.subsidizesystem.model.NoticeEntity
	 * @throws
	 */
	private NoticeEntity assembleNotice(NoticeEntity notice) throws SubsidizeSystemException {
		UserEntity user = userService.getAllById(notice.getId());
		if (user == null){
			notice.setUserDisplayName("暂无");
		}else {
			notice.setUserDisplayName(user.getDisplayName());
		}
		return notice;
	}
}