package com.byfan.subsidizesystem.dao;

import com.byfan.subsidizesystem.model.SchoolEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description School持久层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
public interface SchoolDao extends BaseRepository<SchoolEntity,Integer> {

    @Query("select se from SchoolEntity se where se.name like concat('%', :name, '%')  and se.status=1")
    List<SchoolEntity> findByName(@Param("name") String name);

    SchoolEntity findAllByUserId(@Param("userId") Integer userId);
}
