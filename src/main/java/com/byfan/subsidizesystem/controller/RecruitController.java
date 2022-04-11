package com.byfan.subsidizesystem.controller;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.BaseResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.RecruitEntity;
import com.byfan.subsidizesystem.model.SchoolEntity;
import com.byfan.subsidizesystem.service.RecruitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 招聘控制层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Api(tags = "招聘接口")
@CrossOrigin
@RestController
@RequestMapping("/api/recruit")
public class RecruitController {

	@Autowired
	private RecruitService recruitService;

	/**
	 * 新增/修改
	 * @param recruit
	 * @return 
	 */
	@ApiOperation("新增/修改招聘信息")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public BaseResponse<RecruitEntity> save(RecruitEntity recruit) {
		BaseResponse<RecruitEntity> response = new BaseResponse();
		try {
			RecruitEntity r = recruitService.save(recruit);
			response.setData(r);
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
	 * 根据id删除招聘信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id删除招聘信息")
	@RequestMapping(value = "/deleteById",method = RequestMethod.DELETE)
	public BaseResponse<Void> deleteById(Integer id) {
		BaseResponse<Void> response = new BaseResponse();
		try {
			recruitService.deleteById(id);
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
	@ApiOperation("查询全部招聘信息")
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
	public BaseResponse<List<RecruitEntity>> getAll() {
		BaseResponse<List<RecruitEntity>> response = new BaseResponse();
		try {
			List<RecruitEntity> all = recruitService.getAll();
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
	 * 根据id查询招聘信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id查询招聘信息")
	@RequestMapping(value = "/getById",method = RequestMethod.GET)
	public BaseResponse<RecruitEntity> getById(Integer id) {
		BaseResponse<RecruitEntity> response = new BaseResponse();
		try {
			RecruitEntity recruit = recruitService.getById(id);
			response.setData(recruit);
			response.setCode(CommonResponse.OK.code);
			return response;
		} catch (SubsidizeSystemException e) {
			log.error("getById is except ,e: ", e);
			response.setCode(e.getErrorCode());
			response.setMsg(e.getMessage());
			return response;
		}
	}

	@ApiOperation("审核招聘信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "recruitId", value = "招聘信息id", paramType = "query", required = true, dataType = "int"),
			@ApiImplicitParam(name = "status", value = "审核状态", paramType = "query", required = true, dataType = "int"),
	})
	@RequestMapping(value = "/checkRecruitApprove",method = RequestMethod.GET)
	public BaseResponse<RecruitEntity> checkRecruitApprove(Integer recruitId, Integer status) {
		BaseResponse<RecruitEntity> response = new BaseResponse();
		return response;
	}

}