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
 * 商品表 传输实体
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
@ApiModel(value="Product传输实体", description="商品表传输实体")
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品主键ID")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "租户id")
    private Long relTenantId;

    @ApiModelProperty(value = "默认SKU")
    private Long relDefaultSkuId;

    @ApiModelProperty(value = "一级类目")
    private Long relCategory1Id;

    @ApiModelProperty(value = "二级类目")
    private Long relCategory2Id;

    @ApiModelProperty(value = "三级类目")
    private Long relCategory3Id;

    @ApiModelProperty(value = "规格选项id集合")
    private String specOptions;

    @ApiModelProperty(value = "商城价")
    private BigDecimal price;

    @ApiModelProperty(value = "商品图片")
    private String defaultPic;

    @ApiModelProperty(value = "商品组图,加上默认主图，最多允许5张图，逗号分割")
    private String albumPics;

    @ApiModelProperty(value = "商品编号")
    private String productNo;

    @ApiModelProperty(value = "商品详情描述")
    private String detailDesc;

    @ApiModelProperty(value = "商品详情web端页面，基本以图为主，富文本HTML样式")
    private String detailHtml;

    @ApiModelProperty(value = "销量")
    private Integer salesNum;

    @ApiModelProperty(value = "SKU信息")
    private List<SkuStockDTO> skuList;

    @ApiModelProperty(value = "规格选项")
    private List<SpecificationDTO> specList;

    @ApiModelProperty(value = "商铺名称")
    private String tenantName;
}
