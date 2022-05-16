package com.msb.dongbao.pay.service.impl;

import com.msb.dongbao.pay.db.dao.PayRefundDao;
import com.msb.dongbao.pay.model.dto.PayRefundDTO;
import com.msb.dongbao.pay.model.entity.PayRefund;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 退款流水测试类
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月17日 16时10分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RunWith(SpringRunner.class)
@Slf4j
public class PayRefundServiceImplTest{

    @InjectMocks
    PayRefundServiceImpl payRefundService;
    @Mock
    PayRefundDao payRefundDao;
    @Mock
    PayTransactionServiceImpl payTransactionService;

    @Test
    public void getByRefundOrderNo() {
        String orderNo = "15923023850101111000001";
        PayRefund payRefund = new PayRefund();
        payRefund.setRelOrderNo(orderNo);
        Mockito.when(payRefundDao.selectOne(Mockito.any())).thenReturn(payRefund);
        PayRefundDTO payRefundDTO = null;
        try {
            payRefundDTO = payRefundService.getByRefundOrderNo(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert payRefundDTO.getRelOrderNo().equalsIgnoreCase(orderNo);
    }

    @Test
    public void refundSuccess() {
        Mockito.when(payRefundDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        Mockito.when(payTransactionService.refundTransactionSuccess(Mockito.any())).thenReturn(true);
        Boolean aBoolean = payRefundService.refundSuccess(Mockito.any(), Mockito.any());
        assert aBoolean;
    }

    @Test
    public  void refundFail() {
        Mockito.when(payRefundDao.update(Mockito.any(),Mockito.any())).thenReturn(1);
        Mockito.when(payTransactionService.refundTransactionFail(Mockito.any())).thenReturn(true);
        Boolean aBoolean = payRefundService.refundFail(Mockito.any(), Mockito.any());
        assert aBoolean;
    }
}