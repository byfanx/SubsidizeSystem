package com.byfan.subsidizesystem.controller;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.BaseResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.UserEntity;
import com.byfan.subsidizesystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 用户控制层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Api(tags = "用户接口")
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 新增/修改
	 * @param user
	 * @return 
	 */
	@ApiOperation("新增/修改用户信息")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public BaseResponse<UserEntity> save(UserEntity user) {
		BaseResponse<UserEntity> response = new BaseResponse();
		try {
			UserEntity u = userService.save(user);
			response.setData(u);
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
	 * 根据id删除用户信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id删除用户信息")
	@RequestMapping(value = "/deleteById",method = RequestMethod.DELETE)
	public BaseResponse<Void> deleteById(Integer id) {
		BaseResponse<Void> response = new BaseResponse();
		try {
			userService.deleteById(id);
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
	@ApiOperation("查询全部用户信息")
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
	public BaseResponse<List<UserEntity>> getAll() {
		BaseResponse<List<UserEntity>> response = new BaseResponse();
		try {
			List<UserEntity> all = userService.getAll();
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
	 * 根据id查询用户信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id查询用户信息")
	@RequestMapping(value = "/getById",method = RequestMethod.GET)
	public BaseResponse<UserEntity> getById(Integer id) {
		BaseResponse<UserEntity> response = new BaseResponse();
		try {
			UserEntity user = userService.getById(id);
			response.setData(user);
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