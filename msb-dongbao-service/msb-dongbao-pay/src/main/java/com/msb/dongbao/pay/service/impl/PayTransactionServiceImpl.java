package com.msb.dongbao.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.common.base.enums.PayOrderStatusEnum;
import com.msb.dongbao.common.util.BeanUtils;
import com.msb.dongbao.pay.db.dao.PayTransactionDao;
import com.msb.dongbao.pay.model.dto.PayTransactionDTO;
import com.msb.dongbao.pay.model.entity.PayTransaction;
import com.msb.dongbao.pay.service.IPayTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 支付流水表 服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-09
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
public class PayTransactionServiceImpl extends ServiceImpl<PayTransactionDao, PayTransaction> implements IPayTransactionService {

    @Autowired
    private PayTransactionDao payTransactionDao;

    @Override
    public PayTransactionDTO getByOrderNo(String orderNo) {
        if(!StringUtils.isEmpty(orderNo)){
            LambdaQueryWrapper<PayTransaction> lambda = new QueryWrapper<PayTransaction>().lambda();
            // 订单、处理支付中
            lambda.eq(PayTransaction::getRelOrderNo,orderNo)
            .eq(PayTransaction::getEnable,0);
            PayTransaction payTransaction = payTransactionDao.selectOne(lambda);
            PayTransactionDTO payTransactionDTO = new PayTransactionDTO();
            return BeanUtils.copyBean(payTransaction,payTransactionDTO);
        }

        return null;
    }

    @Override
    public Boolean payTransactionFinished(String orderNumber, String tradeNo) {
        PayTransaction updateEntity = new PayTransaction();
        updateEntity.setOrderStatus(PayOrderStatusEnum.PAY_FINISHED.getValue());
        Wrapper<PayTransaction> updateWrapper = new UpdateWrapper<PayTransaction>().lambda()
                .eq(PayTransaction::getRelOrderNo,orderNumber)
                .eq(PayTransaction::getTradeNo,tradeNo);
        return this.payTransactionDao.update(updateEntity,updateWrapper) > 0;
    }

    @Override
    public Boolean payTransactionSuccess(@NotBlank String tradeNo, @NotNull long notifyTime,
                                         @NotNull long paymentTime, @NotBlank String orderNumber) {
        PayTransaction updateEntity = new PayTransaction();
        updateEntity.setTradeNo(tradeNo);
        updateEntity.setNotifyTime(notifyTime);
        updateEntity.setPaymentTime(paymentTime);
        updateEntity.setOrderStatus(PayOrderStatusEnum.PAY_SUCCESS.getValue());
        Wrapper<PayTransaction> updateWrapper = new UpdateWrapper<PayTransaction>().lambda()
                .eq(PayTransaction::getRelOrderNo,orderNumber)
                .eq(PayTransaction::getOrderStatus,PayOrderStatusEnum.PAY_PENDING.getValue());
        return this.baseMapper.update(updateEntity,updateWrapper) > 0;
    }

    @Override
    public Boolean refundTransactionSuccess(@NotBlank String orderNumber) {
        PayTransaction updateEntity = new PayTransaction();
        updateEntity.setOrderStatus(PayOrderStatusEnum.PAY_TRADE_CLOSE.getValue());
        Wrapper<PayTransaction> updateWrapper = new UpdateWrapper<PayTransaction>().lambda()
                .eq(PayTransaction::getRelOrderNo,orderNumber)
                .eq(PayTransaction::getOrderStatus,PayOrderStatusEnum.PAY_REFUND_PENDING.getValue());
        return this.baseMapper.update(updateEntity,updateWrapper) > 0;
    }

    @Override
    public Boolean refundTransactionFail(@NotBlank String orderNumber) {
        PayTransaction updateEntity = new PayTransaction();
        updateEntity.setOrderStatus(PayOrderStatusEnum.PAY_SUCCESS.getValue());
        Wrapper<PayTransaction> updateWrapper = new UpdateWrapper<PayTransaction>().lambda()
                .eq(PayTransaction::getRelOrderNo,orderNumber)
                .eq(PayTransaction::getOrderStatus,PayOrderStatusEnum.PAY_REFUND_PENDING.getValue());
        return this.baseMapper.update(updateEntity,updateWrapper) > 0;
    }

    @Override
    public Boolean refundTransactionPre(String orderNumber) {
        PayTransaction updateEntity = new PayTransaction();
        updateEntity.setOrderStatus(PayOrderStatusEnum.PAY_REFUND_PENDING.getValue());
        Wrapper<PayTransaction> updateWrapper = new UpdateWrapper<PayTransaction>().lambda()
                .eq(PayTransaction::getRelOrderNo,orderNumber)
                .eq(PayTransaction::getOrderStatus,PayOrderStatusEnum.PAY_SUCCESS.getValue());
        return this.baseMapper.update(updateEntity,updateWrapper) > 0;
    }
}
