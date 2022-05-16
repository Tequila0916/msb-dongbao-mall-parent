package com.msb.dongbao.common.base.enums;

/**
 * 错误码枚举
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年05月29日 15时40分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum ErrorCodeEnum {
    /**
     * PMS 001 100
     */
    PMS001100(0001100, "参数异常"),
    OMS0000001(0000001, "更新失败"),
    OMS0000010(0000010, "扣减库存失败"),
    OMS0000011(0000011, "订单取消失败"),
    OMS0000100(0000100, "退单预取处理订单失败"),
    OMS0000101(0000101, "退单失败后取处理订单失败"),
    OMS0000110(0000110, "退单成功后取处理订单失败"),
    OMS0000111(0000111, "参数异常"),
    OMS0001000(0001000, "订单修改失败"),
    OMS0001001(0001001, "退单修改失败"),

    OMS0001010(0001010, "订单结算金额不能小于0"),

    PMS0001600(1001600, "库存不足,无法添加购车"),
    PMS0001601(1001601, "改库存不足"),
    PMS0001602(1001602, "不存在指定编号的商品"),
    PMS0001603(1001603, "商品不存在"),

    UMS0001001(0001001, "此用户不存在"),
    UMS0001010(0001010, "账号重复"),
    UMS0001011(0001011, "验证码错误"),
    UMS0001012(0001012, "地址不存在"),


    SMS0000001(0000001,"优惠券不存在"),
    SMS0000010(0000010,"优惠券已经领完了"),
    SMS0000011(0000011,"优惠券不存在"),
    SMS0000100(0000100,"优惠券记录修改异常");







    private int code;
    private String msg;

    public String msg() {
        return msg;
    }

    public int code() {
        return code;
    }

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
