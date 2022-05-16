package com.msb.dongbao.sms.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.sms.db.dao.SmsCouponDao;
import com.msb.dongbao.sms.model.dto.SmsCouponDTO;
import com.msb.dongbao.sms.model.dto.SmsCouponProductCategoryRelationDTO;
import com.msb.dongbao.sms.model.dto.SmsCouponProductRelationDTO;
import com.msb.dongbao.sms.model.entity.SmsCoupon;
import com.msb.dongbao.sms.model.enums.UseTypeEnum;
import com.msb.dongbao.sms.service.ISmsCouponProductCategoryRelationService;
import com.msb.dongbao.sms.service.ISmsCouponProductRelationService;
import com.msb.dongbao.sms.service.ISmsCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券相关-服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-16
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
public class SmsCouponServiceImpl extends ServiceImpl<SmsCouponDao, SmsCoupon> implements ISmsCouponService {


    @Autowired
    private ISmsCouponProductRelationService smsCouponProductRelationService;
    @Autowired
    private ISmsCouponProductCategoryRelationService smsCouponProductCategoryRelationService;

    @Override
    public List<SmsCouponDTO> listByUseTypeAndCouponIds(Integer useType, List<Long> couponIds) {
        if (CollectionUtils.isEmpty(couponIds)) {
            return new ArrayList<>();
        }
        //优惠券
        LambdaQueryWrapper<SmsCoupon> queryWrapper = new QueryWrapper<SmsCoupon>().lambda()
                // .ge(SmsCoupon::getEndTime, new Date())
                // .le(SmsCoupon::getEnableTime,new Date())
                .in(SmsCoupon::getId, couponIds);
        if (useType != null) {
            queryWrapper.eq(SmsCoupon::getUseType, useType);
        }

        List<SmsCouponDTO> dtoList = getSmsCouponDTOS(queryWrapper);
        return dtoList;
    }

    @Override
    public List<SmsCouponDTO> listByCouponIds(List<Long> couponIds) {
        if (CollectionUtils.isEmpty(couponIds)) {
            return new ArrayList<>();
        }
        //优惠券
        Wrapper<SmsCoupon> queryWrapper = new QueryWrapper<SmsCoupon>().lambda()
                // .ge(SmsCoupon::getEndTime, new Date())
                // .le(SmsCoupon::getEnableTime,new Date())
                .in(SmsCoupon::getId, couponIds);
        List<SmsCouponDTO> resultList = getSmsCouponDTOS(queryWrapper);
        Map<String, List<SmsCouponDTO>> useTypeListMap = resultList.stream().collect(Collectors.groupingBy(obj -> obj.getUseType().toString()));
        //商品分类相关
        List<SmsCouponDTO> pcList = useTypeListMap.get(UseTypeEnum.CLASSIFICATION.getCode() + "");
        List<Long> pcCouponIds = pcList.stream().map(obj -> obj.getId()).collect(Collectors.toList());
        List<SmsCouponProductCategoryRelationDTO> pcRelationList = smsCouponProductCategoryRelationService
                .listByCouponIds(pcCouponIds);
        Map<Long, List<SmsCouponProductCategoryRelationDTO>> pcMap = pcRelationList.stream()
                .collect(Collectors.groupingBy(obj -> obj.getCouponId()));
        //spu相关
        List<SmsCouponDTO> spuList = Optional.ofNullable(useTypeListMap.get(UseTypeEnum.PRODUCT.getCode() + "")).orElse(new ArrayList<>());
        List<Long> spuCouponIds = spuList.stream().map(obj -> obj.getId()).collect(Collectors.toList());
        List<SmsCouponProductRelationDTO> puRelationList = smsCouponProductRelationService
                .listByCouponIds(spuCouponIds);
        Map<Long, List<SmsCouponProductRelationDTO>> spuMap = puRelationList.stream()
                .collect(Collectors.groupingBy(obj -> obj.getCouponId()));
        //填充分类关系
        resultList.stream().forEach(obj->{
            Integer useType = obj.getUseType();
            if(useType - UseTypeEnum.CLASSIFICATION.getCode() == 0){
                obj.setCouponProductCategoryRelationDTO(pcMap.get(obj.getId()).get(0));
            }else if(useType - UseTypeEnum.PRODUCT.getCode() == 0){
                obj.setCouponProductRelationDTO(spuMap.get(obj.getId()).get(0));
            }
        });
        return resultList;
    }

    private List<SmsCouponDTO> getSmsCouponDTOS(Wrapper<SmsCoupon> queryWrapper) {
        List<SmsCoupon> smsCoupons = this.list(queryWrapper);
        return smsCoupons.stream().map(SmsCouponDTO::new)
                .collect(Collectors.toList());
    }
}
