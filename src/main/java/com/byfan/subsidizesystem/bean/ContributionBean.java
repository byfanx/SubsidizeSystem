package com.byfan.subsidizesystem.bean;

import com.byfan.subsidizesystem.model.ContributionEntity;
import com.byfan.subsidizesystem.model.SchoolEntity;
import com.byfan.subsidizesystem.model.StudentsEntity;
import com.byfan.subsidizesystem.model.UserEntity;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName: ContributionBean
 * @Description:
 * @Author: byfan
 * @Date: 2022/4/8 17:54
 */
@Data
@ToString
public class ContributionBean extends ContributionEntity {
    // 捐款人
    private UserEntity user;
    // 收款学生
    private StudentsEntity students;
    // 收款学校
    private SchoolEntity school;
    // 审核人
    private UserEntity authorUser;
}
