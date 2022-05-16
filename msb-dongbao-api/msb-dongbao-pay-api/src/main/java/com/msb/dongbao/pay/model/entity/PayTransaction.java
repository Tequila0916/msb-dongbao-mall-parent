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
 * 支付流水表
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
@TableName("pay_transaction")
public class PayTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用id
     */
    private String relAppId;

    /**
     * 应用方订单编号
     */
    private String relOrderNo;

    /**
     * 支付渠道id，用来识别支付方式，如：支付宝、微信、Paypal等
     */
    private Integer payChannelId;

    /**
     * 交易唯一编码
     */
    private String transactionNo;

    /**
     * 第三方流水号
     */
    private String tradeNo;

    /**
     * 订单总金额，总共10位，小数位数存2位
     */
    private BigDecimal totalFee;

    /**
     * 实际支付订单金额，总共10位，小数位数存2位
     */
    private BigDecimal payFee;

    /**
     * 订单过期时间
     */
    private Long expireTime;

    /**
     * 第三方支付成功的时间，同步接收
     */
    private Long paymentTime;

    /**
     * 收到异步通知的时间
     */
    private Long notifyTime;

    /**
     * 支付状态:0-等待支付中;1-待付款完成;2-支付成功;3:交易已关闭;-1:支付失败
     */
    private Integer orderStatus;

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
    private String merchantId = "1";


}
