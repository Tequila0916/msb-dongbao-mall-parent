package com.msb.dongbao.oms.service.impl;

import com.msb.dongbao.oms.model.dto.CancelOrderDTO;
import com.msb.dongbao.oms.model.entity.OmsOrder;
import com.msb.dongbao.oms.service.IOmsCancelOrderService;
import com.msb.dongbao.oms.service.IOmsOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 订单超时取消
 *
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 2020年05月27日 17时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
@Slf4j
public class OmsCancelOrderServiceImpl implements IOmsCancelOrderService {


    @Autowired
    private IOmsOrderService omsOrderService;


    DelayQueue<CancelOrderDTO> queue = new DelayQueue<>();

    static final int MINUTE = 2;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8,
            8,
            0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1024));

    private volatile boolean flag;

    @PostConstruct
    public void init() {
        //初始化时加载数据库中需处理超时的订单*
        List<OmsOrder> timeOutOrders = omsOrderService.getUnpaidOrders();
        timeOutOrders.stream().forEach(dto -> {
            this.addCancelOrder(dto.getOrderNumber(), dto.getGmtCreate());
        });
        flag = true;
        ///启动一个线程，去取延迟消息
        threadPoolExecutor.execute(() -> {
            CancelOrderDTO message;
            for (; ; ) {
                if (!flag) {
                    return;
                }
                try {
                    message = queue.take();
                    //处理超时订单
                    omsOrderService.cancelTimeOutOrder(message.getOrderNumber());
                } catch (Exception e) {
                    log.error("定时任务取消订单失败:{}", e);
                }
            }
        });
    }

    //处理超时时间
    private Long handleEndTime(Long createTime) {
        Long endTime = createTime + MINUTE * 60 * 1000;
        return endTime;
    }

    /**
     * 关闭线程池
     */
    @PreDestroy
    public void destory() {
        flag = false;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
        }

    }


    @Override
    public void addCancelOrder(String orderNumber, Long time) {
        CancelOrderDTO cancelOrderDTO = new CancelOrderDTO(orderNumber, handleEndTime(time));
        queue.put(cancelOrderDTO);
    }


}
