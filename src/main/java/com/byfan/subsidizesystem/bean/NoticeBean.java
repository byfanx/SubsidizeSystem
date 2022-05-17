package com.byfan.subsidizesystem.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName: NoticeBean
 * @Description:
 * @Author: byfan
 * @Date: 2022/4/15 16:34
 */
@ApiModel(value = "公告信息返回对象")
@Data
@ToString
public class NoticeBean {
    @ApiModelProperty(value = "发布者名称（新增时不用传）")
    private String userDisplayName;

}
