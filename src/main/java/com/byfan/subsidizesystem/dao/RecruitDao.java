package com.byfan.subsidizesystem.dao;

import com.byfan.subsidizesystem.model.RecruitEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @Description Recruit持久层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
public interface RecruitDao extends BaseRepository<RecruitEntity,Integer> {
    @Transactional
    @Modifying
    @Query("update RecruitEntity re set re.status=:status where re.userId=:userId and re.level=:level")
    void deleteByUserIdAndLevel(@Param("userId") Integer userId, @Param("level") Integer level, @Param("status") Integer status);
}
