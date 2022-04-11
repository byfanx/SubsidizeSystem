package com.byfan.subsidizesystem.dao;

import com.byfan.subsidizesystem.model.ContributionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Description Contribution持久层
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
public interface ContributionDao extends BaseRepository<ContributionEntity,Integer> {

    @Query("select ce from ContributionEntity ce where ce.userId=:userId and ce.status=1")
    Page<ContributionEntity> findByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Query("select ce from ContributionEntity ce where ce.payee=:payee and ce.status=1")
    Page<ContributionEntity> findByPayeeId(@Param("payee") Integer payeeId, Pageable pageable);
}
