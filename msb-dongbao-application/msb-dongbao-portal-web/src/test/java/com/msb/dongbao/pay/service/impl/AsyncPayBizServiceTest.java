package com.msb.dongbao.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.msb.dongbao.common.base.constant.PayConstants;
import com.msb.dongbao.common.util.IDUtils;
import com.msb.dongbao.oms.service.IOrderRefundService;
import com.msb.dongbao.pay.model.entity.PayRefund;
import com.msb.dongbao.pay.service.IPayLogDataService;
import com.msb.dongbao.pay.service.IPayRefundService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * 测试类
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月17日 17时43分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RunWith(SpringRunner.class)
@Slf4j
public class AsyncPayBizServiceTest{

    @InjectMocks
    private AsyncPayBizService asyncPayBizService;

    @Mock
    private IPayLogDataService payLogDataService;
    @Mock
    private AlipayClient alipayClient;
    @Mock
    private IOrderRefundService orderRefundService;
    @Mock
    private IPayRefundService payRefundService;



    @Test
    public void test_fail() throws AlipayApiException, InterruptedException {
        PayRefund payRefund = new PayRefund();
        payRefund.setGmtModified(System.currentTimeMillis());
        payRefund.setRefundNo(IDUtils.UUID());
        payRefund.setRelOrderNo(IDUtils.UUID());
        payRefund.setTradeNo(IDUtils.UUID());
        payRefund.setMerchantId("1");
        payRefund.setRefundFee(new BigDecimal("1.11"));
        payRefund.setRefundReason("测试原因");

        Mockito.when(payLogDataService.saveLogData(Mockito.any())).thenReturn(Mockito.any());

        AlipayTradeRefundResponse refundResponse = new AlipayTradeRefundResponse();
        refundResponse.setSubCode(null);
        refundResponse.setFundChange(PayConstants.ALI_PAY_RETURN_CHANGE_N);
        AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
        Mockito.when(alipayClient.execute(refundRequest)).thenReturn(refundResponse);
        Mockito.doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return "called with arguments: " + args;
            }
        }).when(orderRefundService).orderRefundSuccess(Mockito.any(),Mockito.any());
        Mockito.when(payRefundService.refundSuccess(Mockito.any(),Mockito.any())).thenReturn(true);
        Mockito.doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return "called with arguments: " + args;
            }
        }).when(orderRefundService).orderRefundFail(Mockito.any(),Mockito.any());
        Mockito.when(payRefundService.refundFail(Mockito.any(),Mockito.any())).thenReturn(true);

        asyncPayBizService.asyncHandleAliPayRefund(payRefund);
    }




    @Test
    public void test_success() throws AlipayApiException, InterruptedException {
        PayRefund payRefund = new PayRefund();
        payRefund.setGmtModified(System.currentTimeMillis());
        payRefund.setRefundNo(IDUtils.UUID());
        payRefund.setRelOrderNo(IDUtils.UUID());
        payRefund.setTradeNo(IDUtils.UUID());
        payRefund.setMerchantId("1");
        payRefund.setRefundFee(new BigDecimal("1.11"));
        payRefund.setRefundReason("测试原因");

        Mockito.when(payLogDataService.saveLogData(Mockito.any())).thenReturn(Mockito.any());

        AlipayTradeRefundResponse refundResponse = new AlipayTradeRefundResponse();
        refundResponse.setSubCode(null);
        refundResponse.setFundChange(PayConstants.ALI_PAY_RETURN_CHANGE_Y);
        AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
        Mockito.when(alipayClient.execute(refundRequest)).thenReturn(refundResponse);
        Mockito.doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return "called with arguments: " + args;
            }
        }).when(orderRefundService).orderRefundSuccess(Mockito.any(),Mockito.any());
        Mockito.when(payRefundService.refundSuccess(Mockito.any(),Mockito.any())).thenReturn(true);
        Mockito.doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return "called with arguments: " + args;
            }
        }).when(orderRefundService).orderRefundFail(Mockito.any(),Mockito.any());
        Mockito.when(payRefundService.refundFail(Mockito.any(),Mockito.any())).thenReturn(true);

        asyncPayBizService.asyncHandleAliPayRefund(payRefund);
    }
}
