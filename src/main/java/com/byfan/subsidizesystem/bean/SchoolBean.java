package com.byfan.subsidizesystem.bean;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName: SchoolBean
 * @Description:
 * @Author: byfan
 * @Date: 2022/4/10 23:50
 */
@ApiModel("学校类")
@Data
@ToString
public class SchoolBean {
    @ApiModelProperty(value = "用户昵称")
    private String userDisplayNameName;

    @ApiModelProperty(value = "审核人名称")
    private String auditorName;

    @ApiModelProperty(value = "图片列表")
    private List<String> imageList;
}
