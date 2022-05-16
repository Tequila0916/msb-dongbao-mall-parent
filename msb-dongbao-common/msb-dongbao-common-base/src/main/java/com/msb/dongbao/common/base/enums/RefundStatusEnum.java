package com.msb.dongbao.common.base.enums;

/**
 * 退款状态枚举
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月09日 11时51分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum RefundStatusEnum {

    // 退款状态: 1-退款处理中; 2-退款成功; 3-退款失败;4-取消退款
    REFUND_PENDING(1, "退款处理中"),
    REFUND_SUCCESS(2, "退款成功"),
    REFUND_FAIL(3,"退款失败"),
    REFUND_CANCEL(4, "取消退款");

    private int value;
    private String name;

    RefundStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static RefundStatusEnum getEnumByValue(int value) {
        for (RefundStatusEnum ele : RefundStatusEnum.values()) {
            if (ele.value == value) {
                return ele;
            }
        }
        return null;
    }
}
