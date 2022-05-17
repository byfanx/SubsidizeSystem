package com.byfan.subsidizesystem.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: AttentionBean
 * @Description:
 * @Author: byfan
 * @Date: 2022/4/21 12:55
 */
@ApiModel("关注信息返回对象")
@Data
public class AttentionBean {
    @ApiModelProperty("用户名称（新增时不用传）")
    private String userDisplayName;

    @ApiModelProperty("被关注对象名称（新增时不用传）")
    private String followedUserName;
}
