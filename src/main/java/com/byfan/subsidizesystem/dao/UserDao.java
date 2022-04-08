package com.byfan.subsidizesystem.dao;

import com.byfan.subsidizesystem.model.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Description User持久层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
public interface UserDao extends BaseRepository<UserEntity,Integer> {

    @Transactional
    @Modifying
    @Query("update UserEntity ue set ue.status=:status where ue.id=:id")
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);


    @Query("select ue from UserEntity ue where ue.userName=:userName and ue.status=1")
    List<UserEntity> findByUserName(@Param("userName") String userName);
}
