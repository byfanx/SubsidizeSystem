package com.byfan.subsidizesystem.common;

/**
 * @ClassName: AuthenticationEnum
 * @Description: 认证的身份
 * @Author: byfan
 * @Date: 2022/4/7 17:36
 */
public enum AuthenticationEnum {
    ADMIN(1, "管理员"),
    GENERAL_USER(2, "普通用户"),
    SYSTEM(3,  "系统"),
    STUDENT(4, "学生"),
    SCHOOL(5, "学校");

    public Integer code;
    public String msg;

    AuthenticationEnum(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
