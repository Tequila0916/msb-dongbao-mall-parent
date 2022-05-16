package com.msb.dongbao.common.base.enums;

/**
 * 支付交易日志类型
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年05月29日 15时40分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum PayLogTypeEnum {
    // 日志类型，payment:支付; refund:退款; notify:异步通知; return:同步通知; query:查询
    PAYMENT(0,"支付"),
    REFUND(1,"退款"),
    NOTIFY(2,"异步通知"),
    RETURN(3,"同步通知"),
    QUERY(4,"查询");

    private int value;
    private String name;

    PayLogTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static PayLogTypeEnum getEnumByValue(int value) {
        for (PayLogTypeEnum ele : PayLogTypeEnum.values()) {
            if (ele.value == value) {
                return ele;
            }
        }
        return null;
    }
}
