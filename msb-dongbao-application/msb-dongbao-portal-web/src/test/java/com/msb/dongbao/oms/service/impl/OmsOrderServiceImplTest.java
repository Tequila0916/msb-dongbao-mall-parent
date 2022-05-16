package com.msb.dongbao.oms.service.impl;


import com.msb.dongbao.common.base.dto.PageResult;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.base.enums.ErrorCodeEnum;
import com.msb.dongbao.common.base.exception.BusinessException;
import com.msb.dongbao.common.util.BeanUtils;
import com.msb.dongbao.oms.db.dao.OmsOrderDao;
import com.msb.dongbao.oms.model.dto.OmsOrderDTO;
import com.msb.dongbao.oms.model.dto.OmsOrderPageDTO;
import com.msb.dongbao.oms.model.entity.OmsOrder;
import com.msb.dongbao.oms.model.dto.OmsOrderAndItemsDTO;
import com.msb.dongbao.oms.service.IOmsCancelOrderService;
import com.msb.dongbao.oms.service.IOmsOrderItemService;
import com.msb.dongbao.pms.service.ISkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis-plus 代码生成器
 * 订单类的mock单元测试
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 2020年05月27日 17时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(BeanUtils.class)
@Slf4j
public class OmsOrderServiceImplTest {
    @InjectMocks
    OmsOrderServiceImpl omsOrderService;

    @Mock
     OmsOrderDao omsOrderDao;

    @Mock
    ISkuStockService skuStockService;

    @Mock
    IOmsOrderItemService omsOrderItemService;
    @Mock
    IOmsCancelOrderService omsCancelOrderService;


    @Test
    public void test_detail_success1(){
        String orderNumber = "123456";
        Mockito.when(omsOrderService.getOne(Mockito.any())).thenReturn(new OmsOrder());
        omsOrderService.orderDetail(orderNumber);
    }

    @Test
    public void test_detail_success2(){
        String orderNumber = "123456";
        OmsOrder obj = new OmsOrder();
        Mockito.when(omsOrderService.getOne(Mockito.any())).thenReturn(obj);
        PowerMockito.mockStatic(BeanUtils.class);
        OmsOrderDTO result = new OmsOrderDTO();
        try {
            PowerMockito.when(BeanUtils.copyBean(obj,OmsOrderDTO.class)).thenReturn(new OmsOrderDTO());
        } catch (IllegalAccessException e) {
        } catch (InstantiationException e) {
        }
        OmsOrderDTO detail = omsOrderService.orderDetail(orderNumber);
        //copy之后应该不在改变
        assert detail.equals(result);
    }
    @Test
    public void test_detail_fail1(){
        String orderNumber = "123456";
        OmsOrder obj = new OmsOrder();
        Mockito.when(omsOrderService.getOne(Mockito.any())).thenReturn(obj);

        PowerMockito.mockStatic(BeanUtils.class);
        OmsOrderDTO result = new OmsOrderDTO();

        try {
            PowerMockito.when(BeanUtils.copyBean(obj,OmsOrderDTO.class)).thenThrow(new IllegalAccessException());
        } catch (IllegalAccessException e) {
        } catch (InstantiationException e) {
        }
        OmsOrderDTO detail = omsOrderService.orderDetail(orderNumber);

        assert detail==null;

    }

    @Test
    public void test_getUnpaidOrders_success(){
        omsOrderService.getUnpaidOrders();
    }

    @Test
    public void test_pageListOmsOrder_param_success(){

        OmsOrderPageDTO dto = new OmsOrderPageDTO();
        dto.setPageIndex(1);
        dto.setLength(10);
        List<OmsOrder> list= new ArrayList<>();
        OmsOrder o1 = new OmsOrder();
        list.add(o1);
        try {
            Mockito.when(omsOrderService.list(Mockito.any())).thenReturn(list);
            ResultWrapper<PageResult<OmsOrderAndItemsDTO>> pageResultResultWrapper4 = omsOrderService.pageListOmsOrder(dto);
            assert !CollectionUtils.isEmpty(pageResultResultWrapper4.getData().getContent());
        } catch (Exception e) {
        }

    }




    @Test
    public void test_pageListOmsOrder_param_exception(){
        ResultWrapper<PageResult<OmsOrderAndItemsDTO>> pageResultResultWrapper1 = omsOrderService.pageListOmsOrder(null);
        assert CollectionUtils.isEmpty(pageResultResultWrapper1.getData().getContent());

        OmsOrderPageDTO dto = new OmsOrderPageDTO();
        ResultWrapper<PageResult<OmsOrderAndItemsDTO>> pageResultResultWrapper2 = omsOrderService.pageListOmsOrder(dto);
        assert CollectionUtils.isEmpty(pageResultResultWrapper2.getData().getContent());

        dto.setPageIndex(1);
        ResultWrapper<PageResult<OmsOrderAndItemsDTO>> pageResultResultWrapper3 = omsOrderService.pageListOmsOrder(dto);
        assert CollectionUtils.isEmpty(pageResultResultWrapper3.getData().getContent());


        dto.setPageIndex(null);
        dto.setLength(10);
        ResultWrapper<PageResult<OmsOrderAndItemsDTO>> pageResultResultWrapper4 = omsOrderService.pageListOmsOrder(dto);
        assert CollectionUtils.isEmpty(pageResultResultWrapper4.getData().getContent());

    }






