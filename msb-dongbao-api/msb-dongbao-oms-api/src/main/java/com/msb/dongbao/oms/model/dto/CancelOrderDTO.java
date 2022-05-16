package com.msb.dongbao.oms.model.dto;

import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *  订单延时队列的元素
 *
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 2020年05月27日 17时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
public class CancelOrderDTO implements Delayed {

    private String orderNumber;

    private Long endTime;

    public CancelOrderDTO(String orderNumber, long endTime) {
        this.orderNumber = orderNumber;
        this.endTime = endTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return endTime-System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        CancelOrderDTO order = (CancelOrderDTO) o;
        long diff = this.endTime - order.endTime;
        if (diff <= 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
