package com.msb.dongbao.sms.model.dto;

import com.msb.dongbao.sms.model.entity.SmsCoupon;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * <p>
 *  优惠券传输实体
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
@ApiModel(value="优惠券传输实体", description="优惠券传输实体")
public class SmsCouponDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券")
    private Integer type;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "使用平台：0->全部；1->移动；2->PC")
    private Integer platform;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "每人限领张数")
    private Integer perLimit;

    @ApiModelProperty(value = "使用门槛；0表示无门槛")
    private BigDecimal minPoint;

    @ApiModelProperty(value = "开始使用时间")
    private Long startTime;

    @ApiModelProperty(value = "结束使用时间")
    private Long endTime;

    @ApiModelProperty(value = "使用类型：0->全场通用；1->指定分类；2->指定商品")
    private Integer useType;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "发行数量")
    private Integer publishCount;

    @ApiModelProperty(value = "已使用数量")
    private Integer useCount;

    @ApiModelProperty(value = "领取数量")
    private Integer receiveCount;

    @ApiModelProperty(value = "可以领取的日期")
    private Long enableTime;

    @ApiModelProperty(value = "优惠码")
    private String code;

    @ApiModelProperty(value = "可领取的会员类型：0->无限制")
    private Integer memberLevel;

    @ApiModelProperty(value = "商品SPU和优惠券关系实体")
    private SmsCouponProductRelationDTO couponProductRelationDTO;

    @ApiModelProperty(value = "优惠券和商品分类关系实体")
    private SmsCouponProductCategoryRelationDTO couponProductCategoryRelationDTO;

    public SmsCouponDTO(){

    }

    public SmsCouponDTO(SmsCoupon entity){
        this.id = entity.getId();
        this.amount = entity.getAmount();
        this.code = entity.getCode();
        this.count = entity.getCount();
        this.enableTime = entity.getEnableTime();
        this.endTime = entity.getEndTime();
        this.memberLevel = entity.getMemberLevel();
        this.minPoint = entity.getMinPoint();
        this.name = entity.getName();
        this.note = entity.getNote();
        this.perLimit = entity.getPerLimit();
        this.platform = entity.getPlatform();
        this.publishCount = entity.getPublishCount();
        this.receiveCount = entity.getReceiveCount();
        this.startTime = entity.getStartTime();
        this.type = entity.getType();
        this.useCount = entity.getUseCount();
        this.useType = entity.getUseType();
    }



}
