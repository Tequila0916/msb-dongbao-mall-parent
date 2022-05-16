package com.msb.dongbao.sms.model.enums;

/**
 * 优惠券使用类型
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 14:19
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum UseTypeEnum {
    UNIVERSAL(0,"全场通用"),
    CLASSIFICATION(1,"指定分类"),
    PRODUCT(2,"指定商品");



    private int code;
    private String desc;

    UseTypeEnum(int code, String desc) {
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
