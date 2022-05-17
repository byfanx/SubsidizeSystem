package com.byfan.subsidizesystem.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;

/**
 * @ClassName: QuerySchoolForm
 * @Description: 学校的查询条件
 * @Author: byfan
 * @Date: 2022/4/13 22:44
 */
@ApiModel(value = "学校的查询条件")
@Data
@ToString
public class QuerySchoolForm extends PageForm{
    @ApiModelProperty(value = "用户昵称")
    private String userDisplayName;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "学校地址")
    private String address;

    @ApiModelProperty(value = "学校联系人")
    private String contacts;

    @ApiModelProperty(value = "学校联系电话")
    private String telephone;

    @ApiModelProperty(value = "审核人名称")
    private String auditorDisplayName;

    @ApiModelProperty(value = "认证状态 0 待审核  1 通过  2 拒绝")
    private Integer authorizeStatus;
}
