package com.msb.dongbao.common.base.enums;

/**
 * 退款方式枚举值
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月09日 11时51分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum RefundMethodEnum {

    // 支付状态:1-等待支付中;2-支付成功;3:交易已关闭;-1:支付失败
    REFUND_BACK(1, "原路返还"),
    REFUND_HAND(2, "人工退款");

    private int value;
    private String name;

    RefundMethodEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static RefundMethodEnum getEnumByValue(int value) {
        for (RefundMethodEnum ele : RefundMethodEnum.values()) {
            if (ele.value == value) {
                return ele;
            }
        }
        return null;
    }
}
