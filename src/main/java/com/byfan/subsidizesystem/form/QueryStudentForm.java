package com.byfan.subsidizesystem.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;

/**
 * @ClassName: QueryStudentForm
 * @Description: 学生的查询条件
 * @Author: byfan
 * @Date: 2022/4/14 12:54
 */
@ApiModel(value = "学生的查询条件")
@Data
@ToString
public class QueryStudentForm extends PageForm{
    @ApiModelProperty(value = "用户昵称")
    private String userDisplayName;

    @ApiModelProperty(value = "学生名称")
    private String studentName;

    @ApiModelProperty(value = "性别 1 男  2 女")
    private Integer gender;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "联系电话")
    private String telephone;

    @ApiModelProperty(value = "审核人名称")
    private String auditorName;

    @ApiModelProperty(value = "认证状态 0 待审核  1 通过  2 拒绝")
    private Integer authorizeStatus;
}
