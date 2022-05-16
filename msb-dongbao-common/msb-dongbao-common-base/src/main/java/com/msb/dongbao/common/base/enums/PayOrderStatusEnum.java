package com.msb.dongbao.common.base.enums;

/**
 * 支付状态枚举
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月09日 11时51分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum  PayOrderStatusEnum {

    PAY_PENDING(1, "等待支付中"),
    PAY_SUCCESS(2, "支付成功"),
    PAY_FAIL(-1,"支付失败"),
    PAY_TRADE_CLOSE(3, "未付款交易超时关闭，或支付完成后全额退款"),
    PAY_FINISHED(4,"交易结束,不可退款"),
    PAY_REFUND_PENDING(10, "退款中");



    private int value;
    private String name;

    PayOrderStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static PayOrderStatusEnum getEnumByValue(int value) {
        for (PayOrderStatusEnum ele : PayOrderStatusEnum.values()) {
            if (ele.value == value) {
                return ele;
            }
        }
        return null;
    }
}
