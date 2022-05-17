package com.byfan.subsidizesystem.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName: StudentBean
 * @Description:
 * @Author: byfan
 * @Date: 2022/4/14 11:52
 */
@ApiModel(value = "学生类")
@Data
@ToString
public class StudentBean {
    @ApiModelProperty(value = "用户昵称（新增时不用传）")
    private String userDisplayName;

    @ApiModelProperty(value = "审核人名称（新增时不用传）")
    private String auditorName;

    @ApiModelProperty(value = "图片列表（新增时不用传）")
    private List<String> imageList;
}
