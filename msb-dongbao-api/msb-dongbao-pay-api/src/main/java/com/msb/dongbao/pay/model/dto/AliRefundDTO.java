package com.msb.dongbao.pay.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月08日 15时18分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Builder
@Data
public class AliRefundDTO implements Serializable {
    /**
     * 下单时的商户订单号，不能和 trade_no同时为空
     */
    private String out_trade_no;
    /**
     * 支付宝交易号，不能和 out_trade_no 同时为空
     */
    private String trade_no;
    /**
     * 需要退款的金额
     */
    private String refund_amount;
    /**
     * 退卷原因
     */
    private String refund_reason;
    /**
     * 应用退款编号
     */
    private String out_request_no;
}
