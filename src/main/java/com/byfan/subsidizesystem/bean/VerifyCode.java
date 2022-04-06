package com.byfan.subsidizesystem.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: VerifyCode
 * @Description: 验证码类
 * @Author: byfan
 * @Date: 2022/4/6 21:10
 */
@ApiModel(value = "验证码实体类", description = "验证码Entity")
@Data
public class VerifyCode {
    // 验证码
    @ApiModelProperty(value = "验证码")
    private String code;
    // 图片地址
    @ApiModelProperty(value = "图片地址")
    private String url;
    // 图片字节ß
    @ApiModelProperty(value = "字节流")
    private byte[] imgBytes;
}
