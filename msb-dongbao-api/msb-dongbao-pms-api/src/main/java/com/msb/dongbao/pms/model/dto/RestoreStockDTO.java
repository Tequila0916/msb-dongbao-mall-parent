package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/8/13:54
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "RestoreStockVO恢复库存VO",description = "恢复库存VO")
public class RestoreStockDTO implements Serializable {

    @ApiModelProperty(value = "SKU主键id")
    private Long id;

    @ApiModelProperty(value = "可恢复库存数量")
    private Integer restoreNum;
}
