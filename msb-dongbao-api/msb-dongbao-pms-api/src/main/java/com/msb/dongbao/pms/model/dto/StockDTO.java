package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/9/11:36
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "恢复库存ResponseVO",description = "恢复库存返回VO")
public class StockDTO implements Serializable{

    @ApiModelProperty(value = "原库存量")
    private Integer originNum;

    @ApiModelProperty(value = "恢复后库存量")
    private Integer restoreNum;

    @ApiModelProperty(value = "是否恢复库存成功 true:成功")
    private Boolean flag;
}
