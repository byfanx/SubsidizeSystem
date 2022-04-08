package com.byfan.subsidizesystem.form;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: PageForm
 * @Description: 分页查询
 * @Author: byfan
 * @Date: 2022/4/7 22:22
 */
@ApiModel(value = "分页数据")
@Data
public class PageForm {
    @ApiModelProperty(value = "当前页（默认第1页）")
    private Integer currentPage = 1;
    @ApiModelProperty(value = "每页数量（默认每页20条）")
    private Integer pageSize = 20;

    public Integer getCurrentPage(){
        return currentPage <= 0 ? 1 : currentPage;
    }
}
