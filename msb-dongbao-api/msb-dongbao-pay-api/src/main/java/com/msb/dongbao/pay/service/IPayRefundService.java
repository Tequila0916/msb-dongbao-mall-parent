package com.msb.dongbao.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.pay.model.dto.PayRefundDTO;
import com.msb.dongbao.pay.model.entity.PayRefund;

/**
 * <p>
 * 退款记录表 服务类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-09
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IPayRefundService extends IService<PayRefund> {
  /**
   * 根据退款订单编号查询退款交易流水
   * @param refundOrderNo
   * @return
   * @throws Exception
   */
  PayRefundDTO getByRefundOrderNo(String refundOrderNo) throws Exception;

  /**
   * 更新退款流水的状态: 退款中 -> 退款成功
   * 基于DB乐观锁
   * @param refundOrderNo 退款订单号
   * @param orderNo       订单编号
   * @return
   */
  Boolean refundSuccess(String refundOrderNo, String orderNo);

  /**
   * 更新退款的流水状态: 退款中 -> 退款失败
   * 基于DB乐观锁
   * @param refundOrderNo 退款订单号
   * @param orderNo 订单编号
   * @return
   */
  Boolean refundFail(String refundOrderNo,String orderNo);

}
