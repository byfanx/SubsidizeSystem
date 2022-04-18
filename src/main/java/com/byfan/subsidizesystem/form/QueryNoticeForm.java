package com.byfan.subsidizesystem.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName: QueryNoticeForm
 * @Description: 公告查询条件
 * @Author: byfan
 * @Date: 2022/4/15 17:36
 */
@ApiModel(value = "公告查询条件")
@Data
@ToString
public class QueryNoticeForm extends PageForm{
    @ApiModelProperty(value = "发布者名称")
    private String userDisplayName;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "发布内容")
    private String content;
}
