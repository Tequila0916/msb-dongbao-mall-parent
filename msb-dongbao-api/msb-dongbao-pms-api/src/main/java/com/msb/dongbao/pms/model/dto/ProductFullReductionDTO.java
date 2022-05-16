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
 * @date: 2020/8/19/22:51
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value="ProductFullReduction传输实体", description="商品满减表传输实体")
public class ProductFullReductionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "商品编号")
    private String productNo;

    @ApiModelProperty(value = "商品满足金额")
    private BigDecimal fullPrice;

    @ApiModelProperty(value = "商品减少金额")
    private BigDecimal reducePrice;

    @ApiModelProperty(value = "开始时间")
    private Long startTime;

    @ApiModelProperty(value = "结束时间")
    private Long endTime;
}
