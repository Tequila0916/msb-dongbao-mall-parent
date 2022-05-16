package com.msb.dongbao.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 基础配置类
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月07日 13时07分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@ConfigurationProperties(prefix = "alipay")
@PropertySource("classpath:pay.properties")
@Configuration
public class AlipayConfig {

    /**  应用ID,您的APPID，收款账号即是您的APPID对应支付宝账号 */
    public String app_id;

    // 商户私钥，您的PKCS8格式RSA2私钥
    public String merchant_private_key;

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public String alipay_public_key;

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public String notify_url;

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public String return_url;

    // 签名方式
    public String sign_type;

    // 字符编码格式
    public String charset;

    // 支付宝网关
    public String gateway_url;


    public final  String formateType = "json";


    @Bean
    @ConditionalOnMissingBean(value = AlipayClient.class)
    public AlipayClient alipayClient(){
        // 获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(gateway_url,
                app_id,
                merchant_private_key,
                formateType,
                charset,
                alipay_public_key,
                sign_type);
        return alipayClient;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMerchant_private_key() {
        return merchant_private_key;
    }

    public void setMerchant_private_key(String merchant_private_key) {
        this.merchant_private_key = merchant_private_key;
    }

    public String getAlipay_public_key() {
        return alipay_public_key;
    }

    public void setAlipay_public_key(String alipay_public_key) {
        this.alipay_public_key = alipay_public_key;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getGateway_url() {
        return gateway_url;
    }

    public void setGateway_url(String gateway_url) {
        this.gateway_url = gateway_url;
    }

    public String getFormateType() {
        return formateType;
    }
}