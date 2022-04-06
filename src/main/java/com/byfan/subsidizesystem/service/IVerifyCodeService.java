package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.bean.VerifyCode;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;

import java.io.OutputStream;

/**
 * @ClassName: IVerifyCodeService
 * @Description: 验证码service层
 * @Author: byfan
 * @Date: 2022/4/6 21:12
 */
public interface IVerifyCodeService {
    /**
     * 生成验证码并返回code，将图片写的os中
     *
     * @param width
     * @param height
     * @param os
     * @return
     * @throws com.byfan.subsidizesystem.exception.SubsidizeSystemException
     */
    String generate(int width, int height, OutputStream os) throws SubsidizeSystemException;

    /**
     * 生成验证码对象
     *
     * @param width
     * @param height
     * @return
     * @throws com.byfan.subsidizesystem.exception.SubsidizeSystemException
     */
    VerifyCode generate(int width, int height) throws SubsidizeSystemException;
}
