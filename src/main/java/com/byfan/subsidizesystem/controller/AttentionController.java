package com.byfan.subsidizesystem.controller;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.BaseResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.AttentionEntity;
import com.byfan.subsidizesystem.service.AttentionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 关注信息控制层
 * @Author: byfan
 * @Date: 2022/04/06 23:30
 */
@Slf4j
@Api(tags = "关注信息接口")
@RestController
@RequestMapping("/api/attention")
public class AttentionController {

	@Autowired
	private AttentionService attentionService;

	/**
	 * 新增/修改
	 * @param attention
	 * @return 
	 */
	@ApiOperation("新增/修改关注信息信息")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public BaseResponse<AttentionEntity> save(AttentionEntity attention) {
		BaseResponse<AttentionEntity> response = new BaseResponse();
		try {
			AttentionEntity a = attentionService.save(attention);
			response.setData(a);
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
	 * 根据id删除关注信息信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id删除关注信息信息")
	@RequestMapping(value = "/deleteById",method = RequestMethod.DELETE)
	public BaseResponse<Void> deleteById(Integer id) {
		BaseResponse<Void> response = new BaseResponse();
		try {
			attentionService.deleteById(id);
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
	@ApiOperation("查询全部关注信息信息")
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
	public BaseResponse<List<AttentionEntity>> getAll() {
		BaseResponse<List<AttentionEntity>> response = new BaseResponse();
		try {
			List<AttentionEntity> all = attentionService.getAll();
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
	 * 根据id查询关注信息信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id查询关注信息信息")
	@RequestMapping(value = "/getById",method = RequestMethod.GET)
	public BaseResponse<AttentionEntity> getById(Integer id) {
		BaseResponse<AttentionEntity> response = new BaseResponse();
		try {
			AttentionEntity attention = attentionService.getById(id);
			response.setData(attention);
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