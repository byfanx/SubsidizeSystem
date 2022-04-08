package com.byfan.subsidizesystem.common;
/**
 * @Description 数据状态
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
public enum StatusEnum {

    DELETED(0, "删除"),
    USING(1, "正常");

    public Integer code;
    public String msg;

    StatusEnum(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}