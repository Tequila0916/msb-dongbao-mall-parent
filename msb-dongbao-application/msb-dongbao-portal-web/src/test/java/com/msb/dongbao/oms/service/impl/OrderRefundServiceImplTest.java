package com.msb.dongbao.oms.service.impl;

import com.msb.dongbao.common.base.enums.ErrorCodeEnum;
import com.msb.dongbao.common.base.exception.BusinessException;
import com.msb.dongbao.common.util.BeanUtils;
import com.msb.dongbao.oms.db.dao.OrderRefundDao;
import com.msb.dongbao.oms.model.dto.OmsOrderDTO;
import com.msb.dongbao.oms.model.dto.OrderRefundDTO;
import com.msb.dongbao.oms.model.entity.OrderRefund;
import com.msb.dongbao.oms.service.IOmsOrderService;
import com.msb.dongbao.pay.model.dto.MallRefundDTO;
import com.msb.dongbao.pay.service.IPayStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Mybatis-plus 代码生成器
 *
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 2020年05月27日 17时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(BeanUtils.class)
@Slf4j
public class OrderRefundServiceImplTest {

    @InjectMocks
    OrderRefundServiceImpl orderRefundService;


    @Mock
    private IOmsOrderService omsOrderService;
    @Mock
    private OrderRefundDao orderRefundDao;

    @Mock
    private IPayStrategyService payStrategyService;


    @Test
    public void test_detail_success1(){
        String orderNumber = "123456";
        Mockito.when(orderRefundService.getOne(Mockito.any())).thenReturn(new OrderRefund());
        orderRefundService.detail(orderNumber);
    }

    @Test
    public void test_detail_fail1(){
        try {
            orderRefundService.detail(null);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }

        try {
            orderRefundService.detail("");
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }
    }

    @Test
    public void test_orderRefund_success(){
        OrderRefundDTO orderRefund = new OrderRefundDTO();
        orderRefund.setOrderNumber("123");
        orderRefund.setReason("444");
        try {
            Mockito.when(omsOrderService.orderDetail(Mockito.any())).thenReturn(new OmsOrderDTO());
            Mockito.when(omsOrderService.orderRefundPre(Mockito.any())).thenReturn(true);
            Mockito.when( payStrategyService.refund(Mockito.any(MallRefundDTO.class))).thenReturn(true);
            Mockito.when( orderRefundDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
            orderRefundService.orderRefund(orderRefund);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test_orderRefund_fail1(){
        OrderRefundDTO orderRefund = new OrderRefundDTO();
        orderRefund.setOrderNumber("123");
        orderRefund.setReason("444");
        try {
            Mockito.when(omsOrderService.orderDetail(Mockito.any())).thenReturn(new OmsOrderDTO());
            Mockito.when(omsOrderService.orderRefundPre(Mockito.any())).thenReturn(true);
            Mockito.when( payStrategyService.refund(Mockito.any(MallRefundDTO.class))).thenReturn(false);
            Mockito.when( orderRefundDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
            orderRefundService.orderRefund(orderRefund);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Test
    public void test_orderRefund_fail2(){
        try {
            orderRefundService.orderRefund(null);
        } catch (BusinessException e){
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OrderRefundDTO orderRefundDTO = new OrderRefundDTO();
        try {
            orderRefundService.orderRefund(orderRefundDTO);
        } catch (BusinessException e){
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        orderRefundDTO.setOrderNumber("123");
        orderRefundDTO.setReason(null);
        try {
            orderRefundService.orderRefund(orderRefundDTO);
        } catch (BusinessException e){
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        orderRefundDTO.setOrderNumber(null);
        orderRefundDTO.setReason("123");
        try {
            orderRefundService.orderRefund(orderRefundDTO);
        } catch (BusinessException e){
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Test
    public void test_orderRefundFail_success(){
        Mockito.when(orderRefundDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        orderRefundService.orderRefundFail("123","1234");
    }

    @Test
    public void test_orderRefundFail_fail1(){
        Mockito.when(orderRefundDao.update(Mockito.any(),Mockito.any())).thenReturn(2);
        try {
            orderRefundService.orderRefundFail("123","1234");
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0001001;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }
    }
    @Test
    public void test_orderRefundFail_fail2(){
        try {
            orderRefundService.orderRefundFail("","");
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }

        try {
            orderRefundService.orderRefundFail("","123");
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }

        try {
            orderRefundService.orderRefundFail("123","");
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }


        try {
            orderRefundService.orderRefundFail(null,null);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }

        try {
            orderRefundService.orderRefundFail(null,"123");
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }

        try {
            orderRefundService.orderRefundFail("123",null);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }
    }



    @Test
    public void test_orderRefundSuceess_success(){
        Mockito.when(orderRefundDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        orderRefundService.orderRefundSuccess("123","1234");
    }

    @Test
    public void test_orderRefundSuceess_fail1(){
        Mockito.when(orderRefundDao.update(Mockito.any(),Mockito.any())).thenReturn(2);
        try {
            orderRefundService.orderRefundSuccess("123","1234");
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0001001;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }
    }
    @Test
    public void test_orderRefundSuceess_fail2(){
        try {
            orderRefundService.orderRefundSuccess("","");
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }

        try {
            orderRefundService.orderRefundSuccess("","123");
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }

        try {
            orderRefundService.orderRefundSuccess("123","");
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }


        try {
            orderRefundService.orderRefundSuccess(null,null);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }

        try {
            orderRefundService.orderRefundSuccess(null,"123");
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }

        try {
            orderRefundService.orderRefundSuccess("123",null);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }
    }

}