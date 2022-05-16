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
 * @date: 2020/6/17/16:28
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value="预减库存VO", description="预减库存")
public class CutStockDTO implements Serializable {

    @ApiModelProperty(value = "是否预减库存成功")
    private boolean flag;

    @ApiModelProperty(value = "预减库存成功")
    private List<SkuStockDTO> successList;

    @ApiModelProperty(value = "预减库存失败")
    private List<SkuStockDTO> failList;
}
