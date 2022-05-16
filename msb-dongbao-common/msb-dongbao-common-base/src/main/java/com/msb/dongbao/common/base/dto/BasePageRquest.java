package com.msb.dongbao.common.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: Li Ming
 * @date: 2019年02月23 00时25分
 */
@Data
@ApiModel(value="基础分页请求")
public class BasePageRquest implements Serializable {

    @ApiModelProperty(name = "length",dataType = "int",value = "分页大小")
    @NotNull
    @Min(0)
    private Integer length;

    @ApiModelProperty(name = "start",dataType = "int",value = "查询记录起始下标")
    private Integer start;

    @ApiModelProperty(name = "pageIndex",dataType = "int",value = "分页下标")
    @NotNull
    @Min(0)
    private Integer pageIndex;

    @ApiModelProperty(name = "orderColum",dataType = "string",value = "排序字段")
    private String  orderColum;

    @ApiModelProperty(name = "orderAsc",dataType = "int",value = "排序方向")
    private Integer orderAsc;


}
