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
 * @date: 2020/8/19/23:35
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value="Specification传输实体", description="规格传输实体")
public class SpecificationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规格名称")
    private String name;

    @ApiModelProperty(value = "类目id")
    private Long relCategoryId;

    @ApiModelProperty(value = "规格类别id")
    private Long relSpecTypeId;

    @ApiModelProperty(value = "规格选项")
    private List<SpecificationOptionDTO> optionDTOS;
}
