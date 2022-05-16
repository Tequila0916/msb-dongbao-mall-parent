package com.msb.dongbao.sms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.sms.model.dto.SmsCouponDTO;
import com.msb.dongbao.sms.model.entity.SmsCoupon;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-16
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface ISmsCouponService extends IService<SmsCoupon> {

    /**
     * 根据 使用类型和ids 去获取优惠券
     * 当适用类型为空的时候 不做条件
     *
     * @param userType 使用类型
     * @param couponIds
     * @return
     */
    List<SmsCouponDTO> listByUseTypeAndCouponIds(Integer userType, List<Long> couponIds);

    /**
     * 根据couponIds获取优惠券
     *
     * @param couponIds
     * @return
     */
    List<SmsCouponDTO> listByCouponIds(List<Long> couponIds);


}
