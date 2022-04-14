package com.byfan.subsidizesystem.controller;

import com.byfan.subsidizesystem.bean.ContributionBean;
import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.BaseResponse;
import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QuerySchoolForm;
import com.byfan.subsidizesystem.model.SchoolEntity;
import com.byfan.subsidizesystem.model.StudentsEntity;
import com.byfan.subsidizesystem.service.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 学校控制层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Api(tags = "学校接口")
@CrossOrigin
@RestController
@RequestMapping("/api/school")
public class SchoolController {

	@Autowired
	private SchoolService schoolService;

	/**
	 * 新增/修改
	 * @param school
	 * @return 
	 */
	@ApiOperation("新增/修改学校信息")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public BaseResponse<SchoolEntity> save(SchoolEntity school) {
		BaseResponse<SchoolEntity> response = new BaseResponse();
		try {
			SchoolEntity s = schoolService.save(school);
			response.setData(s);
			response.setCode(CommonResponse.OK.code);
			return response;
		} catch (SubsidizeSystemException e) {
			log.error("save is except ,e: ", e);
			response.setCode(e.getErrorCode());
			response.setMsg(e.getMessage());
			return response;
		}
	}

	/**
	 * 根据id删除学校信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id删除学校信息")
	@RequestMapping(value = "/deleteById",method = RequestMethod.DELETE)
	public BaseResponse<Void> deleteById(Integer id) {
		BaseResponse<Void> response = new BaseResponse();
		try {
			schoolService.deleteById(id);
			response.setCode(CommonResponse.OK.code);
			return response;
		} catch (SubsidizeSystemException e) {
			log.error("deleteById is except ,e: ", e);
			response.setCode(e.getErrorCode());
			response.setMsg(e.getMessage());
			return response;
		}
	}

	/**
	 * 查询全部
	 * @return 
	 */
	@ApiOperation("查询全部学校信息")
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
	public BaseResponse<PageData<SchoolEntity>> getAll(QuerySchoolForm schoolForm) {
		BaseResponse<PageData<SchoolEntity>> response = new BaseResponse();
		try {
			PageData<SchoolEntity> all = schoolService.findByQuery(schoolForm);
			response.setData(all);
			response.setCode(CommonResponse.OK.code);
			return response;
		} catch (SubsidizeSystemException e) {
			log.error("getAll is except ,e: ", e);
			response.setCode(e.getErrorCode());
			response.setMsg(e.getMessage());
			return response;
		}
	}

	/**
	 * 根据id查询学校信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id查询学校信息")
	@RequestMapping(value = "/getById",method = RequestMethod.GET)
	public BaseResponse<SchoolEntity> getById(Integer id) {
		BaseResponse<SchoolEntity> response = new BaseResponse();
		try {
			SchoolEntity school = schoolService.getById(id);
			response.setData(school);
			response.setCode(CommonResponse.OK.code);
			return response;
		} catch (SubsidizeSystemException e) {
			log.error("getById is except ,e: ", e);
			response.setCode(e.getErrorCode());
			response.setMsg(e.getMessage());
			return response;
		}
	}

	@ApiOperation("审核学校认证")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "schoolId", value = "学校id", paramType = "query", required = true, dataType = "int"),
			@ApiImplicitParam(name = "authorizeStatus", value = "审核状态  0 待审核  1 通过  2 拒绝", paramType = "query", required = true, dataType = "int"),
			@ApiImplicitParam(name = "auditorId", value = "审核人id", paramType = "query", required = true, dataType = "int"),
	})
	@RequestMapping(value = "/checkSchoolApprove",method = RequestMethod.GET)
	public BaseResponse<SchoolEntity> checkSchoolApprove(Integer schoolId, Integer authorizeStatus, Integer auditorId) {
		BaseResponse<SchoolEntity> response = new BaseResponse();
		try{
			SchoolEntity school = schoolService.checkSchoolApprove(schoolId, authorizeStatus, auditorId);
			response.setData(school);
			response.setCode(CommonResponse.OK.code);
			return response;
		}catch (SubsidizeSystemException e){
			log.error("checkContributionApprove is except ,e: ", e);
			response.setCode(e.getErrorCode());
			response.setMsg(e.getMessage());
			return response;
		}
	}

}