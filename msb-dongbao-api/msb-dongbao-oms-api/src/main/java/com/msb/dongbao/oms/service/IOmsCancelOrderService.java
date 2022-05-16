package com.msb.dongbao.oms.service;

/**
 * 取消订单服务接口
 *
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 2020年05月27日 17时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IOmsCancelOrderService {
    /**
     *  定时队列添加订单编号
     * @param orderNumber 订单编号
     * @param time 订单创建时间
     */
    void addCancelOrder(String orderNumber, Long time);



}
