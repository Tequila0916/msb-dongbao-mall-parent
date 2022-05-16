package com.msb.dongbao.oms.model.enums;

/**
 * 订单处理状态枚举
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 2020年05月27日 17时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum OrStatusEnum {

    WAIT_PROCESS(0,"处理中"),
    SUCCESS_PROCESS(1,"处理成功"),
    FAIL_PROCESS(2,"处理失败");


    private int code;
    private String deec;

    OrStatusEnum(int code, String deec) {
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
