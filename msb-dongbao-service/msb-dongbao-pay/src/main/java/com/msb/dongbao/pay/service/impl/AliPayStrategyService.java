package com.msb.dongbao.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeOrderinfoSyncResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.google.gson.Gson;
import com.msb.dongbao.common.base.constant.PayConstants;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.base.enums.*;
import com.msb.dongbao.common.base.exception.BusinessException;
import com.msb.dongbao.common.util.IDUtils;
import com.msb.dongbao.oms.model.dto.OmsOrderAndItemsDTO;
import com.msb.dongbao.oms.model.dto.OmsOrderDTO;
import com.msb.dongbao.oms.model.entity.OmsOrder;
import com.msb.dongbao.oms.model.entity.OrderRefund;
import com.msb.dongbao.oms.model.enums.OrderStatusEnum;
import com.msb.dongbao.oms.service.IOmsOrderService;
import com.msb.dongbao.oms.service.IOrderRefundService;
import com.msb.dongbao.pay.common.PayChannelEnum;
import com.msb.dongbao.pay.config.AlipayConfig;
import com.msb.dongbao.pay.model.dto.*;
import com.msb.dongbao.pay.model.entity.PayLogData;
import com.msb.dongbao.pay.model.entity.PayRefund;
import com.msb.dongbao.pay.model.entity.PayTransaction;
import com.msb.dongbao.pay.repository.PayLogDataRepository;
import com.msb.dongbao.pay.service.IPayRefundService;
import com.msb.dongbao.pay.service.IPayTransactionService;
import com.msb.dongbao.pay.strategy.AbstractPayStrategyService;
import com.msb.dongbao.ums.model.entity.UmsMember;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 阿里支付宝支付策略类
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月08日 10时52分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)l;
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Slf4j
@Service(value = "aliPayStrategyService")
public class AliPayStrategyService extends AbstractPayStrategyService {

    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private IPayTransactionService payTransactionService;
    @Autowired
    private IPayRefundService payRefundService;
    @Autowired
    private IOmsOrderService omsOrderService;
    @Autowired
    private IOrderRefundService orderRefundService;
    @Autowired
    private PayLogDataRepository payLogDataRepository;
    @Autowired
    private AsyncPayBizService asyncPayBizService;
    @Autowired
    private AlipayConfig alipayConfig;


    @Override
    public String goPay(MallPayDTO mallPay) throws Exception {
        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfig.getReturn_url());
        alipayRequest.setNotifyUrl(alipayConfig.getNotify_url());
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String orderNo = mallPay.getOrderNo();
        // 查询订单数据、检验
        OmsOrderDTO omsOrder = omsOrderService.orderDetail(orderNo);
        if(omsOrder == null){
            throw new RuntimeException("平台不存在该订单");
        }

        Integer orderStatus = omsOrder.getOrderStatus();
        // 判断订单状态，不等于待付款状态，不允许再次申请支付
        if(orderStatus != OrderStatusEnum.WAIT_PAY.getCode()){
            throw new RuntimeException("该订单状态不可重复发起支付");
        }

        PayTransactionDTO oldPayTransactionDTO = payTransactionService.getByOrderNo(orderNo);
        if(oldPayTransactionDTO != null){
            throw new RuntimeException("该订单的支付记录已经存在");
        }


        // 付款金额，必填
        BigDecimal payAmount = omsOrder.getPayAmount();
        // 订单名称，必填
        String title = omsOrder.getTitle();
        Map<String,Object> map = new HashMap<>();
        map.put("out_trade_no",orderNo);
        map.put("total_amount",payAmount);
        map.put("product_code","FAST_INSTANT_TRADE_PAY");
        map.put("subject",title);
        String requestParam = new Gson().toJson(map);
        alipayRequest.setBizContent(requestParam);

        //  记录请求日志
        String orderNumber = omsOrder.getOrderNumber();
        PayLogData payLogData = PayLogData.builder().merchantId(omsOrder.getMerchantId())
                .logId(IDUtils.UUID())
                .orderNo(orderNumber)
                .transactionNo(orderNumber)
                .gmtCreate(System.currentTimeMillis())
                .logType(PayLogTypeEnum.PAYMENT.getValue())
                .requestParams(requestParam)
                .payChannel(PayChannelEnum.ALI_PAY.getValue())
                .build();
        payLogDataRepository.save(payLogData);

