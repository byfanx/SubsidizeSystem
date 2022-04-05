package com.byfan.subsidizesystem.controller;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.BaseResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.StudentsEntity;
import com.byfan.subsidizesystem.service.StudentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 学生控制层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Api(tags = "学生接口")
@RestController
@RequestMapping("/api/students")
public class StudentsController {

	@Autowired
	private StudentsService studentsService;

	/**
	 * 新增/修改
	 * @param students
	 * @return 
	 */
	@ApiOperation("新增/修改学生信息")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public BaseResponse<StudentsEntity> save(StudentsEntity students) {
		BaseResponse<StudentsEntity> response = new BaseResponse();
		try {
			StudentsEntity s = studentsService.save(students);
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
	 * 根据id删除学生信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id删除学生信息")
	@RequestMapping(value = "/deleteById",method = RequestMethod.DELETE)
	public BaseResponse<Void> deleteById(Integer id) {
		BaseResponse<Void> response = new BaseResponse();
		try {
			studentsService.deleteById(id);
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
	@ApiOperation("查询全部学生信息")
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
	public BaseResponse<List<StudentsEntity>> getAll() {
		BaseResponse<List<StudentsEntity>> response = new BaseResponse();
		try {
			List<StudentsEntity> all = studentsService.getAll();
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
	 * 根据id查询学生信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id查询学生信息")
	@RequestMapping(value = "/getById",method = RequestMethod.GET)
	public BaseResponse<StudentsEntity> getById(Integer id) {
		BaseResponse<StudentsEntity> response = new BaseResponse();
		try {
			StudentsEntity students = studentsService.getById(id);
			response.setData(students);
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