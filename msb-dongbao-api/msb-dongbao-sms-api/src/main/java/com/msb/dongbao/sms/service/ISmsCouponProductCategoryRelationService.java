package com.msb.dongbao.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.sms.model.dto.SmsCouponProductCategoryRelationDTO;
import com.msb.dongbao.sms.model.entity.SmsCouponProductCategoryRelation;

import java.util.List;

/**
 * <p>
 * 商品分类和优惠券关联关系 服务接口
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-17
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface ISmsCouponProductCategoryRelationService extends IService<SmsCouponProductCategoryRelation> {

    /**
     * 根据 商品分类ids 获取和优惠券的关联关系
     *
     * @param productCategoryIds
     * @return
     */
    List<SmsCouponProductCategoryRelationDTO> listByPcIds(List<Long> productCategoryIds);

    /**
     * 根据商品分类ids和优惠券ids 获取和优惠券的关联关系
     *
     * @param pcIds     商品分类ids
     * @param couponIds 优惠券ids
     * @return
     */
    List<SmsCouponProductCategoryRelationDTO> listByPcIdsAndCouponIds(List<Long> pcIds, List<Long> couponIds);

    /**
     * 根据优惠券ids 获取优惠券和商品分类的关系
     * @param couponIds
     * @return
     */
    List<SmsCouponProductCategoryRelationDTO> listByCouponIds(List<Long> couponIds);
}
