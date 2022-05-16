package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * SKU 页面展示实体
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
@ApiModel(value="SkuStock页面展示实体", description="SKU页面展示实体")
public class SkuStockDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "SKU主键id",hidden = true)
    private Long id;

    @ApiModelProperty(value = "SKU编号")
    private String skuNo;

    @ApiModelProperty(value = "商品id",hidden = true)
    private Long relProductId;

    @ApiModelProperty(value = "一级类目")
    private Long relCategory1Id;

    @ApiModelProperty(value = "二级类目")
    private Long relCategory2Id;

    @ApiModelProperty(value = "三级类目")
    private Long relCategory3Id;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "卖点")
    private String sellPoint;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "库存数量")
    private Integer num;

    @ApiModelProperty(value = "商品照片")
    private String image;

    @ApiModelProperty(value = "规格集合")
    private String spec;

    @ApiModelProperty(value = "规格哈希值")
    private String specHash;

    @ApiModelProperty(value = "规格选项id集合")
    private String optionIds;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "是否默认为该商品的SKU")
    private Boolean defaultFlag;

    @ApiModelProperty(value = "是否可以预减库存")
    private Boolean preCut;

    @ApiModelProperty(value = "预减库存响应信息")
    private String msg;

    @ApiModelProperty(value = "满减id")
    private Long reductionId;

    @ApiModelProperty(value = "商品减少金额")
    private BigDecimal reducePrice;

    @ApiModelProperty(value = "优惠价")
    private BigDecimal discountPrice;

    @ApiModelProperty(value = "规格分类信息")
    private List<SpecTypeDTO> specTypeList;

}
