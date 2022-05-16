package com.msb.dongbao.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.sms.db.dao.SmsCouponProductCategoryRelationDao;
import com.msb.dongbao.sms.model.dto.SmsCouponProductCategoryRelationDTO;
import com.msb.dongbao.sms.model.entity.SmsCouponProductCategoryRelation;
import com.msb.dongbao.sms.service.ISmsCouponProductCategoryRelationService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-17
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
public class SmsCouponProductCategoryRelationServiceImpl extends ServiceImpl<SmsCouponProductCategoryRelationDao, SmsCouponProductCategoryRelation> implements ISmsCouponProductCategoryRelationService {

    @Override
    public List<SmsCouponProductCategoryRelationDTO> listByPcIds(List<Long> productCategoryIds) {
        if (CollectionUtils.isEmpty(productCategoryIds)) {
            return new ArrayList<>();
        }
        Wrapper<SmsCouponProductCategoryRelation> queryWrapper = new QueryWrapper<SmsCouponProductCategoryRelation>().lambda()
                .in(SmsCouponProductCategoryRelation::getProductCategoryId, productCategoryIds);
        List<SmsCouponProductCategoryRelation> list = this.list(queryWrapper);
        List<SmsCouponProductCategoryRelationDTO> dtoList = list.stream()
                .map(SmsCouponProductCategoryRelationDTO::new)
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public List<SmsCouponProductCategoryRelationDTO> listByPcIdsAndCouponIds(List<Long> pcIds, List<Long> couponIds) {
        if (CollectionUtils.isEmpty(pcIds) || CollectionUtils.isEmpty(couponIds)) {
            return new ArrayList<>();
        }
        Wrapper<SmsCouponProductCategoryRelation> queryWrapper = new QueryWrapper<SmsCouponProductCategoryRelation>().lambda()
                .in(SmsCouponProductCategoryRelation::getProductCategoryId, pcIds)
                .in(SmsCouponProductCategoryRelation::getCouponId, couponIds);
        List<SmsCouponProductCategoryRelation> list = this.list(queryWrapper);
        List<SmsCouponProductCategoryRelationDTO> dtoList = list.stream()
                .map(SmsCouponProductCategoryRelationDTO::new)
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public List<SmsCouponProductCategoryRelationDTO> listByCouponIds(List<Long> couponIds) {
        if (CollectionUtils.isEmpty(couponIds)) {
            return new ArrayList<>();
        }
        Wrapper<SmsCouponProductCategoryRelation> queryWrapper = new QueryWrapper<SmsCouponProductCategoryRelation>().lambda()
                .in(SmsCouponProductCategoryRelation::getCouponId, couponIds);
        List<SmsCouponProductCategoryRelation> list = this.list(queryWrapper);
        List<SmsCouponProductCategoryRelationDTO> dtoList = list.stream()
                .map(SmsCouponProductCategoryRelationDTO::new)
                .collect(Collectors.toList());
        return dtoList;
    }
}
