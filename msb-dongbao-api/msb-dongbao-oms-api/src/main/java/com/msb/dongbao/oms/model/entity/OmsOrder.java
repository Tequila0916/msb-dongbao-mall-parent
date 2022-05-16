package com.msb.dongbao.oms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 订单实体
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
@TableName("oms_order")
public class OmsOrder extends Model<OmsOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 父订单编号 暂时用不到
     */
    private String parentOrderNumber;

    /**
     * 提交时间
     */
    private Long gmtCreate;

    /**
     * 订单标题
     */
    private String title;

    /**
     * 商家编号
     */
    private String merchantId;

    /**
     * 订单金额
     */
    private BigDecimal totalAmount;

    /**
     * 实际支付金额
     */
    private BigDecimal payAmount;

    /**
     * 支付方式：1->支付宝；2->微信
     */
    private Integer payType;

    /**
     * 订单来源：0->PC订单；1->app订单
     */
    private Integer sourceType;

    /**
     * 订单状态
     * {@link com.msb.dongbao.oms.model.enums.OrderStatusEnum}
     */
    private Integer orderStatus;

    /**
     * 订单类型：0->正常订单；1->秒杀订单
     */
    private Integer orderType;

    /**
     * 自动确认时间（天）
     */
    private Integer autoConfirmDay;

    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     */
    private Integer billType;

    /**
     * 发票抬头
     */
    private String billHeader;

    /**
     * 发票内容
     */
    private String billContent;

    /**
     * 收票人电话
     */
    private String billReceiverPhone;

    /**
     * 收票人邮箱
     */
    private String billReceiverEmail;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 收货人邮编
     */
    private String receiverPostCode;

    /**
     * 省份/直辖市
     */
    private String receiverProvince;

    /**
     * 城市
     */
    private String receiverCity;

    /**
     * 区
     */
    private String receiverRegion;

    /**
     * 详细地址
     */
    private String receiverDetailAddress;

    /**
     * 订单备注
     */
    private String note;

    /**
     * 确认收货状态：0->未确认；1->已确认
     */
    private Integer confirmStatus;

    /**
     * 删除状态：0->未删除；1->已删除
     */
    private Integer deleteStatus;

    /**
     * 支付时间
     */
    private Long paymentTime;

    /**
     * 发货时间
     */
    private Long deliveryTime;

    /**
     * 确认收货时间
     */
    private Long receiveTime;

    /**
     * 最新评价时间
     */
    private Long commentTime;

    /**
     * 修改时间
     */
    private Long gmtModified;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String modifiedBy;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
