package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * Mybatis-plus 代码生成器
 *
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 2020年05月27日 17时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */

@Data
@EqualsAndHashCode
@ApiModel(value="商品传输信息", description="传输实体")
public class ProductItemParam {

    @ApiModelProperty(value = "商品主键")
    @NotNull
    private Long productId ;

    @ApiModelProperty(value = "购买数量")
    @NotNull
    private Integer quantity;
}
