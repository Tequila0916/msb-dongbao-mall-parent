package com.msb.dongbao.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.common.base.enums.RefundStatusEnum;
import com.msb.dongbao.common.util.BeanUtils;
import com.msb.dongbao.pay.db.dao.PayRefundDao;
import com.msb.dongbao.pay.model.dto.PayRefundDTO;
import com.msb.dongbao.pay.model.entity.PayRefund;
import com.msb.dongbao.pay.service.IPayRefundService;
import com.msb.dongbao.pay.service.IPayTransactionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 退款记录表 服务实现类
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
public class PayRefundServiceImpl extends ServiceImpl<PayRefundDao, PayRefund> implements IPayRefundService {

    @Autowired
    private IPayTransactionService payTransactionService;
    @Autowired
    private PayRefundDao payRefundDao;

    @Override
    public PayRefundDTO getByRefundOrderNo(String orderNo) throws Exception {
        if(StringUtils.isNotEmpty(orderNo)){
            LambdaQueryWrapper<PayRefund> lambda = new QueryWrapper<PayRefund>().lambda();
            lambda.eq(PayRefund::getRelRefundOrderNo,orderNo);
            PayRefund payRefund = payRefundDao.selectOne(lambda);
            return BeanUtils.copyBean(payRefund, PayRefundDTO.class);
        }
        return  null;
    }

    @Override
    public Boolean refundSuccess(String refundOrderNo, String orderNo) {
        PayRefund updateEntity = new PayRefund();
        updateEntity.setRefundStatus(RefundStatusEnum.REFUND_SUCCESS.getValue());
        Wrapper<PayRefund> refundWrapper = new UpdateWrapper<PayRefund>().lambda()
                .eq(PayRefund::getRelRefundOrderNo,refundOrderNo)
                .eq(PayRefund::getRefundStatus, RefundStatusEnum.REFUND_PENDING.getValue());
        this.payRefundDao.update(updateEntity,refundWrapper);
        return payTransactionService.refundTransactionSuccess(orderNo);
    }

    @Override
    public Boolean refundFail(String refundOrderNo,String orderNo) {
        PayRefund updateEntity = new PayRefund();
        updateEntity.setRefundStatus(RefundStatusEnum.REFUND_FAIL.getValue());
        Wrapper<PayRefund> refundWrapper = new UpdateWrapper<PayRefund>().lambda()
                .eq(PayRefund::getRelRefundOrderNo,refundOrderNo)
                .eq(PayRefund::getRefundStatus, RefundStatusEnum.REFUND_PENDING.getValue());
        this.payRefundDao.update(updateEntity,refundWrapper);
        return  payTransactionService.refundTransactionFail(orderNo);
    }
}
