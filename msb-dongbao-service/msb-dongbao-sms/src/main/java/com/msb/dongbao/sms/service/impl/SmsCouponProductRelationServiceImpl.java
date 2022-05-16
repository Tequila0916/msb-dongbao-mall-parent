package com.msb.dongbao.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.sms.db.dao.SmsCouponProductRelationDao;
import com.msb.dongbao.sms.model.dto.SmsCouponProductRelationDTO;
import com.msb.dongbao.sms.model.entity.SmsCouponProductRelation;
import com.msb.dongbao.sms.service.ISmsCouponProductRelationService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券和商品之间关联-服务实现类
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
public class SmsCouponProductRelationServiceImpl
        extends ServiceImpl<SmsCouponProductRelationDao, SmsCouponProductRelation>
        implements ISmsCouponProductRelationService {



    @Override
    public List<SmsCouponProductRelationDTO> listBySpuIds(List<Long> spuIds) {
        if (CollectionUtils.isEmpty(spuIds)) {
            return new ArrayList<>();
        }
        Wrapper<SmsCouponProductRelation> queryWrapper = new QueryWrapper<SmsCouponProductRelation>()
                .lambda()
                .in(SmsCouponProductRelation::getProductId, spuIds);
        List<SmsCouponProductRelationDTO> result = getSmsCouponProductRelationDTOS(queryWrapper);
        return result;
    }

    @Override
    public List<SmsCouponProductRelationDTO> listBySpuId(Long spuId) {
        Wrapper<SmsCouponProductRelation> queryWrapper = new QueryWrapper<SmsCouponProductRelation>().lambda()
                .eq(SmsCouponProductRelation::getProductId, spuId);
        List<SmsCouponProductRelationDTO> result = getSmsCouponProductRelationDTOS(queryWrapper);
        return result;
    }

    @Override
    public List<SmsCouponProductRelationDTO> listByCouponIds(List<Long> couponIds) {
        if(CollectionUtils.isEmpty(couponIds)){
            return new ArrayList<>();
        }
        Wrapper<SmsCouponProductRelation> queryWrapper = new QueryWrapper<SmsCouponProductRelation>().lambda()
                .in(SmsCouponProductRelation::getCouponId, couponIds);
        List<SmsCouponProductRelationDTO> result = getSmsCouponProductRelationDTOS(queryWrapper);
        return result;
    }

    private List<SmsCouponProductRelationDTO> getSmsCouponProductRelationDTOS(Wrapper<SmsCouponProductRelation> queryWrapper) {
        List<SmsCouponProductRelation> list = this.list(queryWrapper);
        return list.stream()
                .map(SmsCouponProductRelationDTO::new)
                .collect(Collectors.toList());
    }
}
