package com.msb.dongbao.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.common.base.dto.PageResult;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.oms.model.dto.OmsOrderDTO;
import com.msb.dongbao.oms.model.dto.OmsOrderPageDTO;
import com.msb.dongbao.oms.model.dto.OrderParamNewDTO;
import com.msb.dongbao.oms.model.entity.OmsOrder;
import com.msb.dongbao.oms.model.dto.OmsOrderAndItemsDTO;

import java.util.List;

/**
 * <p>
 * 订单服务接口类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IOmsOrderService extends IService<OmsOrder> {

    /**
     * 分页查询
     *
     * @param pageDTO
     * @return
     */
    ResultWrapper<PageResult<OmsOrderAndItemsDTO>> pageListOmsOrder(OmsOrderPageDTO pageDTO);


    /**
     * 获取订单详情 无商品详情
     *
     * @param orderNumber 订单编号
     * @return
     */
    OmsOrderDTO orderDetail(String orderNumber);

    /**
     * 获得订单详情  有商品详情
     *
     * @param orderNumber 订单编号
     * @return
     */
    OmsOrderAndItemsDTO orderAndItemsDetail(String orderNumber);


    /**
     * 生成订单
     *
     * @param orderParamNewDTO
     * @return
     */
    ResultWrapper<OmsOrder> generateOrder(OrderParamNewDTO orderParamNewDTO);


    /**
     * 支付成功回调接口,修改订单状态
     *
     * @param orderNumber
     * @return
     */
    boolean orderPaySuccess(String orderNumber);


    /**
     * 获取代付款的订单---超时取消业务初始化使用
     */
    List<OmsOrder> getUnpaidOrders();


    /**
     * 取消未付款的订单---系统自动取消
     *
     * @return
     */
    boolean cancelTimeOutOrder(String orderNumber);

    /**
     * 取消未付款的订单---手动取消
     *
     * @param orderNumber
     */
    boolean cancelOrder(String orderNumber);

    /**
     * 退单预更新 前置状态  退单中
     *
     * @param orderNumber
     * @return
     */
    boolean orderRefundPre(String orderNumber);

    /**
     * 退单取消更新 终态 回退状态
     *
     * @param orderNumber
     * @return
     */
    boolean orderRefundFailEnd(String orderNumber);

    /**
     * 退单更新 终态 退单完成---成功还是失败请查退单表
     *
     * @param orderNumber
     * @return
     */
    boolean orderRefundSuccessEnd(String orderNumber);
}
