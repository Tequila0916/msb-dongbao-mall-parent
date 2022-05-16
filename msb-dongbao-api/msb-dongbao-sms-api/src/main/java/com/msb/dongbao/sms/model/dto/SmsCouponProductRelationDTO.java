package com.msb.dongbao.sms.model.dto;

import com.msb.dongbao.sms.model.entity.SmsCouponProductRelation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *  优惠券和商品的关系传输实体
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-16
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value="优惠券和商品的关系传输实体", description="优惠券和商品的关系传输实体")
public class SmsCouponProductRelationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商品条码")
    private String productSn;


    public SmsCouponProductRelationDTO(SmsCouponProductRelation obj){
        this.id = obj.getId();
        this.couponId = obj.getCouponId();
        this.productId = obj.getProductId();
        this.productName = obj.getProductName();
        this.productSn = obj.getProductSn();
    }




}
