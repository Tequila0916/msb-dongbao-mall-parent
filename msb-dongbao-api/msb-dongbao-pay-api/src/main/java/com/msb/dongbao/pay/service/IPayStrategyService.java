package com.msb.dongbao.pay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.msb.dongbao.pay.model.dto.AliRefundQueryDTO;
import com.msb.dongbao.pay.model.dto.MallPayDTO;
import com.msb.dongbao.pay.model.dto.MallRefundDTO;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月08日 10时23分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IPayStrategyService {

    /**
     * 发起支付，跳转支付页面
     * 存在多次发起支付的情况
     * 用户首次下单发起支付后，但是不进行支付
     *
     * 建议:
     * 增加支付掉单处理，即当未收到明确应答时，反查交易状态，为节省系统资源可设置合理的查询次数，且查询时间间隔递增。
     * @param mallPay
     * @return
     * @throws AlipayApiException
     */
    String goPay(MallPayDTO mallPay) throws Exception;

    /**
     * 发起退款
     * @param request
     * @return
     */
    @Deprecated
    String refund(HttpServletRequest request);

    /**
     * 发起退款
     * @param mallRefund
     * @return
     * @throws Exception
     */
    Boolean refund(MallRefundDTO mallRefund) throws Exception;

    /**
     * 支付异步通知
     * @param request
     * @return
     */
    String payNotify(HttpServletRequest request);

    /**
     * 支付同步通知
     * @param request
     * @return
     */
    @Deprecated
    String payReturn(HttpServletRequest request, HttpServletResponse response);

    /**
     * 退款查询
     * @param request
     * @return
     */
    String refundQuery(HttpServletRequest request);

    /**
     * 退款查询
     * @param aliRefundQuery
     * @return
     */
    AlipayTradeFastpayRefundQueryResponse refundQuery(AliRefundQueryDTO aliRefundQuery);

    /**
     * 关闭交易
     * @param request
     * @return
     */
    String tradeClose(HttpServletRequest request);

    /**
     * 关闭交易
     * @param outTradeNo
     * @param tradeNo
     * @param operatorId
     * @return
     */
    AlipayTradeCloseResponse tradeClose(String outTradeNo, String tradeNo, String operatorId);

    /**
     * 交易查询
     *
     * 需要调用查询接口的情况:
     * 1. 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * 2. 调用支付接口后，返回系统错误或未知交易状态情况；
     * 3. 调用alipay.trade.pay，返回INPROCESS的状态；
     * 4. 调用alipay.trade.cancel之前，需确认支付状态；
     * @param request
     * @return
     */
    String tradeQuery(HttpServletRequest request);

    /**
     * 交易查询
     * @param outTradeNo 商户订单编号
     * @param tradeNo 支付宝交易编号
     * @return
     */
    AlipayTradeQueryResponse tradeQuery(String outTradeNo, @Nullable String tradeNo);

    /**
     * 同步订单信息到第三方
     * @param request
     * @return
     */
    String orderSync(HttpServletRequest request);
}
