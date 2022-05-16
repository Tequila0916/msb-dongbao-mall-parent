package com.msb.dongbao.common.base.enums.system;

import com.msb.dongbao.common.base.enums.ErrorCodeEnum;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年05月29日 15时40分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum ModuleEnum {

    MODULE_COMMON(000, "公共模块"),
    MODULE_USER(001, "用户模块"),
    MODULE_ORDER(002, "订单模块"),
    MODULE_PRODUCT(003, "商品模块"),
    MODULE_PAY(004, "支付模块");

    private int value;
    private String name;

    ModuleEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ErrorCodeEnum getEnum(int code) {
        for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }
}
