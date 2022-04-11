package com.byfan.subsidizesystem.controller;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.BaseResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.model.NoticeEntity;
import com.byfan.subsidizesystem.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 公告控制层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Slf4j
@Api(tags = "公告接口")
@CrossOrigin
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	/**
	 * 新增/修改
	 * @param notice
	 * @return 
	 */
	@ApiOperation("新增/修改公告信息")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public BaseResponse<NoticeEntity> save(NoticeEntity notice) {
		BaseResponse<NoticeEntity> response = new BaseResponse();
		try {
			NoticeEntity n = noticeService.save(notice);
			response.setData(n);
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
	 * 根据id删除公告信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id删除公告信息")
	@RequestMapping(value = "/deleteById",method = RequestMethod.DELETE)
	public BaseResponse<Void> deleteById(Integer id) {
		BaseResponse<Void> response = new BaseResponse();
		try {
			noticeService.deleteById(id);
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
	@ApiOperation("查询全部公告信息")
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
	public BaseResponse<List<NoticeEntity>> getAll() {
		BaseResponse<List<NoticeEntity>> response = new BaseResponse();
		try {
			List<NoticeEntity> all = noticeService.getAll();
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
	 * 根据id查询公告信息
	 * @param id
	 * @return 
	 */
	@ApiOperation("根据id查询公告信息")
	@RequestMapping(value = "/getById",method = RequestMethod.GET)
	public BaseResponse<NoticeEntity> getById(Integer id) {
		BaseResponse<NoticeEntity> response = new BaseResponse();
		try {
			NoticeEntity notice = noticeService.getById(id);
			response.setData(notice);
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