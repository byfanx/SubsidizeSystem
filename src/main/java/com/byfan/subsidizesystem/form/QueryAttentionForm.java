package com.byfan.subsidizesystem.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * @ClassName: QueryAttentionForm
 * @Description: 关注信息查询条件
 * @Author: byfan
 * @Date: 2022/4/21 22:09
 */
@ApiModel("关注信息查询条件")
@Data
public class QueryAttentionForm extends PageForm{
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty("用户名称")
    private String userDisplayName;

    @ApiModelProperty("被关注对象名称")
    private String followedUserName;

    @ApiModelProperty(value = "认证的身份 4 学生  5 学校")
    private Integer approveIdentity;

}
