package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/17/16:33
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "预减库存",description = "预减库存")
public class StockUpdateDTO implements Serializable {

    @ApiModelProperty(value = "预减库存后商品数量")
    private Integer newNum;

    @ApiModelProperty(value = "预减库存后版本号")
    private Integer newVersion;

    @ApiModelProperty(value = "当前版本号")
    private Integer version;

    @ApiModelProperty(value = "SKU_ID")
    private Long id;

}