        // 记录支付流水记录
        PayTransaction payTransaction = new PayTransaction();
        payTransaction.setRelOrderNo(orderNumber);
        payTransaction.setTransactionNo(orderNumber);
        payTransaction.setPayChannelId(PayChannelEnum.ALI_PAY.getValue());
        payTransaction.setTotalFee(omsOrder.getTotalAmount());
        payTransaction.setPayFee(omsOrder.getPayAmount());
        payTransaction.setOrderStatus(PayOrderStatusEnum.PAY_PENDING.getValue());
        payTransaction.setExpireTime(0L);
        payTransaction.setEnable(0);
        payTransaction.setMerchantId(omsOrder.getMerchantId());
        payTransactionService.save(payTransaction);
        // 支付请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;
    }

    @Override
    public String refund(HttpServletRequest request) {

        // 设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        // 商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = request.getParameter("out_trade_no");
        // 支付宝交易号
        String trade_no =request.getParameter("trade_no");
        // 请二选一设置
        // 需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = request.getParameter("refund_amount");
        // 退款的原因说明
        String refund_reason = request.getParameter("refund_reason");
        // 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String out_request_no = request.getParameter("out_request_no");
        AliRefundDTO aliRefund = AliRefundDTO.builder().out_trade_no(out_trade_no)
                .refund_amount(refund_amount)
                .trade_no(trade_no)
                .refund_reason(refund_reason)
                .out_request_no(out_request_no).build();
        alipayRequest.setBizContent(new Gson().toJson(aliRefund));
        try{
            String result = alipayClient.execute(alipayRequest).getBody();
            return result;
        }catch (AlipayApiException e){

        }
        return "";

    }

    @Override
    public Boolean refund(MallRefundDTO mallRefund) throws Exception {

        String refundOrderNo = mallRefund.getRefundOrderNo();
        // 查询对应的退款单号
        OrderRefund orderRefund = orderRefundService.detail(refundOrderNo);
        if(orderRefund == null){
            throw new BusinessException("不存在对应的退款单记录");
        }

        // 查询退款流水
        PayRefundDTO payRefundDTO = payRefundService.getByRefundOrderNo(refundOrderNo);
        if(payRefundDTO != null){
            log.error("该退款单号的退款记录经存在,退款订单编号:{}",refundOrderNo);
            throw new BusinessException("退款单号的退款记录经存在");
        }

        String orderNumber = orderRefund.getOrderNumber();
        // 记录退费流水记录
        PayRefund payRefund = new PayRefund();
        // 退款金额
        payRefund.setRefundFee(orderRefund.getRefundAmount());
        payRefund.setEnable(0);
        payRefund.setPayChannelId(PayChannelEnum.ALI_PAY.getValue());
        payRefund.setRefundMethod(RefundMethodEnum.REFUND_BACK.getValue());
        payRefund.setRelOrderNo(orderNumber);
        payRefund.setRelRefundOrderNo(mallRefund.getRefundOrderNo());
        payRefund.setRefundReason(orderRefund.getReason());
        payRefund.setMerchantId(orderRefund.getMerchantId());
        payRefund.setRefundNo(IDUtils.UUID());
        UmsMember currentUser = getCurrentUser();
        String userId = currentUser.getId().toString();
        String username = currentUser.getUsername();
        payRefund.setCreateUid(userId);
        payRefund.setCreateUname(currentUser.getUsername());
        long now = System.currentTimeMillis();
        payRefund.setGmtCreate(now);
        payRefund.setModifiedUid(userId);
        payRefund.setModifiedUname(username);
        payRefund.setGmtModified(now);
        // 退款处理中
        payRefund.setRefundStatus(RefundStatusEnum.REFUND_PENDING.getValue());
        payRefundService.save(payRefund);
        payTransactionService.refundTransactionPre(orderNumber);

        // 异步处理退款请求
        asyncPayBizService.asyncHandleAliPayRefund(payRefund);
        return true;
    }

    @Override
    public String payNotify(HttpServletRequest request) {

        // 获取支付宝 POST 通知
        Map<String, String> params = parseAliPayCallback(request,true);
        try{
            // 验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipay_public_key(),
                    alipayConfig.getCharset(), alipayConfig.getSign_type());
            /**
             * 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号
             * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）
             * 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
             * 4、验证app_id是否为该商户本身
             */
            if(signVerified) {
                // 商户订单号
                String outTradeNo = params.get("out_trade_no");
                // 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号
                OmsOrderDTO orderDTO = omsOrderService.orderDetail(outTradeNo);
                if(orderDTO == null){
                    log.error("当前平台不存在该订单,订单号:{}",outTradeNo);
                    return PayConstants.ALI_PAY_RETURN_FAIL;
                }

                //  记录请求日志
                String orderNumber = orderDTO.getOrderNumber();
                PayLogData payLogData = PayLogData.builder().merchantId(orderDTO.getMerchantId())
                        .logId(IDUtils.UUID())
                        .orderNo(orderNumber)
                        .transactionNo(orderNumber)
                        .gmtCreate(System.currentTimeMillis())
                        .requestParams(new Gson().toJson(params))
                        .logType(PayLogTypeEnum.NOTIFY.getValue())
                        .payChannel(PayChannelEnum.ALI_PAY.getValue())
                        .build();
                payLogDataRepository.save(payLogData);

                // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）
                BigDecimal payAmount = orderDTO.getPayAmount();
                String totalAmount = params.get("total_amount");
                if(payAmount.compareTo(new BigDecimal(totalAmount)) != 0){
                    log.error("订单的支付金额与支付宝的回传金额不一致,订单编号:{}",outTradeNo);
                    return PayConstants.ALI_PAY_RETURN_FAIL;
                }

                // 检查支付记录是否存在
                PayTransactionDTO payTransaction = payTransactionService.getByOrderNo(outTradeNo);
                if(payTransaction == null){
                    log.error("当前平台不存在对应的支付记录,订单号:{}",outTradeNo);
                    return PayConstants.ALI_PAY_RETURN_FAIL;
                }


                Integer orderStatus = orderDTO.getOrderStatus();
                Integer transactionOrderStatus = payTransaction.getOrderStatus();

                // 支付宝交易号
                String tradeNo = params.get("trade_no");
                // 交易状态
                String tradeStatus = params.get("trade_status");
                // 交易结束,注意: 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                if(tradeStatus.equals(AliTradeStatusEnum.TRADE_FINISHED.getName())){

                    if(orderStatus == OrderStatusEnum.TRANSACTION_COMPLETE.getCode()){
                        log.warn("该订单已经是交易完成状态,订单编号:{}",outTradeNo);
                        return PayConstants.ALI_PAY_RETURN_SUCCESS;
                    }
                    // 更新为交易结束状态
                    OmsOrder omsOrder = new OmsOrder();
                    omsOrder.setId(orderDTO.getId());
                    omsOrder.setOrderStatus(OrderStatusEnum.TRANSACTION_COMPLETE.getCode());
                    // TODO 更新订单为交易完成状态
                    omsOrderService.updateById(omsOrder);
                    if(transactionOrderStatus == PayOrderStatusEnum.PAY_FINISHED.getValue()){
                        log.warn("支付流水记录已经处于交易完成状态,订单编号:{}",outTradeNo);
                        return PayConstants.ALI_PAY_RETURN_SUCCESS;
                    }
                    // 更新支付流水状态为交易完成
                    payTransactionService.payTransactionFinished(orderNumber,tradeNo);
                    // TODO 更新订单状态为交易完成
                    // omsOrderService.payTransactionFinished(payTransaction);
                    return  PayConstants.ALI_PAY_RETURN_SUCCESS;

                }
                // 支付成功
                else if (tradeStatus.equals(AliTradeStatusEnum.TRADE_SUCCESS.getName())){

                    if(orderStatus != OrderStatusEnum.WAIT_PAY.getCode()){
                        log.warn("订单被处理过:{}",outTradeNo);
                        return PayConstants.ALI_PAY_RETURN_SUCCESS;
                    }

                    omsOrderService.orderPaySuccess(orderNumber);

                    // 非支付中的状态，说明被处理过
                    if(transactionOrderStatus != PayOrderStatusEnum.PAY_PENDING.getValue()){
                        log.warn("支付流水记录已经被处理过:{}",outTradeNo);
                        return PayConstants.ALI_PAY_RETURN_SUCCESS;
                    }
                    // TODO 更新通知时间与支付时间，时间转换工具类
                    long notifyTime = 0L;
                    long paymentTime = 0L;
                    // 更新支付流水状态、订单支付状态
                    if(payTransactionService.payTransactionSuccess(tradeNo,notifyTime,paymentTime,orderNumber)){
                        return PayConstants.ALI_PAY_RETURN_SUCCESS;
                    }
                    return  PayConstants.ALI_PAY_RETURN_FAIL;
                }

            }
            else {
                log.error("验签失败,支付宝异步通知信息:{}",params);
                return PayConstants.ALI_PAY_RETURN_FAIL;
            }
        }catch (AlipayApiException e){
            log.error("支付宝回调处理异常",e);
        }
        return PayConstants.ALI_PAY_RETURN_SUCCESS;
    }

    @Override
    @Deprecated
    public String payReturn(HttpServletRequest request,HttpServletResponse response) {
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = parseAliPayCallback(request,false);
        try{
            // 调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipay_public_key(),
                    alipayConfig.getCharset(), alipayConfig.getSign_type());
            if(signVerified) {
                // 商户订单号
                String outTradeNo = params.get("out_trade_no");
                // 支付宝交易号
                String tradeNo = params.get("trade_no");
                // 付款金额
                String total_amount = params.get("total_amount");

                // 需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号
                OmsOrderDTO orderDTO = omsOrderService.orderDetail(outTradeNo);
                if(orderDTO == null){
                    log.error("当前平台不存在该订单,订单号:{}",outTradeNo);
                    throw new RuntimeException("当前平台不存在该订单");
                }

                BigDecimal payAmount = orderDTO.getPayAmount();
                // 检查金额是否正确
                String totalAmount = params.get("total_amount");
                if(payAmount.compareTo(new BigDecimal(totalAmount)) != 0){
                    log.error("订单的支付金额与支付宝的回传金额不一致,订单编号:{}",outTradeNo);
                    throw new RuntimeException("订单的支付金额与支付宝的回传金额不一致");
                }

                // 检查支付记录是否存在
                PayTransactionDTO payTransaction = payTransactionService.getByOrderNo(outTradeNo);
                if(payTransaction == null){
                    log.error("当前平台不存在对应的支付记录,订单号:{}",outTradeNo);
                    throw new RuntimeException("当前平台不存在对应的交易记录");
                }

                Integer orderStatus = orderDTO.getOrderStatus();
                Integer transactionOrderStatus = payTransaction.getOrderStatus();

                // 更新订单、支付流水状态
                if(orderStatus == OrderStatusEnum.WAIT_PAY.getCode()){
                    OmsOrder omsOrder = new OmsOrder();
                    omsOrder.setId(orderDTO.getId());
                    omsOrder.setOrderStatus(OrderStatusEnum.WAIT_SHIP.getCode());
                    // 更新订单状态
                    omsOrderService.updateById(omsOrder);
                }

                // 更新支付中状态数据
                if(transactionOrderStatus == PayOrderStatusEnum.PAY_PENDING.getValue()){
                    payTransaction.setOrderStatus(PayOrderStatusEnum.PAY_SUCCESS.getValue());
                    payTransaction.setTradeNo(tradeNo);
                }

                // return "trade_no:"+tradeNo+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount;
                OmsOrderAndItemsDTO omsOrderAndItemsDTO = new OmsOrderAndItemsDTO();
                return new Gson().toJson(ResultWrapper.getSuccessBuilder().data(omsOrderAndItemsDTO).build());
            }else {
                log.error("验签失败,支付宝同步通知信息:{}",params);
                throw new RuntimeException("验签失败，请联系管理员");
            }
        }catch (AlipayApiException e){
            log.error("支付宝同步回调失败",e);
        }
        return new Gson().toJson(ResultWrapper.getErrorBuilder().build());
    }

    @Override
    public String refundQuery(HttpServletRequest request) {
        // 业务订单编号
        String out_trade_no = request.getParameter("out_trade_no");
        // 支付宝交易号
        String trade_no = request.getParameter("trade_no");
        // 退款订单编号
        String out_request_no = request.getParameter("out_request_no");
        AliRefundQueryDTO refundQuery = AliRefundQueryDTO.builder().out_request_no(out_request_no)
                .out_trade_no(out_trade_no)
                .trade_no(trade_no).build();
        AlipayTradeFastpayRefundQueryRequest refundQueryRequest = new AlipayTradeFastpayRefundQueryRequest();
        refundQueryRequest.setBizContent(new Gson().toJson(refundQuery));
        try{
            AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(refundQueryRequest);
            if(response.isSuccess()){
                return "success";
            } else {
                return "fail";
            }
        }catch (AlipayApiException e){
            log.error("支付宝退款查询失败",e);
        }
        return null;
    }

    @Override
    public AlipayTradeFastpayRefundQueryResponse refundQuery(AliRefundQueryDTO aliRefundQuery) {
        AlipayTradeFastpayRefundQueryRequest refundQueryRequest = new AlipayTradeFastpayRefundQueryRequest();
        refundQueryRequest.setBizContent(new Gson().toJson(aliRefundQuery));
        try{
            AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(refundQueryRequest);
            return response;
        }catch (AlipayApiException e){
            log.error("支付宝退款查询失败",e);
        }
        return null;
    }

    @Override
    public String tradeClose(HttpServletRequest request) {

        // 设置请求参数
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
        // 商户订单号，商户网站订单系统中唯一订单号
        String outTradeNo = request.getParameter("outTradeNo");
        // 支付宝交易号
        String tradeNo = request.getParameter("tradeNo");
        String operatorId = request.getParameter("operatorId");
        AlipayTradeCloseResponse closeResponse = tradeClose(outTradeNo, tradeNo, operatorId);
        if(closeResponse.isSuccess()){
            return closeResponse.getBody();
        }
        return null;
    }

    @Override
    public AlipayTradeCloseResponse tradeClose(String outTradeNo,String tradeNo,String operatorId) {

        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
        Map<String,String> params = new HashMap<>();
        if(StringUtils.isNotEmpty(outTradeNo)){
            params.put("out_trade_no",outTradeNo);
        }
        if(StringUtils.isNotEmpty(tradeNo)){
            params.put("trade_no",tradeNo);
        }
        if(StringUtils.isNotEmpty(operatorId)){
            params.put("operator_id",operatorId);
        }
        alipayRequest.setBizContent(new Gson().toJson(params));
        try{
            AlipayTradeCloseResponse closeResponse = alipayClient.execute(alipayRequest);
            return closeResponse;
        }catch (AlipayApiException e){
            log.error("支付宝关闭交易失败",e);
        }
        return null;
    }

    @Override
    public String tradeQuery(HttpServletRequest request) {

        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        //商户订单号，商户网站订单系统中唯一订单号
        String outTradeNo = request.getParameter("orderNo");
        OmsOrderDTO omsOrder = omsOrderService.orderDetail(outTradeNo);
        AlipayTradeQueryResponse queryResponse = tradeQuery(outTradeNo,null);
        if(queryResponse.isSuccess()){
            String tradeStatus = queryResponse.getTradeStatus();
            // 交易结束并不可退款
            if(tradeStatus.equalsIgnoreCase(AliTradeStatusEnum.TRADE_FINISHED.getName())){
                omsOrder.setOrderStatus(OrderStatusEnum.TRANSACTION_COMPLETE.getCode());
            }
            // 交易支付成功
            else if(tradeStatus.equalsIgnoreCase(AliTradeStatusEnum.TRADE_SUCCESS.getName())){
                omsOrder.setOrderStatus(OrderStatusEnum.WAIT_SHIP.getCode());
            }
            // 未付款交易超时关闭或支付完成后全额退款
            else if(tradeStatus.equalsIgnoreCase(AliTradeStatusEnum.TRADE_CLOSED.getName())){
                omsOrder.setOrderStatus(OrderStatusEnum.TIMEOUT_CANCEL.getCode());
            }
            // 交易创建并等待买家付款
            else if(tradeStatus.equalsIgnoreCase(AliTradeStatusEnum.WAIT_BUYER_PAY.getName())){
                omsOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.getCode());
            }
        }
        return null;
    }


    @Override
    public AlipayTradeQueryResponse tradeQuery(String  outTradeNo,String tradeNo) {

        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        Map<String,String> params = new HashMap<>();
        if(StringUtils.isNotEmpty(outTradeNo)){
            params.put("out_trade_no",outTradeNo);
        }
        if(StringUtils.isNotEmpty(tradeNo)){
            params.put("trade_no",tradeNo);
        }
        alipayRequest.setBizContent(new Gson().toJson(params));
        try{
            AlipayTradeQueryResponse queryResponse = alipayClient.execute(alipayRequest);
            return queryResponse;
        }catch (AlipayApiException e){
            log.error("支付宝交易查询失败",e);
            throw new RuntimeException("支付宝交易查询失败");
        }
    }

    @Override
    public String orderSync(HttpServletRequest request) {

        AlipayTradeOrderinfoSyncRequest syncRequest = new AlipayTradeOrderinfoSyncRequest();
        syncRequest.setBizContent("{" +
                "\"trade_no\":\"2018061021001004680073956707\"," +
                "\"orig_request_no\":\"HZ01RF001\"," +
                "\"out_request_no\":\"HZ01RF001\"," +
                "\"biz_type\":\"CREDIT_AUTH\"," +
                "\"order_biz_info\":\"\\\"{\\\\\\\"status\\\\\\\":\\\\\\\"COMPLETE\\\\\\\"}\\\"\"" +
                "  }");

        try{
            AlipayTradeOrderinfoSyncResponse response = alipayClient.execute(syncRequest);
            if(response.isSuccess()){
                return "success";
            } else {
                return "fail";
            }
        }catch (AlipayApiException e){
            log.error("支付宝订单同步失败",e);
        }
        return null;
    }

}
