package com.msb.dongbao.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.pay.model.dto.PayTransactionDTO;
import com.msb.dongbao.pay.model.entity.PayTransaction;

/**
 * <p>
 * 支付流水表 服务类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-09
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IPayTransactionService extends IService<PayTransaction> {

  /**
   * 根据订单编号查询支付流水记录
   * @param orderNo
   * @return
   */
  PayTransactionDTO getByOrderNo(String orderNo);

  /**
   * 更新交易流水的状态为 交易完成
   * @param orderNumber 订单编号
   * @param tradeNo 第三方交易号
   * @return
   */
  Boolean payTransactionFinished(String orderNumber, String tradeNo);

  /**
   * 更新交易流水的状态: 支付中 -> 支付成功
   * @param tradeNo 第三方交易号
   * @param notifyTime  异步通知时间
   * @param paymentTime 支付时间
   * @param orderNumber 订单编号
   * @return
   */
  Boolean payTransactionSuccess(String tradeNo, long notifyTime, long paymentTime, String orderNumber);

  /**
   * 更新交易流水的订单状态: 退款中 -> 交易关闭
   * @param orderNo
   * @return
   */
  Boolean refundTransactionSuccess(String orderNo);

  /**
   * 回滚交易流水的订单状态: 退款中 -> 支付成功
   * @param orderNo
   * @return
   */
  Boolean refundTransactionFail(String orderNo);

  /**
   * 更新交易流水的订单状态: 支付成功 -> 退款中
   * @param orderNumber
   * @return
   */
  Boolean refundTransactionPre(String orderNumber);

}
