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
 * @date: 2020/6/8/11:09
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "类目VO",description = "新增类目VO")
public class CategorySpecDTO implements Serializable {

    @ApiModelProperty(value = "父类目名称")
    private String name;

    @ApiModelProperty(value = "子类目")
    private List<CategorySpecDTO> childCateLists;

}
