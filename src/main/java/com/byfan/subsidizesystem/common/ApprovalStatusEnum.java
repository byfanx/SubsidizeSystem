package com.byfan.subsidizesystem.common;

/**
 * @ClassName: ApprovalStatusEnum
 * @Description: 数据的审核状态
 * @Author: byfan
 * @Date: 2022/4/7 17:32
 */
public enum ApprovalStatusEnum {
    WAIT(0, "待审核"),
    PASS(1, "通过"),
    REFUSE(2,"拒绝");

    public Integer code;
    public String msg;

    ApprovalStatusEnum(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
