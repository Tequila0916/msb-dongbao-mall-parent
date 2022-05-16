package com.msb.dongbao.sms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * sku对应的优惠券信息
 *
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 10:54
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@ApiModel(value = "sku对应的优惠券信息",description = "sku对应的优惠券信息")
public class SmsCouponBySpuDTO {
    @ApiModelProperty(value = "商品Id")
    private Long skuId;

    @ApiModelProperty(value = "优惠券信息")
    private List<SmsCouponDTO> coupons;
}
