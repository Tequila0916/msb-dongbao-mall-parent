package com.msb.dongbao.oms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p>
 *  订单传输实体
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
@ApiModel(value="订单传输实体", description="订单传输实体")
public class OmsOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    @ApiModelProperty(value = "父订单编号")
    private String parentOrderNumber;

    @ApiModelProperty(value = "提交时间")
    private Long gmtCreate;

    @ApiModelProperty(value = "订单标题")
    private String title;

    @ApiModelProperty(value = "商家编号")
    private String merchantId;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "实际支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "支付方式：0->未支付；1->支付宝；2->微信")
    private Integer payType;

    @ApiModelProperty(value = "订单来源：0->PC订单；1->app订单")
    private Integer sourceType;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单类型：0->正常订单；1->秒杀订单")
    private Integer orderType;

    @ApiModelProperty(value = "自动确认时间（天）")
    private Integer autoConfirmDay;

    @ApiModelProperty(value = "发票类型：0->不开发票；1->电子发票；2->纸质发票")
    private Integer billType;

    @ApiModelProperty(value = "发票抬头")
    private String billHeader;

    @ApiModelProperty(value = "发票内容")
    private String billContent;

    @ApiModelProperty(value = "收票人电话")
    private String billReceiverPhone;

    @ApiModelProperty(value = "收票人邮箱")
    private String billReceiverEmail;

    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    @ApiModelProperty(value = "收货人电话")
    private String receiverPhone;

    @ApiModelProperty(value = "收货人邮编")
    private String receiverPostCode;

    @ApiModelProperty(value = "省份/直辖市")
    private String receiverProvince;

    @ApiModelProperty(value = "城市")
    private String receiverCity;

    @ApiModelProperty(value = "区")
    private String receiverRegion;

    @ApiModelProperty(value = "详细地址")
    private String receiverDetailAddress;

    @ApiModelProperty(value = "订单备注")
    private String note;

    @ApiModelProperty(value = "确认收货状态：0->未确认；1->已确认")
    private Integer confirmStatus;

    @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
    private Integer deleteStatus;

    @ApiModelProperty(value = "支付时间")
    private Long paymentTime;

    @ApiModelProperty(value = "发货时间")
    private Long deliveryTime;

    @ApiModelProperty(value = "确认收货时间")
    private Long receiveTime;

    @ApiModelProperty(value = "最新评价时间")
    private Long commentTime;

    @ApiModelProperty(value = "修改时间")
    private Long gmtModified;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    private String modifiedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        OmsOrderDTO that = (OmsOrderDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(orderNumber, that.orderNumber) &&
                Objects.equals(parentOrderNumber, that.parentOrderNumber) &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(title, that.title) &&
                Objects.equals(merchantId, that.merchantId) &&
                Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(payAmount, that.payAmount) &&
                Objects.equals(payType, that.payType) &&
                Objects.equals(sourceType, that.sourceType) &&
                Objects.equals(orderStatus, that.orderStatus) &&
                Objects.equals(orderType, that.orderType) &&
                Objects.equals(autoConfirmDay, that.autoConfirmDay) &&
                Objects.equals(billType, that.billType) &&
                Objects.equals(billHeader, that.billHeader) &&
                Objects.equals(billContent, that.billContent) &&
                Objects.equals(billReceiverPhone, that.billReceiverPhone) &&
                Objects.equals(billReceiverEmail, that.billReceiverEmail) &&
                Objects.equals(receiverName, that.receiverName) &&
                Objects.equals(receiverPhone, that.receiverPhone) &&
                Objects.equals(receiverPostCode, that.receiverPostCode) &&
                Objects.equals(receiverProvince, that.receiverProvince) &&
                Objects.equals(receiverCity, that.receiverCity) &&
                Objects.equals(receiverRegion, that.receiverRegion) &&
                Objects.equals(receiverDetailAddress, that.receiverDetailAddress) &&
                Objects.equals(note, that.note) &&
                Objects.equals(confirmStatus, that.confirmStatus) &&
                Objects.equals(deleteStatus, that.deleteStatus) &&
                Objects.equals(paymentTime, that.paymentTime) &&
                Objects.equals(deliveryTime, that.deliveryTime) &&
                Objects.equals(receiveTime, that.receiveTime) &&
                Objects.equals(commentTime, that.commentTime) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(createBy, that.createBy) &&
                Objects.equals(modifiedBy, that.modifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, orderNumber, parentOrderNumber, gmtCreate, title, merchantId, totalAmount, payAmount, payType, sourceType, orderStatus, orderType, autoConfirmDay, billType, billHeader, billContent, billReceiverPhone, billReceiverEmail, receiverName, receiverPhone, receiverPostCode, receiverProvince, receiverCity, receiverRegion, receiverDetailAddress, note, confirmStatus, deleteStatus, paymentTime, deliveryTime, receiveTime, commentTime, gmtModified, createBy, modifiedBy);
    }
}
