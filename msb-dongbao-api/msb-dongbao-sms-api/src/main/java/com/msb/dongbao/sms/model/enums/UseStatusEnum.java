package com.msb.dongbao.sms.model.enums;

/**
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 11:12
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum UseStatusEnum {
    UNUSED(0,"未使用"),
    USED(1,"已使用"),
    TIMEOUT(2,"已超时");



    private int code;
    private String desc;

    UseStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
