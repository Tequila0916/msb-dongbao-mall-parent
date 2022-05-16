package com.msb.dongbao.pay.common;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月08日 10时34分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public enum  PayChannelEnum {
    // 1-支付宝;2-微信支付
    ALI_PAY(1,"ali_pay","支付宝支付","aliPayStrategyService"),
    WECHAT_PAY(2,"wechat_pay","微信支付","wechatPayStrategyService");

    private int value;
    private String code;
    private String name;
    private String beanId;

    PayChannelEnum(int value, String code, String name,String beanId) {
        this.value = value;
        this.code = code;
        this.name = name;
        this.beanId = beanId;
    }

    /**
     * 根据支付编码获取枚举值
     * @param payCode
     * @return
     */
    public static String getPayBeanByCode(String payCode){
        for(PayChannelEnum channelEnum:PayChannelEnum.values()){
            if(channelEnum.code.equalsIgnoreCase(payCode)){
                return channelEnum.beanId;
            }
        }
        return null;
    }

    public static String getPayBeanByValue(int value){
        for(PayChannelEnum channelEnum:PayChannelEnum.values()){
            if(channelEnum.value == value){
                return channelEnum.beanId;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getBeanId() {
        return beanId;
    }
}
