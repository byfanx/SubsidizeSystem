package com.byfan.subsidizesystem.bean;

import com.byfan.subsidizesystem.model.SchoolEntity;
import com.byfan.subsidizesystem.model.StudentsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: AuthenticationInfoBean
 * @Description: 返回用户的认证信息
 * @Author: byfan
 * @Date: 2022/4/22 14:28
 */
@ApiModel("用户认证信息")
@Data
public class AuthenticationInfoBean {
    @ApiModelProperty("认证的身份 4 学生, 5 学校")
    private Integer authentication;

    @ApiModelProperty("学生信息")
    private StudentsEntity student;

    @ApiModelProperty("学校信息")
    private SchoolEntity school;

}
