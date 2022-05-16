package com.msb.dongbao.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.sms.model.dto.SmsCouponBySpuDTO;
import com.msb.dongbao.sms.model.dto.SmsCouponDTO;
import com.msb.dongbao.sms.model.entity.SmsCouponHistory;
import com.msb.dongbao.sms.model.dto.SmsCouponByPcDTO;
import com.msb.dongbao.sms.model.dto.SmsCouponHaveDescriptionDTO;

import java.util.List;

/**
 * <p>
 * 优惠券历史服务接口
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-16
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface ISmsCouponHistoryService extends IService<SmsCouponHistory> {


    /**
     * 用户领取优惠券,新增优惠券历史记录
     *
     * @param id
     * @return
     */
    ResultWrapper<String> doSave(Long id);


    /**
     * 根据分类(productCategory)Ids ,获取当前用户领取的优惠券信息(可用sql实现,但是无法兼容mongodb)
     *
     * @param productCategoryIds
     * @return
     */
    ResultWrapper<List<SmsCouponByPcDTO>> listByProductCategoryIdsAndUser(List<Long> productCategoryIds);

    /**
     * 根据spuIds 获取优惠券
     *
     * @param spuIds
     * @return
     */
    ResultWrapper<List<SmsCouponBySpuDTO>> listBySpuIds(List<Long> spuIds);

    /**
     * 根据spuId和当前用户 获取优惠券信息
     * 商品详情页面需要展示优惠券信息,
     * 用户领取过的则显示不可领取(但是会展示出来),
     * 用户没有领取过则可以在此页面领取
     * <p>
     * 用户信息只是做展示处理
     *
     * @param spuId
     * @return
     */
    ResultWrapper<List<SmsCouponHaveDescriptionDTO>> listBySpuIdOrUser(Long spuId);

    /**
     * 根据商品分类id 和当前用户 获取优惠券信息
     * 商品详情页面需要展示优惠券信息,
     * 用户领取过的则显示不可领取(但是会展示出来),
     * 用户没有领取过则可以在此页面领取
     * <p>
     * 用户信息只是做展示处理
     *
     * @param productCategoryId
     * @return
     */
    ResultWrapper<List<SmsCouponHaveDescriptionDTO>> listByProductCategoryIdOrUser(Long productCategoryId);


    /**
     * 根据商品spuId 商品分类id 用户 查询优惠券信息
     *
     * @param spuId
     * @param productCategoryId
     * @return
     */
    ResultWrapper<List<SmsCouponHaveDescriptionDTO>> listBySpuIdOrProductCategoryIdOrUser(Long spuId, Long productCategoryId);



    /**
     * 根据当前用户获取优惠券信息
     *
     * @return
     */
    ResultWrapper<List<SmsCouponHaveDescriptionDTO>> listByUser();

    /**
     * 修改用户的优惠券使用记录
     * @param couponDTOS 优惠券集合 (可能为空)
     * @param orderNumber 订单编号
     */

    void useCoupons(List<SmsCouponDTO> couponDTOS, String orderNumber);

    /**
     * 退单的时候 恢复用户的优惠券
     * @param orderNumber
     */
    void restoreCouponsByOrderNumber(String orderNumber);

}
