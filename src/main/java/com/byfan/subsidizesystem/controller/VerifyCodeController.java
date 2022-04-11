package com.byfan.subsidizesystem.controller;

import com.byfan.subsidizesystem.bean.VerifyCode;
import com.byfan.subsidizesystem.common.BaseResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.service.IVerifyCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: VerifyCodeController
 * @Description:
 * @Author: byfan
 * @Date: 2022/4/6 21:28
 */
@Slf4j
@Api(tags = "验证码接口")
@RestController
@RequestMapping("/api/verifycode")
public class VerifyCodeController {
    @Autowired
    private IVerifyCodeService verifyCodeService;

    @ApiOperation(value = "获取验证码")
    @CrossOrigin  // 解决跨域
    @GetMapping()
    public BaseResponse<VerifyCode> getVerifyCode(){
        BaseResponse<VerifyCode> response = new BaseResponse<VerifyCode>();
        try {
            VerifyCode verifyCode = verifyCodeService.generate(80, 28);
            response.setData(verifyCode);
            return response;
        }catch (SubsidizeSystemException sse){
            log.error("getVerifyCode is except ,e: ", sse);
            response.setCode(sse.getErrorCode());
            response.setMsg(sse.getMessage());
            return response;
        }
    }


}
