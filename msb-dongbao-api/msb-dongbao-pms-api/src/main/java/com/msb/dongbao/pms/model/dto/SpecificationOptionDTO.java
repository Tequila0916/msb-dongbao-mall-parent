package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/8/19/23:34
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value="SpecificationOption传输实体", description="规格项传输实体")
public class SpecificationOptionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规格项ID")
    private Long id;

    @ApiModelProperty(value = "规格项名称")
    private String name;

    @ApiModelProperty(value = "规格id")
    private Long relSpecId;

    @ApiModelProperty(value = "是否被默认选中")
    private boolean active;
}
