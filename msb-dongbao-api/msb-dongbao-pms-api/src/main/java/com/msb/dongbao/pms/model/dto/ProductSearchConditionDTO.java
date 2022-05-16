package com.msb.dongbao.pms.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品检索条件
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value="商品检索条件", description="商品检索条件")
@JsonIgnoreProperties(value = {"handler"})
public class ProductSearchConditionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规格项id")
    private Integer specId;

    @ApiModelProperty(value = "规格项名称")
    private String specName;

    @ApiModelProperty(value = "规格项值")
    private List<SpecificationOptionDTO> optionList;

}