    @Test
    public void test_orderPaySuccess_success(){
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        boolean b = omsOrderService.orderPaySuccess("1234567");
        assert b;
    }

    @Test
    public void test_orderPaySuccess_exception0(){
        try {
            omsOrderService.orderPaySuccess(null);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }

    }

    @Test
    public void test_orderPaySuccess_exception1(){
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(0);

        try {
            omsOrderService.orderPaySuccess("1234567");
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000001;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
            log.error("异常:{}",e);
        }

    }


    @Test
    public void test_cancelTimeOutOrder_success(){
        String orderNumber = "123456";
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        Mockito.when(skuStockService.restoreStock(Mockito.any())).thenReturn(true);
        boolean flag = omsOrderService.cancelTimeOutOrder(orderNumber);
        assert  flag;
    }

    @Test
    public void test_cancelTimeOutOrder_fail1(){
        String orderNumber = "123456";
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(0);
        Mockito.when(skuStockService.restoreStock(Mockito.any())).thenReturn(true);
        try {
            omsOrderService.cancelTimeOutOrder(orderNumber);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000011;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
        }

    }

    @Test
    public void test_cancelTimeOutOrder_fail2(){
        String orderNumber = "123456";
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        Mockito.when(skuStockService.restoreStock(Mockito.any())).thenReturn(false);
        boolean flag = omsOrderService.cancelTimeOutOrder(orderNumber);
        assert  !flag;
    }

    @Test
    public void test_cancelTimeOutOrder_fail3(){
        try {
            omsOrderService.cancelTimeOutOrder(null);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
        }
    }

    @Test
    public void test_cancelOrder_success(){
        String orderNumber = "123456";
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        Mockito.when(skuStockService.restoreStock(Mockito.any())).thenReturn(false);
        boolean flag = omsOrderService.cancelOrder(orderNumber);
        assert  !flag;
    }


    @Test
    public void test_cancelOrder_fail1(){
        String orderNumber = "123456";
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(0);
        Mockito.when(skuStockService.restoreStock(Mockito.any())).thenReturn(true);
        try {
            omsOrderService.cancelOrder(orderNumber);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000011;

            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
        }

    }

    @Test
    public void test_cancelOrder_fail2(){
        String orderNumber = "123456";
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        Mockito.when(skuStockService.restoreStock(Mockito.any())).thenReturn(false);
        boolean flag = omsOrderService.cancelOrder(orderNumber);
        assert  !flag;
    }

    @Test
    public void test_cancelOrder_fail3(){
        try {
            omsOrderService.cancelOrder(null);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
        }
    }
    @Test
    public void test_orderRefundPre_success(){
        String orderNumber = "123456";
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        boolean flag = omsOrderService.orderRefundPre(orderNumber);
        assert  flag;
    }
    @Test
    public void test_orderRefundPre_fail(){
        String orderNumber = "123456";
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(0);
        try {
            omsOrderService.orderRefundPre(orderNumber);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000100;

            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
        }
    }

    @Test
    public void test_orderRefundPre_fail1(){
        try {
            omsOrderService.orderRefundPre(null);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
        }
    }
    @Test
    public void test_orderRefundFailEnd_success(){
        String orderNumber = "123456";
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        boolean flag = omsOrderService.orderRefundFailEnd(orderNumber);
        assert  flag;
    }
    @Test
    public void test_orderRefundFailEnd_fail(){
        String orderNumber = "123456";
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(0);
        try {
            omsOrderService.orderRefundFailEnd(orderNumber);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000101;

            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
        }
    }

    @Test
    public void test_orderRefundFailEnd_fail1(){
        try {
            omsOrderService.orderRefundFailEnd(null);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
        }
    }
    @Test
    public void test_orderRefundSuccessEnd_success(){
        String orderNumber = "123456";
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        boolean flag = omsOrderService.orderRefundSuccessEnd(orderNumber);
        assert  flag;
    }
    @Test
    public void test_orderRefundSuccessEnd_fail(){
        String orderNumber = "123456";
        Mockito.when(omsOrderDao.update(Mockito.any(),Mockito.any())).thenReturn(0);
        try {
            omsOrderService.orderRefundSuccessEnd(orderNumber);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000110;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
        }
    }
    @Test
    public void test_orderRefundSuccessEnd_fail1(){
        try {
            omsOrderService.orderRefundSuccessEnd(null);
        } catch (BusinessException e) {
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OMS0000111;
            assert e.getMessage().equals(errorCodeEnum.msg());
            assert e.getErrorCode() == errorCodeEnum.code();
        }
    }

}