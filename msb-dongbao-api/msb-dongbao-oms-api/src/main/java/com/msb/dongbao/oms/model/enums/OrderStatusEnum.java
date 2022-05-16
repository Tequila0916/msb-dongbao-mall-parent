package com.msb.dongbao.oms.model.enums;

/**
 * 订单状态
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 2020年05月27日 17时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum OrderStatusEnum {
    WAIT_PAY(0, "待付款"),
    WAIT_SHIP(1, "待发货"),
    WAIT_RECEIPT(2, "待收货"),
    TRANSACTION_COMPLETE(3, "交易完成"),
    CANCEL(4, "已取消"),
    TIMEOUT_CANCEL(5, "超时取消"),
    REFUNDING(10, "退单中"),
    REFUND_COMPLETED(11, "REFUND_COMPLETED"),
    INVALID_ORDER(12, "无效订单");


    private int code;
    private String deec;

    OrderStatusEnum(int code, String deec) {
        this.code = code;
        this.deec = deec;
    }

    public int getCode() {
        return code;
    }

    public String getDeec() {
        return deec;
    }

}
