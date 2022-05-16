package com.msb.dongbao.pay.strategy;

import com.msb.dongbao.pay.common.PayChannelEnum;
import com.msb.dongbao.pay.common.SpringContextUtil;
import com.msb.dongbao.pay.model.dto.MallPayDTO;
import com.msb.dongbao.pay.model.dto.MallRefundDTO;
import com.msb.dongbao.pay.service.IPayStrategyService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 支付策略上下文
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月08日 10时31分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Component
public class PayContextStrategy {

    public IPayStrategyService parseStrategy(HttpServletRequest request){
        String payCode = request.getParameter("payCode");
        return parseStrategy(payCode);
    }

    public IPayStrategyService parseStrategy(String payCode){
        String payBeanId = PayChannelEnum.getPayBeanByCode(payCode);
        Assert.notNull(payBeanId,"不支持的支付方式");
        IPayStrategyService payStrategyService = (IPayStrategyService)SpringContextUtil.getBean(payBeanId);
        return payStrategyService;
    }

    /**
     * 发起支付，转向支付页面
     * @param mallPay
     * @return
     */
    public String goPayHtml(MallPayDTO mallPay) throws Exception {
        String payCode = mallPay.getPayCode();
        IPayStrategyService payStrategyService = parseStrategy(payCode);
        return payStrategyService.goPay(mallPay);
    }

    public String refund(HttpServletRequest request) {
        IPayStrategyService payStrategyService = parseStrategy(request);
        return payStrategyService.refund(request);
    }

    public String payNotify(HttpServletRequest request,String payCode) {
        IPayStrategyService payStrategyService = parseStrategy(payCode);
        return payStrategyService.payNotify(request);
    }


    public String payReturn(HttpServletRequest request, HttpServletResponse response,String payCode) {
        IPayStrategyService payStrategyService = parseStrategy(payCode);
        return payStrategyService.payReturn(request,response);
    }

    public String tradeClose(HttpServletRequest request) {
        IPayStrategyService payStrategyService = parseStrategy(request);
        return payStrategyService.tradeClose(request);
    }

    public String tradeQuery(HttpServletRequest request) {
        IPayStrategyService payStrategyService = parseStrategy(request);
        return payStrategyService.tradeQuery(request);
    }

    public String refundQuery(HttpServletRequest request) {
        IPayStrategyService payStrategyService = parseStrategy(request);
        return payStrategyService.refundQuery(request);
    }

    public String orderSync(HttpServletRequest request) {
        IPayStrategyService payStrategyService = parseStrategy(request);
        return payStrategyService.orderSync(request);
    }


    /**
     * 发起退款
     * @param mallRefund
     * @return
     */
    public Boolean refund(MallRefundDTO mallRefund) throws Exception {
        IPayStrategyService payStrategyService = parseStrategy(mallRefund.getPayCode());
        return payStrategyService.refund(mallRefund);
    }


}
