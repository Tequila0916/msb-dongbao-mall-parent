package com.msb.dongbao.sms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品分类 对应的 全部优惠券信息
 *
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 10:36
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@ApiModel(value = "商品分类对应的优惠券信息", description = "商品分类对应的优惠券信息")
public class SmsCouponByPcDTO {

    @ApiModelProperty(value = "商品分类Id")
    private Long productCategoryId;

    @ApiModelProperty(value = "商品分类名称")
    private String productCategoryName;

    @ApiModelProperty(value = "优惠券信息")
    private List<SmsCouponHaveDescriptionDTO> coupons;
}
