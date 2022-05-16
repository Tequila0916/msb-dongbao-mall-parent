package com.msb.dongbao.pay.service.impl;

import com.msb.dongbao.common.base.enums.PayLogTypeEnum;
import com.msb.dongbao.common.util.IDUtils;
import com.msb.dongbao.pay.common.PayChannelEnum;
import com.msb.dongbao.pay.model.entity.PayLogData;
import com.msb.dongbao.pay.repository.PayLogDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 操作Cassanra 测试类
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月10日 10时20分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RunWith(SpringRunner.class)
@Slf4j
public class PayLogDataRepositoryTest{

    @InjectMocks
    private PayLogDataServiceImpl payLogDataService;
    @Mock
    private PayLogDataRepository payLogDataRepository;

    @Test
    public void testSaveToCassandra(){
        PayLogData logData = PayLogData.builder().logId(IDUtils.UUID())
                .requestParams("测试")
                .payChannel(PayChannelEnum.ALI_PAY.getValue())
                .logType(PayLogTypeEnum.PAYMENT.getValue())
                .gmtCreate(System.currentTimeMillis())
                .transactionNo(IDUtils.UUID())
                .merchantId("1")
                .orderNo(IDUtils.UUID())
                .build();
        Mockito.when(payLogDataRepository.save(Mockito.any())).thenReturn(logData);
        PayLogData save = payLogDataService.saveLogData(logData);
        assert save.getLogId().equalsIgnoreCase(logData.getLogId());
    }

}
