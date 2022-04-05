package com.byfan.subsidizesystem.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 分页数据格式
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageData<T> {
    Integer currentPage = 0;
    Integer pageSize = 20;
    Integer total = 0;
    Integer totalPage = 0;
    List<T> list = new ArrayList<>();

    public Integer getTotalPage(){
        return total % pageSize == 0 ? (total / pageSize) : ((totalPage / pageSize) + 1);
    }
}