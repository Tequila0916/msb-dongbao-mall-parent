package com.msb.dongbao.pay.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 退款记录表
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-09
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@TableName("pay_refund")
public class PayRefund implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用id
     */
    private String relAppId;

    /**
     * 上游应用的订单编号
     */
    private String relOrderNo;


    /**
     * 上游应用的退款订单编号
     */
    private String relRefundOrderNo;

    /**
     * 支付系统交易唯一编码
     */
    private String relTransactionNo;

    /**
     * 第三方交易号
     */
    private String tradeNo;

    /**
     * 支付平台生成的唯一退款单号
     */
    private String refundNo;

    /**
     * 支付渠道ID
     */
    private Integer payChannelId;

    /**
     * 退款金额
     */
    private BigDecimal refundFee;

    /**
     * 退款理由
     */
    private String refundReason;

    /**
     * 退款方式：1-自动原路返回; 2-人工打款
     */
    private Integer refundMethod;

    /**
     * 退款状态: 1-退款处理中; 2-退款成功; 3-退款失败;4-取消退款
     */
    private Integer refundStatus;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 更新时间
     */
    private Long gmtModified;

    /**
     * 创建人uid
     */
    private String createUid;

    /**
     * 创建人昵称
     */
    private String createUname;

    /**
     * 更新人uid
     */
    private String modifiedUid;

    /**
     * 更新人昵称
     */
    private String modifiedUname;

    /**
     * 是否删除:0-未删除;1-删除
     */
    private Integer enable;

    /**
     * 商户id
     */
    private String merchantId;


}
