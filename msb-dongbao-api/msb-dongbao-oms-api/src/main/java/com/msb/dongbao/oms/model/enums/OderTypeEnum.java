package com.msb.dongbao.oms.model.enums;

/**
 * 订单类型
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 2020年05月27日 17时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum OderTypeEnum {
    NORMAL_ORDER(0, "正常订单"),
    SPIKE_ORDER(1, "秒杀订单");


    private int code;
    private String deec;

    OderTypeEnum(int code, String deec) {
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
