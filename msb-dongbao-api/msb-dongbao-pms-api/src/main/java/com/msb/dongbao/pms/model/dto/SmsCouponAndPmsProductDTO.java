package com.msb.dongbao.pms.model.dto;

/**
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 14:17
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ApiModel(value="下单使用的数据传输对象,包括优惠券,商品分类和商品的关系", description="下单使用的数据传输对象,包括优惠券,商品分类和商品的关系")
@ToString
public class SmsCouponAndPmsProductDTO {

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "品类id")
    private Long productCategoryId;

    @ApiModelProperty(value = "商品列表")
    List<ProductItemParam> items;

}
