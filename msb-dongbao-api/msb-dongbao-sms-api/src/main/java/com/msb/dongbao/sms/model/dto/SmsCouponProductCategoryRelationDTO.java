package com.msb.dongbao.sms.model.dto;

import com.msb.dongbao.sms.model.entity.SmsCouponProductCategoryRelation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *  优惠券和商品分类的关系传输实体
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-17
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value="优惠券和商品分类的关系传输实体", description="优惠券和商品分类的关系传输实体")
public class SmsCouponProductCategoryRelationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "商品分类id")
    private Long productCategoryId;

    @ApiModelProperty(value = "商品分类名称")
    private String productCategoryName;

    @ApiModelProperty(value = "父分类名称")
    private String parentCategoryName;

    public SmsCouponProductCategoryRelationDTO(SmsCouponProductCategoryRelation entity){
        this.id  = entity.getId();
        this.couponId = entity.getCouponId();
        this.parentCategoryName = entity.getParentCategoryName();
        this.productCategoryId = entity.getProductCategoryId();
        this.productCategoryName = entity.getProductCategoryName();
    }



}
