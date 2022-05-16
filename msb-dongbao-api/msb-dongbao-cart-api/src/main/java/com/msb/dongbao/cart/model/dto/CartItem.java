package com.msb.dongbao.cart.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车商品元素
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月18日 09时16分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@ApiModel(value="购物车商品元素", description="购物车商品元素")
public class CartItem implements Serializable {
    private static final long serialVersionUID = 4110741463154569712L;
    /** 购物车中某个商品的唯一标记 */
    @ApiModelProperty(value = "购物车中某个商品的唯一标记")
    private String itemId;
    @ApiModelProperty(value = "商品 SKU ID")
    private Long skuId;
    /** 商品 SKU 编号 */
    @ApiModelProperty(value = "商品 SKU 编号")
    private String skuNo;
    /** 商品SPU编号 */
    @ApiModelProperty(value = "商品SPU编号")
    private String spuNo;
    /** 商品SKU的规格集合，json */
    @ApiModelProperty(value = "商品SKU的规格集合")
    private String skuSpec;
    /** 商品SKU数量 */
    @ApiModelProperty(value = "商品SKU数量")
    private Integer amount;
    /** 商品SKU状态:0-有库存，上架状态;1-无库存，下架状态*/
    @ApiModelProperty(value = "商品SKU状态")
    private Byte status;
    /** 记录加车时候的销售价格 */
    @ApiModelProperty(value = "记录加车时候的销售价格")
    private BigDecimal salePrice;
    /** 指定价格加购物车 */
    @ApiModelProperty(value = "指定价格加购物车")
    private BigDecimal specialPrice;
    /** 是否免邮,0-否，1-是 */
    @ApiModelProperty(value = "是否免邮")
    private Byte postFree;
    @ApiModelProperty(value = "默认商品照片")
    private String image;
    @ApiModelProperty(value = "商品标题")
    private String title;
    @ApiModelProperty(value = "对应商品Id")
    private Long relProductId;
    @ApiModelProperty(value = "对应商品的三级分类ID")
    private Long categoryId;
    /** 添加时间 */
    private Long addTime;
    /** 更新时间 */
    private Long updateTime;
    /** 绑定的父item id */
    @Deprecated
    private String parentItemId;
    /** 绑定的订单号*/
    @Deprecated
    private String orderId;
    /** 商品来源渠道 */
    @Deprecated
    private String channel;



}
