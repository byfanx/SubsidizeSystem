package com.byfan.subsidizesystem.controller;

import com.byfan.subsidizesystem.bean.ContributionBean;
import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.BaseResponse;
import com.byfan.subsidizesystem.common.PageData;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.form.QueryContributionForm;
import com.byfan.subsidizesystem.model.ContributionEntity;
import com.byfan.subsidizesystem.model.RecruitEntity;
import com.byfan.subsidizesystem.service.ContributionService;
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
 * @Description 捐款控制层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Api(tags = "捐款接口")
@CrossOrigin
@RestController
@RequestMapping("/api/contribution")
public class ContributionController {

	@Autowired
	private ContributionService contributionService;

	/**
	 * 新增/修改
	 * @param contribution
	 * @return 
	 */
	@ApiOperation("新增/修改捐款信息")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public BaseResponse<ContributionEntity> save(ContributionEntity contribution) {
		BaseResponse<ContributionEntity> response = new BaseResponse();
		try {
			ContributionEntity c = contributionService.save(contribution);
			response.setData(c);
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
	 * 根据id删除捐款信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id删除捐款信息")
	@RequestMapping(value = "/deleteById",method = RequestMethod.DELETE)
	public BaseResponse<Void> deleteById(Integer id) {
		BaseResponse<Void> response = new BaseResponse();
		try {
			contributionService.deleteById(id);
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
	@ApiOperation("查询全部捐款信息")
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
	public BaseResponse<PageData<ContributionBean>> getAll(QueryContributionForm queryContributionForm) {
		BaseResponse<PageData<ContributionBean>> response = new BaseResponse();
		try {
			PageData<ContributionBean> all = contributionService.findByQuery(queryContributionForm);
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
	 * 根据id查询捐款信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id查询捐款信息")
	@RequestMapping(value = "/getById",method = RequestMethod.GET)
	public BaseResponse<ContributionBean> getById(Integer id) {
		BaseResponse<ContributionBean> response = new BaseResponse();
		try {
			ContributionBean contribution = contributionService.getById(id);
			response.setData(contribution);
			response.setCode(CommonResponse.OK.code);
			return response;
		} catch (SubsidizeSystemException e) {
			log.error("getById is except ,e: ", e);
			response.setCode(e.getErrorCode());
			response.setMsg(e.getMessage());
			return response;
		}
	}

	@ApiOperation("审核捐款信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "contributionId", value = "捐款信息id", paramType = "query", required = true, dataType = "int"),
			@ApiImplicitParam(name = "authorizeStatus", value = "审核状态  0 待审核  1 通过  2 拒绝", paramType = "query", required = true, dataType = "int"),
			@ApiImplicitParam(name = "auditorId", value = "审核人id", paramType = "query", required = true, dataType = "int"),
	})
	@RequestMapping(value = "/checkContributionApprove",method = RequestMethod.GET)
	public BaseResponse<ContributionBean> checkRecruitApprove(Integer contributionId, Integer authorizeStatus, Integer auditorId) {
		BaseResponse<ContributionBean> response = new BaseResponse();
		try{
			ContributionBean contribution = contributionService.checkRecruitApprove(contributionId, authorizeStatus, auditorId);
			response.setData(contribution);
			response.setCode(CommonResponse.OK.code);
			return response;
		}catch (SubsidizeSystemException e){
			log.error("checkContributionApprove is except ,e: ", e);
			response.setCode(e.getErrorCode());
			response.setMsg(e.getMessage());
			return response;
		}
	}

	@ApiOperation("根据捐款人id获取捐信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "捐款人id", paramType = "query", required = true, dataType = "int"),
			@ApiImplicitParam(name = "currentPage", value = "当前页（默认第1页）", paramType = "query", defaultValue = "1", required = false, dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "每页数量（默认每页20条）", paramType = "query", defaultValue = "20", required = false, dataType = "int"),
	})
	@RequestMapping(value = "/getByUserId",method = RequestMethod.GET)
	public BaseResponse<PageData<ContributionBean>> getByUserId(Integer userId, Integer currentPage, Integer pageSize) {
		BaseResponse<PageData<ContributionBean>> response = new BaseResponse();
		try{
			PageData<ContributionBean> pageData = contributionService.getByUserId(userId, currentPage, pageSize);
			response.setData(pageData);
			response.setCode(CommonResponse.OK.code);
			return response;
		}catch (SubsidizeSystemException e){
			log.error("checkContributionApprove is except ,e: ", e);
			response.setCode(e.getErrorCode());
			response.setMsg(e.getMessage());
			return response;
		}
	}

	@ApiOperation("根据被捐款人id获取捐信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "payeeId", value = "捐款人id", paramType = "query", required = true, dataType = "int"),
			@ApiImplicitParam(name = "currentPage", value = "当前页（默认第1页）", paramType = "query", defaultValue = "1", required = false, dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "每页数量（默认每页20条）", paramType = "query", defaultValue = "20", required = false, dataType = "int"),
	})
	@RequestMapping(value = "/getByPayeeId",method = RequestMethod.GET)
	public BaseResponse<PageData<ContributionBean>> getByPayeeId(Integer payeeId, Integer currentPage, Integer pageSize) {
		BaseResponse<PageData<ContributionBean>> response = new BaseResponse<>();
		try{
			PageData<ContributionBean> pageData = contributionService.getByPayeeId(payeeId, currentPage, pageSize);
			response.setData(pageData);
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