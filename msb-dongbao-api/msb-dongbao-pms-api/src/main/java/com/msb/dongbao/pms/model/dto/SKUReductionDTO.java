package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/7/21/22:42
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "SKU满减信息",description = "SKU满减信息")
public class SKUReductionDTO implements Serializable {

    @ApiModelProperty(value = "SKU_ID")
    private Long skuId;

    @ApiModelProperty(value = "下单数量")
    private Integer num;
}
