package com.msb.dongbao.job.order;

import com.alipay.api.AlipayClient;
import com.msb.dongbao.oms.service.IOmsOrderService;
import com.msb.dongbao.pay.service.IPayTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 检查订单任务
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月16日 10时00分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Component
public class PayAndOrderJob {

    @Autowired
    private IOmsOrderService omsOrderService;
    @Autowired
    private IPayTransactionService payTransactionService;
    @Autowired
    private AlipayClient alipayClient;


    /**
     * 定时查询 未支付 待处理的业务订单，发送到消息队列，启用多个消费端去处理
     * 通过订单编号去查询支付宝的交易状态，进而更新订单状态
     * 每隔1小时执行一次
     */
    @Scheduled(cron ="0 0 0/1 * * ?")
    public void aliOrderPayCheck(){
        // TODO 执行任务
    }



    /**
     * 定时查询 退款中 业务订单，发送到消息队列，启用多个消费端去处理
     * 每隔1小时执行一次
     */
    @Scheduled(cron ="0 0 0/1 * * ?")
    public void aliOrderRefundCheck(){
        // TODO 执行任务
    }
}
