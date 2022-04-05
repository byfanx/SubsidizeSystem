package com.byfan.subsidizesystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @Description 基础Dao类
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@NoRepositoryBean
public interface BaseRepository<T,PK extends Serializable> extends JpaRepository<T,PK> {
}