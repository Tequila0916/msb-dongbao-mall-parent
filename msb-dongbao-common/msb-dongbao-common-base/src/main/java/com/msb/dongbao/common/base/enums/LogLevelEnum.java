package com.msb.dongbao.common.base.enums;

/**
 * 日志级别枚举
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年05月29日 15时40分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum LogLevelEnum {

    DEBUG(-1, "DEBUG"),
    INFO(0, "INFO"),
    WARN(1, "WARN"),
    ERROR(2, "ERROR"),
    TRACE(3, "TRACE");

    private int value;
    private String name;

    LogLevelEnum(int value, String name) {
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
}
