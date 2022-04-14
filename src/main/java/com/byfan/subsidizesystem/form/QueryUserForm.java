package com.byfan.subsidizesystem.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @ClassName: QueryUserForm
 * @Description: 查询用户条件
 * @Author: byfan
 * @Date: 2022/4/7 22:30
 */
@ApiModel(value = "查询用户条件")
@Data
public class QueryUserForm extends PageForm{

    @ApiModelProperty(value = "登录名")
    private String userName;

    @ApiModelProperty(value = "展示名")
    private String displayName;

    @ApiModelProperty(value = "用户角色 1 管理员 2 普通用户")
    private Integer role;

    @ApiModelProperty(value = "性别  1 男  2 女")
    private Integer gender;

    @ApiModelProperty(value = "手机号")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "地址")
    private String address;
}
