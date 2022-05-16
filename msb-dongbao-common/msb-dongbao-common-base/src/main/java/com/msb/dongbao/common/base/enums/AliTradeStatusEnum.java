package com.msb.dongbao.common.base.enums;

/**
 * 阿里交易状态码枚举
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月09日 11时51分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum AliTradeStatusEnum {

    // 退款状态: 1-退款处理中; 2-退款成功; 3-退款失败;4-取消退款
    WAIT_BUYER_PAY(1,"WAIT_BUYER_PAY","交易创建，等待买家付款"),
    TRADE_SUCCESS(2, "TRADE_SUCCESS","交易支付成功"),
    TRADE_CLOSED(3,"TRADE_CLOSED","未付款交易超时关闭，或支付完成后全额退款"),
    TRADE_FINISHED(4, "TRADE_FINISHED","交易结束，不可退款");

    private int value;
    private String name;
    private String desc;

    AliTradeStatusEnum(int value, String name,String desc) {
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public static AliTradeStatusEnum getEnumByValue(int value) {
        for (AliTradeStatusEnum ele : AliTradeStatusEnum.values()) {
            if (ele.value == value) {
                return ele;
            }
        }
        return null;
    }
}
