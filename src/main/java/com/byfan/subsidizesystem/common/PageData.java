package com.byfan.subsidizesystem.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 分页数据格式
 * @Author: byfan
 * @Date: 2022/04/05 16:09
 */
@ApiModel(value = "分页查询的数据")
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageData<T> {
    @ApiModelProperty(value = "当前页")
    Integer currentPage = 0;
    @ApiModelProperty(value = "每页数量")
    Integer pageSize = 20;
    @ApiModelProperty(value = "数据总量")
    Integer total = 0;
    @ApiModelProperty(value = "总页数")
    Integer totalPage = 0;
    @ApiModelProperty(value = "数据列表")
    List<T> list = new ArrayList<>();

    public Integer getTotalPage(){
        return total % pageSize == 0 ? (total / pageSize) : ((totalPage / pageSize) + 1);
    }
}