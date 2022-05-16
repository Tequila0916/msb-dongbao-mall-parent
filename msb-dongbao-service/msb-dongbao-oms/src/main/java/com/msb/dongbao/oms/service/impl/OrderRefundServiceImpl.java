package com.msb.dongbao.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.common.base.enums.ErrorCodeEnum;
import com.msb.dongbao.common.base.exception.BusinessException;
import com.msb.dongbao.common.util.BeanUtils;
import com.msb.dongbao.oms.db.dao.OrderRefundDao;
import com.msb.dongbao.oms.model.dto.OmsOrderDTO;
import com.msb.dongbao.oms.model.dto.OrderRefundDTO;
import com.msb.dongbao.oms.model.entity.OrderRefund;
import com.msb.dongbao.oms.model.enums.OrStatusEnum;
import com.msb.dongbao.oms.service.IOmsOrderService;
import com.msb.dongbao.oms.service.IOrderRefundService;
import com.msb.dongbao.pay.model.dto.MallRefundDTO;
import com.msb.dongbao.pay.service.IPayStrategyService;
import com.msb.dongbao.sms.service.ISmsCouponHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * 退单服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-09
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
@Slf4j
public class OrderRefundServiceImpl extends ServiceImpl<OrderRefundDao, OrderRefund> implements IOrderRefundService {

    @Autowired
    private IOmsOrderService omsOrderService;
    @Autowired
    private OrderRefundDao orderRefundDao;

    @Autowired
    private IPayStrategyService payStrategyService;

    @Autowired
    private ISmsCouponHistoryService smsCouponHistoryService;

    @Override
    public OrderRefund detail(String  refundNo){
        if(StringUtils.isEmpty(refundNo)){
            throw new BusinessException(ErrorCodeEnum.OMS0000111);
        }
        Wrapper<OrderRefund> wrapper = new QueryWrapper<OrderRefund>().lambda().eq(OrderRefund::getRefundNo,refundNo);
        return this.getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean orderRefund(OrderRefundDTO orderRefund) throws Exception{
        if (orderRefund ==null || StringUtils.isEmpty(orderRefund.getOrderNumber()) || StringUtils.isEmpty(orderRefund.getReason())) {
            throw new BusinessException(ErrorCodeEnum.OMS0000111);
        }
        String orderNumber = orderRefund.getOrderNumber();
        OmsOrderDTO orderDetail = omsOrderService.orderDetail(orderNumber);
        //更新订单前置状态
        omsOrderService.orderRefundPre(orderNumber);
        String refundOrderNo = generateOrderSn();
        OrderRefund entity = BeanUtils.copyBean(orderRefund,OrderRefund.class);
        entity.setRefundAmount(orderDetail.getPayAmount());
        entity.setOrStatus(OrStatusEnum.WAIT_PROCESS.getCode());
        entity.setRefundNo(refundOrderNo);
        entity.setMerchantId(orderDetail.getMerchantId());
        //插入数据
        this.save(entity);
        MallRefundDTO mallRefundDTO = MallRefundDTO.builder().refundOrderNo(refundOrderNo).build();
        boolean paySuccess = false;
        try {
            paySuccess = payStrategyService.refund(mallRefundDTO);
        } catch (Exception e) {
            log.error("退款发生异常：{}",e);
        }
        if(!paySuccess){
            log.info("单号:{},退款失败,处理相关状态-start-",orderNumber);
            orderRefundFail(orderNumber,refundOrderNo);
            log.info("单号:{},退款失败,处理相关状态-end-");

        }
        return true;
    }

    @Override
    public void orderRefundFail(String orderNumber,String refundOrderNo) {
        if(StringUtils.isEmpty(orderNumber) || StringUtils.isEmpty(refundOrderNo)){
            throw new BusinessException(ErrorCodeEnum.OMS0000111);
        }
        log.info("订单退单失败:订单编号{},退单编号{}",orderNumber,refundOrderNo);
        //更新退单状态  处理失败
        OrderRefund resultEntity = new OrderRefund();
        resultEntity.setOrStatus(OrStatusEnum.FAIL_PROCESS.getCode());
        Wrapper<OrderRefund> orderRefundWrap = new UpdateWrapper<OrderRefund>().lambda()
                .eq(OrderRefund::getOrderNumber,orderNumber)
                .eq(OrderRefund::getRefundNo, refundOrderNo)
                .eq(OrderRefund::getOrStatus, OrStatusEnum.WAIT_PROCESS.getCode());
        //回退订单状态
        omsOrderService.orderRefundFailEnd(orderNumber);
        boolean success = orderRefundDao.update(resultEntity, orderRefundWrap) == 1;
        if(!success){
            throw new BusinessException(ErrorCodeEnum.OMS0001001);
        }
    }

    @Override
    public void orderRefundSuccess(String orderNumber,
                                   String refundOrderNo) {
        if(StringUtils.isEmpty(orderNumber) || StringUtils.isEmpty(refundOrderNo)){
            throw new BusinessException(ErrorCodeEnum.OMS0000111);
        }
        log.info("订单退单成功:订单编号{},退单编号{}",orderNumber,refundOrderNo);
        OrderRefund resultEntity = new OrderRefund();
        resultEntity.setOrStatus(OrStatusEnum.SUCCESS_PROCESS.getCode());
        Wrapper<OrderRefund> orderRefundWrap = new UpdateWrapper<OrderRefund>().lambda()
                .eq(OrderRefund::getOrderNumber,orderNumber)
                .eq(OrderRefund::getRefundNo, refundOrderNo)
                .eq(OrderRefund::getOrStatus, OrStatusEnum.WAIT_PROCESS.getCode());
        omsOrderService.orderRefundSuccessEnd(orderNumber);
        boolean success = orderRefundDao.update(resultEntity, orderRefundWrap) == 1;
        if(!success){
            throw new BusinessException(ErrorCodeEnum.OMS0001001);
        }
        //回退优惠券状态
        smsCouponHistoryService.restoreCouponsByOrderNumber(orderNumber);
    }

    /**
     * 生成28位退单单编号:13位时间戳+2位平台号码+2位支付方式+11位自增数字
     */
    private AtomicLong orderNum = new AtomicLong();
    private String generateOrderSn() {
        StringBuilder sb = new StringBuilder();
        Long time = System.currentTimeMillis();
        Long increment = orderNum.incrementAndGet();
        sb.append(time);
        sb.append("1111");
        sb.append(String.format("%06d", increment));
        return sb.toString();
    }



}
