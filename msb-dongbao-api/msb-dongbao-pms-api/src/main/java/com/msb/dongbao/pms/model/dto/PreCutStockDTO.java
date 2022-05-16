package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/8/13:51
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "PreCutStockVO预减库存VO",description = "预减库存VO")
public class PreCutStockDTO implements Serializable {

    @ApiModelProperty(value = "SKU主键id")
    @NotBlank(message = "SKU主键id不能为空")
    private Long id;

    @ApiModelProperty(value = "下单数量")
    @NotBlank(message = "下单数量不能为空")
    private Integer preCutNum;
}
