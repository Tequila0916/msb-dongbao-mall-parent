package com.msb.dongbao.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.google.gson.Gson;
import com.msb.dongbao.common.base.constant.PayConstants;
import com.msb.dongbao.common.base.enums.PayLogTypeEnum;
import com.msb.dongbao.common.util.IDUtils;
import com.msb.dongbao.oms.service.IOrderRefundService;
import com.msb.dongbao.pay.common.PayChannelEnum;
import com.msb.dongbao.pay.model.dto.AliRefundDTO;
import com.msb.dongbao.pay.model.entity.PayLogData;
import com.msb.dongbao.pay.model.entity.PayRefund;
import com.msb.dongbao.pay.service.IPayLogDataService;
import com.msb.dongbao.pay.service.IPayRefundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步处理业务
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月15日 17时35分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Slf4j
@Service
public class AsyncPayBizService {

    @Autowired
    private IPayLogDataService payLogDataService;
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private IOrderRefundService orderRefundService;
    @Autowired
    private IPayRefundService payRefundService;

    /**
     * 异步处理支付宝退款请求
     * @param payRefund 退款流水实体
     */
    @Async
    public void asyncHandleAliPayRefund(PayRefund payRefund) throws AlipayApiException {

        String refundOrderNo = payRefund.getRelRefundOrderNo();
        String orderNo = payRefund.getRelOrderNo();

        // 封装退款请求
        AlipayTradeRefundRequest aliPayRequest = new AlipayTradeRefundRequest();
        AliRefundDTO aliRefund = AliRefundDTO.builder()
                .out_trade_no(orderNo)
                .refund_amount(payRefund.getRefundFee().toString())
                .trade_no(payRefund.getTradeNo())
                .refund_reason(payRefund.getRefundReason())
                .out_request_no(refundOrderNo).build();
        String requestParam = new Gson().toJson(aliRefund);
        aliPayRequest.setBizContent(requestParam);

        // 记录请求日志
        handleLog(requestParam,orderNo, PayLogTypeEnum.REFUND.getValue(), PayChannelEnum.ALI_PAY.getValue(),
                IDUtils.UUID(),payRefund.getMerchantId());


        boolean success = false;
        int i = 0;
        // 添加重试机制
        for(;;){
            if(success || i >= 3){
                break;
            }
            long start = System.currentTimeMillis();
            log.info("开始发送支付宝退款请求");
            AlipayTradeRefundResponse refundResponse = alipayClient.execute(aliPayRequest);
            log.info("支付宝退款请求响应成功,耗时:{}",System.currentTimeMillis()-start);
            // 退费成功
            if(refundResponse.isSuccess() && refundResponse.getFundChange().equalsIgnoreCase(PayConstants.ALI_PAY_RETURN_CHANGE_Y)){
                success = true;
                log.info("退款成功,订单编号:{},回调信息:{}",orderNo,new Gson().toJson(refundResponse));
                // 更新订单状态、退费单状态、更新退款流水状态
                orderRefundService.orderRefundSuccess(orderNo,refundOrderNo);
                payRefundService.refundSuccess(orderNo,refundOrderNo);
                break;
            }
            else{
                log.error("退款失败,订单编号:{},错误信息:{}",orderNo,new Gson().toJson(refundResponse));
                i++;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error("中断异常",e);
                Thread.currentThread().interrupt();
            }
        }

        if(!success){
            log.warn("退款失败,更新退款状态,订单编号:{}",orderNo);
            // 退款单状态 更新为 退款失败
            orderRefundService.orderRefundFail(orderNo,refundOrderNo);
            payRefundService.refundFail(refundOrderNo,orderNo);
        }


    }

    /**
     * 处理日志
     * @param requestParam
     * @param orderNumber
     * @param logType
     * @param payChannel
     * @param logId
     * @param merchantId
     */
    private void handleLog(String requestParam, String orderNumber, Integer logType, Integer payChannel,String logId,String merchantId) {
        PayLogData payLogData = PayLogData.builder()
                .merchantId(merchantId)
                .logId(logId)
                .orderNo(orderNumber)
                .gmtCreate(System.currentTimeMillis())
                .logType(logType)
                .requestParams(requestParam)
                .payChannel(payChannel)
                .build();
        payLogDataService.saveLogData(payLogData);
    }

}
