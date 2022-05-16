package com.msb.dongbao.pay.service.impl;

import com.msb.dongbao.pay.db.dao.PayTransactionDao;
import com.msb.dongbao.pay.model.dto.PayTransactionDTO;
import com.msb.dongbao.pay.model.entity.PayTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Supplier;

/**
 * 支付流水测试类
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月17日 16时30分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RunWith(SpringRunner.class)
@Slf4j
public class PayTransactionServiceImplTest{

    @InjectMocks
    private PayTransactionServiceImpl payTransactionService;
    @Mock
    PayTransactionDao payTransactionDao;

    @Test
    public void getByOrderNo() {
        String orderNo = "15923305414690001000001";
        PayTransaction payTransaction = new PayTransaction();
        payTransaction.setRelOrderNo(orderNo);
        Mockito.when(payTransactionDao.selectOne(Mockito.any())).thenReturn(payTransaction);
        PayTransactionDTO payTransactionDTO = payTransactionService.getByOrderNo(orderNo);
        assert orderNo.equalsIgnoreCase(payTransactionDTO.getRelOrderNo());
    }

    @Test
    public void payTransactionFinished() {

        String orderNo = "15923305414690001000001";
        String tradeNo = "2020061722001466500501067595";
        Mockito.when(payTransactionDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        Boolean flag = payTransactionService.payTransactionFinished(orderNo, tradeNo);
        assert flag;

    }

    @Test
    public void payTransactionSuccess() {
        String orderNo = "15923305414690001000001";
        String tradeNo = "2020061722001466500501067595";
        long now = System.currentTimeMillis();
        Mockito.when(payTransactionDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        Boolean flag = payTransactionService.payTransactionSuccess(tradeNo, now, now, orderNo);
        assert flag;
    }

    @Test
    public void refundTransactionSuccess() {
        String orderNo = "15923305414690001000001";
        Mockito.when(payTransactionDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        Boolean flag = payTransactionService.refundTransactionSuccess(orderNo);
        assert flag;
    }

    @Test
    public void refundTransactionFail() {
        String orderNo = "15923305414690001000001";
        Mockito.when(payTransactionDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        Boolean flag = payTransactionService.refundTransactionFail(orderNo);
        assert flag;
    }



    @Test
    public void refundTransactionPre() {
        String orderNo = "15923305414690001000001";
        Boolean aBoolean = payTransactionService.refundTransactionPre(orderNo);
        Supplier messageSupplier  = ()-> "refundTransactionPre()判断失败";
        Assertions.assertEquals(true,aBoolean,messageSupplier);
    }
}