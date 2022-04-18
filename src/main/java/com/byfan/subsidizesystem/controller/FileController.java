package com.byfan.subsidizesystem.controller;

import com.byfan.subsidizesystem.common.BaseResponse;
import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.service.IFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: FileController
 * @Description: 文件接口
 * @Author: byfan
 * @Date: 2022/4/18 22:43
 */
@Slf4j
@Api(tags = "文件接口")
@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private IFileService fileService;

    @ApiOperation("上传文件")
    @ApiImplicitParam(name = "file", value = "文件(支持的格式有：jpg/jpeg/png)，最大10M", paramType = "query", required = true)
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public BaseResponse upload(@RequestBody MultipartFile file) throws SubsidizeSystemException {
        BaseResponse response = new BaseResponse();
        if (file.getSize()/Math.pow(1024,2) > 10){
            log.error("upload file is too large");
            throw new SubsidizeSystemException(CommonResponse.FILE_TOO_LARGE, "文件大小超出限制");
        }
        try {
            String path = fileService.upload(file);
            response.setData(path);
            response.setCode(CommonResponse.OK.code);
            response.setMsg("success");
            return response;
        } catch (SubsidizeSystemException e) {
            log.error("upload except ", e);
            response.setCode(e.getErrorCode());
            response.setMsg(e.getMessage());
            return response;
        }
    }

    @ApiOperation("删除文件")
    @ApiImplicitParam(name = "path", value = "文件路径(eg:xxx/xxx.png)", paramType = "query", required = true)
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public BaseResponse delete(String path) throws SubsidizeSystemException {
        BaseResponse response = new BaseResponse();
        try {
            fileService.delete(path);
            response.setCode(CommonResponse.OK.code);
            response.setMsg("success");
            return response;
        } catch (SubsidizeSystemException e) {
            log.error("delete except ", e);
            response.setCode(e.getErrorCode());
            response.setMsg(e.getMessage());
            return response;
        }
    }
}
