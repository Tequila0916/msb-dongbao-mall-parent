package com.msb.dongbao.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.sms.model.dto.SmsCouponProductRelationDTO;
import com.msb.dongbao.sms.model.entity.SmsCouponProductRelation;

import java.util.List;

/**
 * <p>
 *  优惠券和商品关联关系服务接口
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-16
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface ISmsCouponProductRelationService extends IService<SmsCouponProductRelation> {

  /**
   * 根据spuIds 查询优惠券和商品类别的关系
   * @param spuIds
   * @return
   */
  List<SmsCouponProductRelationDTO> listBySpuIds(List<Long> spuIds);


  /**
   * 根据spuId 查询商品和优惠券关系
   * @param spuId
   * @return
   */
  List<SmsCouponProductRelationDTO> listBySpuId(Long spuId);


  /**
   * 根据优惠券ids 获得和商品的关联关系
   * @param couponIds
   * @return
   */
  List<SmsCouponProductRelationDTO> listByCouponIds(List<Long> couponIds);


}
