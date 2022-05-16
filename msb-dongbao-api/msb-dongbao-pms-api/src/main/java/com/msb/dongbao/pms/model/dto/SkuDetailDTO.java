package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/21/21:05
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "SKU详细信息",description = "SKU详细信息")
public class SkuDetailDTO implements Serializable {

    @ApiModelProperty(value = "规格选项id集合")
    String optionIds;

    @ApiModelProperty(value = "商品id")
    Integer productId;
}
