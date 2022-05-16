package com.msb.dongbao.common.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zero
 */
@Data
@ApiModel(value = "分页返回实体",description = "分页返回实体")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="当前页码")
    private long number;

    @ApiModelProperty(name="总页数")
    private long totalPages;

    @ApiModelProperty(name="总数")
    private long totalElements;

    @ApiModelProperty(name="起始记录数")
    private long elementsNo;

    @ApiModelProperty(name="查询分页长度")
    private int limit;

    @ApiModelProperty(name="查询结果")
    private List<T> content;

    //@ApiModelProperty(value = "需要高亮的关键词",dataType = "list")
    //private List<String> highlightWords;
}

