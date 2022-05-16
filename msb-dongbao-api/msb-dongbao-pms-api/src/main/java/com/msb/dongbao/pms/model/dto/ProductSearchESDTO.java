package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@ApiModel(value = "ProductSearchESVO",description = "商品搜索条件")
@EqualsAndHashCode
public class ProductSearchESDTO {


    @ApiModelProperty(value = "商品主键ID")
    @Id
    @Field(value = "product_id", type = FieldType.Long)
    private Long id;

    @ApiModelProperty(value = "商品名称")
    @Field(value = "product_name", type = FieldType.Text)
    private String productName;

    @ApiModelProperty(value = "一级类目")
    @Field(value = "rel_category1_id", type = FieldType.Long)
    private Long relCategory1Id;

    @ApiModelProperty(value = "二级类目")
    @Field(value = "rel_category2_id", type = FieldType.Long)
    private Long relCategory2Id;

    @ApiModelProperty(value = "三级类目")
    @Field(value = "rel_category3_id", type = FieldType.Long)
    private Long relCategory3Id;

    @ApiModelProperty(value = "销售数量")
    @Field(value = "sales_num", type = FieldType.Long)
    private Integer salesNum;

    @ApiModelProperty(value = "商城价")
    @Field(value = "price", type = FieldType.Float)
    private Float price;

    @ApiModelProperty(value = "商品编号")
    @Field(value = "product_no", type = FieldType.Keyword)
    private String productNo;

    @ApiModelProperty(value = "是否可用")
    @Field(value = "enabled", type = FieldType.Boolean)
    private Boolean enabled;

    @Field(value = "spec_options", type = FieldType.Nested)
    @ApiModelProperty(value = "规格选项id集合")
    private List<String> specOptions;

    @ApiModelProperty(value = "价格上限")
    @Field(value = "price", type = FieldType.Float)
    private Float priceUpper;

    @ApiModelProperty(value = "价格下限")
    @Field(value = "price", type = FieldType.Float)
    private Float priceLower;

    @ApiModelProperty(value = "排序字段")
    private String sortField;

    @ApiModelProperty(value = "升序-false， 降序-true")
    private Boolean sortDesc;





}
