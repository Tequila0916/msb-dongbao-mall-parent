package com.msb.dongbao.cart.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 购物车商品更新实体
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月19日 13时36分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@ApiModel(value="购物车商品更新实体", description="购物车商品更新实体")
public class UpdateCartItem {
    @ApiModelProperty(value = "旧商品规格实体")
    CartItem oldCartItem;
    @ApiModelProperty(value = "新商品规格实体")
    CartItem newCartItem;
}
