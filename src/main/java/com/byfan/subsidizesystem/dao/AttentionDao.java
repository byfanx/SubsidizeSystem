package com.byfan.subsidizesystem.dao;

import com.byfan.subsidizesystem.model.AttentionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @Description Attention持久层
 * @Author: byfan
 * @Date: 2022/04/06 23:30
 */
public interface AttentionDao extends BaseRepository<AttentionEntity,Integer> {
    @Transactional
    @Modifying
    @Query("update AttentionEntity ae set ae.status=:status where ae.userId=:userId")
    void deleteByUserId(@Param("userId") Integer userId, @Param("status") Integer status);
}
