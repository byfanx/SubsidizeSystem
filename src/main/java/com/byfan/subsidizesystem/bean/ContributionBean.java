package com.byfan.subsidizesystem.bean;

import com.byfan.subsidizesystem.model.ContributionEntity;
import com.byfan.subsidizesystem.model.SchoolEntity;
import com.byfan.subsidizesystem.model.StudentsEntity;
import com.byfan.subsidizesystem.model.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName: ContributionBean
 * @Description:
 * @Author: byfan
 * @Date: 2022/4/8 17:54
 */
@ApiModel(value = "捐款信息返回对象")
@Data
@ToString
public class ContributionBean extends ContributionEntity {
    // 捐款人
    @ApiModelProperty(value = "捐款人信息")
    private UserEntity user;
    // 收款学生
    @ApiModelProperty(value = "收款学生信息")
    private StudentsEntity students;
    // 收款学校
    @ApiModelProperty(value = "收款学校信息")
    private SchoolEntity school;
    // 审核人
    @ApiModelProperty(value = "审核人信息")
    private UserEntity authorUser;
}
