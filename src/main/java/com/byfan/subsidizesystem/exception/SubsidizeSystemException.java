package com.byfan.subsidizesystem.exception;

import com.byfan.subsidizesystem.common.CommonResponse;

/**
 * @Author: byfan
 * @Description 服务端抛出异常
 * @Version 1.0
 * @Date: 2022/04/05 16:09
 */
public class SubsidizeSystemException extends Exception{

    private Integer errorCode;

    public SubsidizeSystemException(CommonResponse commonResponse) {
        super(commonResponse.msg);
        this.errorCode = commonResponse.code;
    }

    public SubsidizeSystemException(String message) {
        super(message);
        this.errorCode = CommonResponse.UNKNOWN_ERROR.code;
    }

    public SubsidizeSystemException(CommonResponse commonResponse, String message) {
        super(message);
        this.errorCode = commonResponse.code;
    }


    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

}