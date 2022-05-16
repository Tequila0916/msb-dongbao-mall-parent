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
 * @date: 2020/6/21/18:03
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "规格分类",description = "规格分类")
public class SpecTypeDTO implements Serializable {

    @ApiModelProperty(value = "")
    private String specType;

    @ApiModelProperty(value = "规格选项信息")
    private List<SpecOptionNameDTO> list;
}
