package com.byfan.subsidizesystem.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: QueryRecruitForm
 * @Description: 查询招聘条件
 * @Author: byfan
 * @Date: 2022/4/11 22:43
 */
@ApiModel(value = "查询招聘条件")
@Data
public class QueryRecruitForm extends PageForm{
    @ApiModelProperty(value = "招聘者id（如果根据id查询，则一定要传招聘级别 level）")
    private Integer userId;

    @ApiModelProperty("招聘单位")
    private String recruitmentUnit;

    @ApiModelProperty(value = "招聘级别 3 系统 5 学校")
    private Integer level;

    @ApiModelProperty("审核人名称")
    private String auditorDisplayName;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "岗位")
    private String position;

    @ApiModelProperty(value = "薪资")
    private String salary;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "审核状态 0 待审核  1 通过  2 拒绝")
    private Integer authorizeStatus;
}
