package com.byfan.subsidizesystem.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
 * @ClassName: QueryContributionForm
 * @Description: 查询捐款的条件
 * @Author: byfan
 * @Date: 2022/4/8 18:29
 */
@ApiModel(value = "查询捐款的条件")
@Data
@ToString
public class QueryContributionForm extends PageForm{

    @ApiModelProperty(value = "捐款人id")
    private Integer userId;

    @ApiModelProperty(value = "捐款人名称")
    private String userDisplayName;

    @ApiModelProperty(value = "被捐款对象名称")
    private String payeeDisplayName;

    @ApiModelProperty(value = "被捐款对象身份  3 平台 4 贫困生 5 贫困学校", required = true)
    private Integer approveIdentity;

    @ApiModelProperty(value = "审核人名称")
    private String auditorDisplayName;

    @ApiModelProperty(value = "审核状态 0 待审核  1 通过  2 拒绝")
    private Integer authorizeStatus;

}
