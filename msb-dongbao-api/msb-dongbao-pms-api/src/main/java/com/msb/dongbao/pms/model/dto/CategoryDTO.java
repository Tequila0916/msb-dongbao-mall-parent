package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品类目 页面展示实体
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
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Category实体", description="商品类目实体")
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类目主键ID")
    private Long id;

    @ApiModelProperty(value = "类目父ID")
    private Long parentId;

    @ApiModelProperty(value = "类目名称")
    private String name;

    @ApiModelProperty(value = "子类目")
    private List<CategoryDTO> list;
}
