package com.byfan.subsidizesystem.exception;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description 异常统一处理类
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@RestControllerAdvice       // 捕获全局异常
@Order(1)                   // 类加载顺序
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SubsidizeSystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse exception(SubsidizeSystemException e) {
        BaseResponse response = new BaseResponse();
        response.setCode(e.getErrorCode());
        if (StringUtils.isNotEmpty(e.getMessage())) {
            response.setMsg(e.getMessage());
        }
        //打印错误日志，方便跟踪
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        log.warn("Throw exception location: {}  Exception reason: {}",
                stackTraceElement.toString(), response.getMsg());
        return  response;
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse exception(Exception e) {
        log.error("Exception:", e);
        BaseResponse response = new BaseResponse(CommonResponse.UNKNOWN_ERROR);
        return response;
    }
}