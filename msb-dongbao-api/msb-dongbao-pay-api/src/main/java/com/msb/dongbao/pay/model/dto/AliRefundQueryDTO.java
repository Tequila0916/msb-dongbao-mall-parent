package com.msb.dongbao.pay.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 退款查询实体
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月08日 15时18分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Builder
@Data
public class AliRefundQueryDTO implements Serializable {
    /**
     * 下单时的商户订单号，不能和 trade_no同时为空
     */
    private String out_trade_no;
    /**
     * 支付宝交易号，不能和 out_trade_no 同时为空
     */
    private String trade_no;
    /**
     * 退款编号
     */
    private String out_request_no;
    /**
     * 双联通过该参数指定需要查询的交易所属收单机构的pid
     */
    private int org_pid;
    /**
     * 查询选项，商户通过上送该参数来定制同步需要额外返回的信息字段，数组格式
     */
    private String[] query_options;
}
