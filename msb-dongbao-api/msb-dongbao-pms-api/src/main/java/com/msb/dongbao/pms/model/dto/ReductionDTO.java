package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/7/22/11:28
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@ApiModel(value = "SKU满减后实体信息")
@Data
@EqualsAndHashCode(callSuper = false)
public class ReductionDTO implements Serializable {

    @ApiModelProperty(value = "SKU_ID")
    private Long skuId;

    @ApiModelProperty(value = "满减id")
    private Long reductionId;

    @ApiModelProperty(value = "商品减少金额")
    private BigDecimal reducePrice;

    @ApiModelProperty(value = "原价")
    private BigDecimal originPrice;

    @ApiModelProperty(value = "优惠价")
    private BigDecimal discountPrice;
}
