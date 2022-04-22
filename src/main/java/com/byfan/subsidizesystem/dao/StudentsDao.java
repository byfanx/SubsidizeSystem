package com.byfan.subsidizesystem.dao;

import com.byfan.subsidizesystem.model.StudentsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description Students持久层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
public interface StudentsDao extends BaseRepository<StudentsEntity,Integer> {

    @Query("select se from StudentsEntity se where se.name like concat('%', :name, '%')  and se.status=1")
    List<StudentsEntity> findByName(@Param("name") String name);

    StudentsEntity findAllByUserId(@Param("userId") Integer userId);
}
