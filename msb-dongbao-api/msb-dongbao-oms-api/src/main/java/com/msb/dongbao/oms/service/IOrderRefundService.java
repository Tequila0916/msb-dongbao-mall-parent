package com.msb.dongbao.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.oms.model.dto.OrderRefundDTO;
import com.msb.dongbao.oms.model.entity.OrderRefund;

/**
 * <p>
 * 退单服务接口
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-09
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IOrderRefundService extends IService<OrderRefund> {


    /**
     * 查询退单记录详情---支付模块用
     *
     * @param refundNo
     * @return
     */
    OrderRefund detail(String refundNo);

    /**
     * 提交退款申请 暂时只支持退整个订单
     */
    boolean orderRefund(OrderRefundDTO orderRefund) throws Exception;

    /**
     * 退单失败 回调接口
     *
     * @param orderNumber
     * @param refundOrderNo
     */
    void orderRefundFail(String orderNumber, String refundOrderNo);

    /**
     * 退单成功 回调接口
     *
     * @param orderNumber
     * @param refundOrderNo
     * @return
     */
    void orderRefundSuccess(String orderNumber, String refundOrderNo);


}
