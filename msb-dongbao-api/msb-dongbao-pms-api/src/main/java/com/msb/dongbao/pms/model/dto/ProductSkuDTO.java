package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/8/11:26
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@ApiModel(value = "ProductSkuVO商品VO",description = "同步商品信息")
@EqualsAndHashCode(callSuper = false)
public class ProductSkuDTO implements Serializable {

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商户id")
    private Long relTenantId;

    @ApiModelProperty(value = "一级类目")
    private Long relCategory1Id;

    @ApiModelProperty(value = "二级类目")
    private Long relCategory2Id;

    @ApiModelProperty(value = "三级类目")
    private Long relCategory3Id;

    @ApiModelProperty(value = "规格id集合")
    private String specOptions;

    @ApiModelProperty(value = "sku信息")
    private List<SkuStockDTO> skuList;

}
