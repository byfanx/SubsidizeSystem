package com.byfan.subsidizesystem.controller;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.BaseResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.ContributionEntity;
import com.byfan.subsidizesystem.service.ContributionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
	public BaseResponse<List<ContributionEntity>> getAll() {
		BaseResponse<List<ContributionEntity>> response = new BaseResponse();
		try {
			List<ContributionEntity> all = contributionService.getAll();
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
	public BaseResponse<ContributionEntity> getById(Integer id) {
		BaseResponse<ContributionEntity> response = new BaseResponse();
		try {
			ContributionEntity contribution = contributionService.getById(id);
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

}