package com.byfan.subsidizesystem.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName: RecruitBean
 * @Description:
 * @Author: byfan
 * @Date: 2022/4/11 22:04
 */
@ApiModel("招聘信息返回对象")
@Data
@ToString
public class RecruitBean {
    @ApiModelProperty("招聘单位（新增时不用传）")
    private String recruitmentUnit;

    @ApiModelProperty("审核人名称（新增时不用传）")
    private String auditorName;

}
