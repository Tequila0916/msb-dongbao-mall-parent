package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/21/18:03
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@ApiModel(value = "规格选项名称信息",description = "规格选型名称信息")
@EqualsAndHashCode(callSuper = false)
public class SpecOptionNameDTO implements Serializable {

    @ApiModelProperty(value = "规格名称")
    private String name;

    @ApiModelProperty(value = "规格选项名称")
    private String value;

}
