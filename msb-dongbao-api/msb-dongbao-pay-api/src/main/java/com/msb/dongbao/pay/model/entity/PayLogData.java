package com.msb.dongbao.pay.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

/**
 * 支付交易日志数据
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月10日 10时57分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Builder
@Setter
@Getter
@Table("pay_log_data")
public class PayLogData implements Serializable {

    /**
     * 数据库的结构 primary key(merchantId,(order_no,transaction_no));
     */
    /**
     * 分区键
     */
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0, name = "merchant_id")
    private String merchantId;

    /**
     * 主键ID,分布式UUID
     */
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 1, name = "log_id")
    @Indexed
    private String logId;

    /**
     * 应用方订单唯一编号
     */
    @Column(value = "order_no")
    @Indexed
    private String orderNo;

    /**
     * 支付系统交易唯一编号
     */
    @Indexed
    @Column(value = "transaction_no")
    private String transactionNo;

    /**
     * 支付的请求参数
     */
    @Column(value = "request_params")
    private String requestParams;


    /**
     * 日志类型，payment:支付; refund:退款; notify:异步通知; return:同步通知; query:查询
     */
    @Column(value = "log_type")
    private Integer logType;

    /**
     * 创建时间
     */
    @Column(value = "gmt_create")
    private Long gmtCreate;

    /**
     * 支付渠道
     */
    @Column(value = "pay_channel")
    private Integer payChannel;
}