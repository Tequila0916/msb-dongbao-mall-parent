package com.msb.dongbao.pms.model.dto;

import com.msb.dongbao.pms.model.entity.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品表 页面展示实体
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
@Document(indexName = "productsearchik", type = "_doc")
public class GoodDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品主键ID")
    @Id
    @Field("product_id")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    @Field("product_name")
    private String productName;

    @ApiModelProperty(value = "一级类目")
    @Field("rel_category1_id")
    private Long relCategory1Id;

    @ApiModelProperty(value = "二级类目")
    @Field("rel_category2_id")
    private Long relCategory2Id;

    @ApiModelProperty(value = "三级类目")
    @Field("rel_category3_id")
    private Long relCategory3Id;

    @ApiModelProperty(value = "销售数量")
    @Field("sales_num")
    private Integer salesNum;

    @ApiModelProperty(value = "商城价")
    @Field("price")
    private Float price;

    @ApiModelProperty(value = "商品图片")
    @Field("default_pic")
    private String defaultPic;

    @ApiModelProperty(value = "商品编号")
    @Field("product_no")
    private String productNo;

    @ApiModelProperty(value = "是否可用")
    @Field("enabled")
    private Boolean enabled;

    @Field("spec_options")
    @ApiModelProperty(value = "规格选项id集合")
    private List<String> specOptions;

    public GoodDTO(){

    }

    public GoodDTO(Product product){
        id = product.getId();
        productName = product.getProductName();
        relCategory1Id = product.getRelCategory1Id();
        relCategory2Id = product.getRelCategory2Id();
        relCategory3Id = product.getRelCategory3Id();
        price = product.getPrice().floatValue();
        defaultPic = product.getDefaultPic();
        productNo = product.getProductNo();
        enabled = product.getEnabled();
        salesNum = product.getSalesNum();
    }

    public GoodDTO(Long id, String productName, Long relCategory1Id, Long relCategory2Id, Long relCategory3Id, Integer salesNum, float price, String defaultPic, String productNo, Boolean enabled) {
        this.id = id;
        this.productName = productName;
        this.relCategory1Id = relCategory1Id;
        this.relCategory2Id = relCategory2Id;
        this.relCategory3Id = relCategory3Id;
        this.salesNum = salesNum;
        this.price = price;
        this.defaultPic = defaultPic;
        this.productNo = productNo;
        this.enabled = enabled;
    }
}
