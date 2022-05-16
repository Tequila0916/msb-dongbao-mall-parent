package com.msb.dongbao.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.base.enums.ErrorCodeEnum;
import com.msb.dongbao.common.base.exception.BusinessException;
import com.msb.dongbao.common.util.BeanUtils;
import com.msb.dongbao.sms.db.dao.SmsCouponHistoryDao;
import com.msb.dongbao.sms.model.dto.*;
import com.msb.dongbao.sms.model.entity.SmsCoupon;
import com.msb.dongbao.sms.model.entity.SmsCouponHistory;
import com.msb.dongbao.sms.model.enums.UseStatusEnum;
import com.msb.dongbao.sms.model.enums.UseTypeEnum;
import com.msb.dongbao.sms.model.dto.SmsCouponByPcDTO;
import com.msb.dongbao.sms.model.dto.SmsCouponHaveDescriptionDTO;
import com.msb.dongbao.sms.service.ISmsCouponHistoryService;
import com.msb.dongbao.sms.service.ISmsCouponProductCategoryRelationService;
import com.msb.dongbao.sms.service.ISmsCouponProductRelationService;
import com.msb.dongbao.sms.service.ISmsCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券历史记录-服务实现类
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
public class SmsCouponHistoryServiceImpl extends ServiceImpl<SmsCouponHistoryDao, SmsCouponHistory> implements ISmsCouponHistoryService {

    @Autowired
    private ISmsCouponService smsCouponService;

    @Autowired
    private ISmsCouponProductCategoryRelationService smsCouponProductCategoryRelationService;

    @Autowired
    private ISmsCouponProductRelationService smsCouponProductRelationService;

    @Autowired
    private SmsCouponHistoryDao smsCouponHistoryDao;


    @Override
    public ResultWrapper<String> doSave(Long couponId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usercode = Optional.ofNullable(authentication).map(obj -> obj.getName()).orElse("");
        SmsCoupon coupon = smsCouponService.getById(couponId);
        if (coupon == null) {
            throw new BusinessException(ErrorCodeEnum.SMS0000001);
        }
        if (coupon.getCount() <= 0) {
            throw new BusinessException(ErrorCodeEnum.SMS0000010);
        }
        Date now = new Date();
        if (now.before(new Date(coupon.getEnableTime()))) {
            throw new BusinessException(ErrorCodeEnum.SMS0000011);
        }
        //判断用户领取的优惠券数量是否超过限制
        Wrapper<SmsCouponHistory> queryWrapper = new QueryWrapper<SmsCouponHistory>().lambda()
                .eq(SmsCouponHistory::getMemberId, usercode)
                .eq(SmsCouponHistory::getCouponId, couponId);
        List<SmsCouponHistory> list = this.list(queryWrapper);

        if (list.size() >= coupon.getPerLimit()) {
            throw new RuntimeException("领取该优惠券次数已经用完");
        }
        //生成领取优惠券历史
        SmsCouponHistory couponHistory = new SmsCouponHistory();
        couponHistory.setCouponId(coupon.getId());
        couponHistory.setCouponCode(generateCouponCode(usercode));
        couponHistory.setCreateTime(now.getTime());
        couponHistory.setMemberId(usercode);
        couponHistory.setMemberNickname(usercode);
        //主动领取
        couponHistory.setGetType(1);
        //未使用
        couponHistory.setUseStatus(0);
        //保存
        this.save(couponHistory);
        //修改优惠券表的数量、领取数量
        coupon.setCount(coupon.getCount() - 1);
        coupon.setReceiveCount(coupon.getReceiveCount() == null ? 1 : coupon.getReceiveCount() + 1);
        Wrapper<SmsCoupon> updateWrapper = new UpdateWrapper<SmsCoupon>().lambda()
                .eq(SmsCoupon::getCount, coupon.getCount() + 1)
                .eq(SmsCoupon::getReceiveCount, coupon.getReceiveCount() - 1)
                .eq(SmsCoupon::getId, coupon.getId());
        smsCouponService.update(coupon, updateWrapper);
        return ResultWrapper.getSuccessBuilder().data("领取成功").build();
    }


    /**
     * 16位优惠码生成：时间戳后8位+4位随机数+用户id后4位
     */
    private String generateCouponCode(String memberId) {
        StringBuilder sb = new StringBuilder();
        Long currentTimeMillis = System.currentTimeMillis();
        String timeMillisStr = currentTimeMillis.toString();
        sb.append(timeMillisStr.substring(timeMillisStr.length() - 8));
        for (int i = 0; i < 4; i++) {
            sb.append(new Random().nextInt(10));
        }
        if (memberId.length() <= 4) {
            sb.append(String.format("%04d", memberId));
        } else {
            sb.append(memberId.substring(memberId.length() - 4));
        }
        return sb.toString();
    }

    @Override
    public ResultWrapper<List<SmsCouponByPcDTO>> listByProductCategoryIdsAndUser(List<Long> productCategoryIds) {
        if (CollectionUtils.isEmpty(productCategoryIds)) {
            return ResultWrapper.getSuccessBuilder().build();
        }
        //1.查询当前用户领取过的优惠券信息
        List<SmsCouponHistoryDTO> smsCouponHistoryDTOS = queryHistoryByUserAndUnUsed();
        List<Long> couponIdsByUser = smsCouponHistoryDTOS.stream()
                .map(SmsCouponHistoryDTO::getCouponId)
                .collect(Collectors.toList());
        //2.根据 1的结果 和 要查询的分类ids ，查询分类和优惠券的关联关系
        List<SmsCouponProductCategoryRelationDTO> smsCouponProductCategoryRelationDTOS = smsCouponProductCategoryRelationService
                .listByPcIdsAndCouponIds(productCategoryIds, couponIdsByUser);
        //获得优惠券ids
        List<Long> couponIds = getLongsForSmsCouponProductCategoryRelationDTOS(smsCouponProductCategoryRelationDTOS);
        //3.根据1的结果,查询关于详细优惠券的信息 (这个集合 <= 2的集合)
        List<SmsCouponDTO> allSmsCouponDTOS = smsCouponService
                .listByUseTypeAndCouponIds(UseTypeEnum.CLASSIFICATION.getCode(), couponIds);
        //4.聚合2 3结果
        //根据分类id做key 优惠券关联关系做value
        Map<Long, List<SmsCouponProductCategoryRelationDTO>> pcIdListMap = smsCouponProductCategoryRelationDTOS.stream()
                .collect(Collectors.groupingBy(obj -> obj.getProductCategoryId()));
        //根据优惠券id做key 优惠券信息做value(长度定为1,唯一)
        Map<Long, List<SmsCouponHaveDescriptionDTO>> couponIdListMap = allSmsCouponDTOS.stream()
                .map(obj -> {
                    SmsCouponHaveDescriptionDTO schdDTO = BeanUtils.copyBeanNoException(obj, SmsCouponHaveDescriptionDTO.class);
                    schdDTO.handleTexts();
                    return schdDTO;
                })
                .collect(Collectors.groupingBy(obj -> obj.getId()));
        List<SmsCouponByPcDTO> result = pcIdListMap.entrySet().stream().map(entry -> {
            //分类id
            Long pcId = entry.getKey();
            //优惠券列表
            List<SmsCouponProductCategoryRelationDTO> couponPCRList = entry.getValue();
            //分类名字
            String productCategoryName = couponPCRList.get(0).getProductCategoryName();
            List<SmsCouponHaveDescriptionDTO> schdDTO = couponPCRList.stream()
                    .filter(scpcrDTO -> couponIdListMap.containsKey(scpcrDTO.getCouponId()))
                    .map(scpcrDTO -> {
                        SmsCouponHaveDescriptionDTO smsCouponHaveDescriptionDTO = couponIdListMap.get(scpcrDTO.getCouponId()).get(0);
                        smsCouponHaveDescriptionDTO.setCouponProductCategoryRelationDTO(couponPCRList.get(0));
                        return smsCouponHaveDescriptionDTO;
                    })
                    .collect(Collectors.toList());
            SmsCouponByPcDTO scbsDTO = new SmsCouponByPcDTO();
            scbsDTO.setProductCategoryId(pcId);
            scbsDTO.setProductCategoryName(productCategoryName);
            scbsDTO.setCoupons(schdDTO);
            return scbsDTO;
        }).collect(Collectors.toList());
        return ResultWrapper.getSuccessBuilder().data(result).build();

    }


    /**
     * 根据优惠券ids,查询当前用户领取的,可用的 优惠券信息
     *
     * @param couponIds
     * @return
     */
    private List<SmsCouponHistoryDTO> queryHistoryByCouponIdsAndUserAndUnUse(List<Long> couponIds) {
        return queryHistoryByCouponIdsAndUser(couponIds, true, true);
    }

    /**
     * @param couponIds    优惠券ids
     * @param useCouponIds 是否需要使用couponIds作为条件
     * @param haveUseStats 是否使用可用性作为条件
     * @return
     */
    private List<SmsCouponHistoryDTO> queryHistoryByCouponIdsAndUser(List<Long> couponIds
            , boolean useCouponIds
            , boolean haveUseStats) {
        if (useCouponIds && CollectionUtils.isEmpty(couponIds)) {
            return new ArrayList<>();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = Optional.ofNullable(authentication).map(obj -> obj.getName()).orElse("");
        LambdaQueryWrapper<SmsCouponHistory> wrapper = new QueryWrapper<SmsCouponHistory>().lambda()
                .eq(SmsCouponHistory::getMemberId, userName);
        if (useCouponIds) {
            wrapper.in(SmsCouponHistory::getCouponId, couponIds);
        }
        if (haveUseStats) {
            wrapper.eq(SmsCouponHistory::getUseStatus, UseStatusEnum.UNUSED.getCode());
        }
        List<SmsCouponHistory> userCouponsBySkuIds = this.list(wrapper);
        List<SmsCouponHistoryDTO> collect = userCouponsBySkuIds.stream().map(obj -> BeanUtils.copyBeanNoException(obj, SmsCouponHistoryDTO.class)).collect(Collectors.toList());
        return collect;

    }

    /**
     * 当前用户的优惠券领取情况--可用的
     *
     * @return
     */
    private List<SmsCouponHistoryDTO> queryHistoryByUserAndUnUsed() {
        return queryHistoryByCouponIdsAndUser(null, false, true);

    }

    /**
     * 当前用户的优惠券领取情况--全部的
     *
     * @return
     */
    private List<SmsCouponHistoryDTO> queryAllHistoryByUser() {
        return queryHistoryByCouponIdsAndUser(null, false, false);

    }

    @Override
    public ResultWrapper<List<SmsCouponBySpuDTO>> listBySpuIds(List<Long> spuIds) {
        if (CollectionUtils.isEmpty(spuIds)) {
            return ResultWrapper.getSuccessBuilder().build();
        }

        List<SmsCouponProductRelationDTO> smsCouponProductRelationDTOS = smsCouponProductRelationService.listBySpuIds(spuIds);
        //
        List<Long> couponIds = smsCouponProductRelationDTOS.stream()
                .map(SmsCouponProductRelationDTO::getId).collect(Collectors.toList());
        List<SmsCouponDTO> smsCoupons = smsCouponService.listByUseTypeAndCouponIds(UseTypeEnum.PRODUCT.getCode(), couponIds);

        Map<Long, List<SmsCouponDTO>> couponMapById = smsCoupons.stream()
                .collect(Collectors.groupingBy(obj -> obj.getId()));

        Map<Long, List<SmsCouponProductRelationDTO>> collect = smsCouponProductRelationDTOS.stream().collect(Collectors.groupingBy(obj -> obj.getProductId()));
        List<SmsCouponBySpuDTO> result = collect.entrySet().stream()
                .map(entry -> {
                    Long key = entry.getKey();
                    List<SmsCouponProductRelationDTO> value = entry.getValue();
                    //从map中根据id 获取详细信息
                    List<SmsCouponDTO> coupons = value.stream()
                            .map(SmsCouponProductRelationDTO::getCouponId)
                            .filter(id -> couponMapById.containsKey(id))
                            .map(id -> couponMapById.get(id).get(0))
                            .collect(Collectors.toList());

                    SmsCouponBySpuDTO newDto = new SmsCouponBySpuDTO();
                    newDto.setSkuId(key);
                    newDto.setCoupons(coupons);
                    return newDto;
                }).collect(Collectors.toList());

        return ResultWrapper.getSuccessBuilder().data(result).build();
    }

    @Override
    public ResultWrapper<List<SmsCouponHaveDescriptionDTO>> listBySpuIdOrUser(Long spuId) {
        //1.查询此spu和优惠券的关系
        List<SmsCouponProductRelationDTO> smsCouponProductRelationDTOS = smsCouponProductRelationService.listBySpuId(spuId);

        List<Long> couponIds = smsCouponProductRelationDTOS.stream()
                .map(SmsCouponProductRelationDTO::getCouponId)
                .collect(Collectors.toList());
        List<SmsCouponHaveDescriptionDTO> result = getSchdDTOSByCouponIdsOrUser(couponIds,
                UseTypeEnum.PRODUCT.getCode());
        Map<Long, List<SmsCouponProductRelationDTO>> couponIdListMap = smsCouponProductRelationDTOS.stream()
                .collect(Collectors.groupingBy(obj -> obj.getCouponId()));
        result.stream().forEach(obj -> {
            obj.setCouponProductRelationDTO(couponIdListMap.get(obj.getId()).get(0));
            obj.handleTexts();
        });

        return ResultWrapper.getSuccessBuilder().data(result).build();
    }


    @Override
    public ResultWrapper<List<SmsCouponHaveDescriptionDTO>> listByProductCategoryIdOrUser(Long productCategoryId) {
        //1.查询商品分类id 和优惠券关系
        List<SmsCouponProductCategoryRelationDTO> smsCouponProductCategoryRelationDTOS = smsCouponProductCategoryRelationService
                .listByPcIds(Arrays.asList(productCategoryId));
        List<Long> couponIds = getLongsForSmsCouponProductCategoryRelationDTOS(smsCouponProductCategoryRelationDTOS);
        List<SmsCouponHaveDescriptionDTO> result = getSchdDTOSByCouponIdsOrUser(couponIds,
                UseTypeEnum.CLASSIFICATION.getCode());
        Map<Long, List<SmsCouponProductCategoryRelationDTO>> couponIdListMap = smsCouponProductCategoryRelationDTOS.stream()
                .collect(Collectors.groupingBy(obj -> obj.getCouponId()));
        //填充商品分类和优惠券的关系
        result.stream().forEach(obj -> {
            obj.setCouponProductCategoryRelationDTO(couponIdListMap.get(obj.getId()).get(0));
            obj.handleTexts();
        });
        return ResultWrapper.getSuccessBuilder().data(result).build();
    }

    @Override
    public ResultWrapper<List<SmsCouponHaveDescriptionDTO>> listBySpuIdOrProductCategoryIdOrUser(Long spuId, Long productCategoryId) {
        ResultWrapper<List<SmsCouponHaveDescriptionDTO>> listResultWrapperForSpuId = this.listBySpuIdOrUser(spuId);
        ResultWrapper<List<SmsCouponHaveDescriptionDTO>> listResultWrapperForPcId = this.listByProductCategoryIdOrUser(productCategoryId);
        List<SmsCouponHaveDescriptionDTO> dataForSpuId = listResultWrapperForSpuId.getData();
        List<SmsCouponHaveDescriptionDTO> dataForPcId = listResultWrapperForPcId.getData();
        List<SmsCouponHaveDescriptionDTO> result = new ArrayList<>();
        result.addAll(dataForSpuId);
        result.addAll(dataForPcId);
        return ResultWrapper.getSuccessBuilder().data(result).build();
    }

    /**
     * 根据入参 获取优惠券的ids
     *
     * @param smsCouponProductRelationDTOS
     * @return
     */
    private List<Long> getLongsForSmsCouponProductCategoryRelationDTOS(List<SmsCouponProductCategoryRelationDTO> smsCouponProductRelationDTOS) {
        return smsCouponProductRelationDTOS.stream()
                .map(SmsCouponProductCategoryRelationDTO::getCouponId)
                .collect(Collectors.toList());
    }

    /**
     * 根据优惠券ids 和 用户的领取记录进行联合处理
     * 展示主体是ids对应的优惠券信息
     *
     * @param couponIds
     * @return
     */
    private List<SmsCouponHaveDescriptionDTO> getSchdDTOSByCouponIdsOrUser(List<Long> couponIds, int useType) {
        if (CollectionUtils.isEmpty(couponIds)) {
            return new ArrayList<>();
        }
        //1.根据 优惠券ids 查询出来全部优惠信息
        List<SmsCouponDTO> smsCoupons = smsCouponService
                .listByUseTypeAndCouponIds(useType, couponIds);
        //2.查询出来当前用户的优惠券
        List<SmsCouponHistoryDTO> userCouponsBySkuIdsAndUser = queryHistoryByCouponIdsAndUserAndUnUse(couponIds);
        Map<Long, List<SmsCouponHistoryDTO>> userCouponsBySkuIdsMap = userCouponsBySkuIdsAndUser.stream().collect(Collectors.groupingBy(obj -> obj.getCouponId()));
        //对2 3的结果进行聚合,展示给前端,影响展示
        return smsCoupons.stream()
                .map(obj -> {
                    SmsCouponHaveDescriptionDTO schdDTO = BeanUtils.copyBeanNoException(obj, SmsCouponHaveDescriptionDTO.class);
                    if (userCouponsBySkuIdsMap.containsKey(obj.getId())) {
                        //当前用户已经领取过了
                        schdDTO.setIsReceive(1);
                    } else {
                        //当前用户没有领取过
                        schdDTO.setIsReceive(0);
                    }
                    return schdDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public ResultWrapper<List<SmsCouponHaveDescriptionDTO>> listByUser() {
        List<SmsCouponHistoryDTO> smsCouponHistoryDTOS = queryAllHistoryByUser();
        List<Long> couponIds = smsCouponHistoryDTOS.stream()
                .map(obj -> obj.getCouponId())
                .collect(Collectors.toList());
        List<SmsCouponDTO> smsCouponDTOS = smsCouponService.listByCouponIds(couponIds);
        List<SmsCouponHaveDescriptionDTO> result = smsCouponDTOS.stream().map(obj -> {
            SmsCouponHaveDescriptionDTO schdDTO = BeanUtils.copyBeanNoException(obj, SmsCouponHaveDescriptionDTO.class);
            schdDTO.handleTexts();
            return schdDTO;
        }).collect(Collectors.toList());
        return ResultWrapper.getSuccessBuilder().data(result).build();
    }

    @Override
    public void useCoupons(List<SmsCouponDTO> couponDTOS, String orderNumber) {
        if (CollectionUtils.isEmpty(couponDTOS)) {
            return;
        }
        //当前登录人
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = Optional.ofNullable(authentication).map(obj -> obj.getName()).orElse("");

        SmsCouponHistory couponHistory = new SmsCouponHistory();
        couponHistory.setUseStatus(1);
        couponHistory.setOrderSn(orderNumber);
        couponHistory.setOrderId(orderNumber);
        couponHistory.setUseTime(System.currentTimeMillis());
        List<Long> couponIds = couponDTOS.stream().map(obj -> obj.getId()).collect(Collectors.toList());
        LambdaUpdateWrapper<SmsCouponHistory> updateWrapper = new UpdateWrapper<SmsCouponHistory>().lambda();
        updateWrapper.in(SmsCouponHistory::getCouponId, couponIds)
                .eq(SmsCouponHistory::getMemberNickname, userName)
                .eq(SmsCouponHistory::getUseStatus, UseStatusEnum.UNUSED.getCode());
        int n = smsCouponHistoryDao.update(couponHistory, updateWrapper);
        if (n != couponDTOS.size()) {
            throw new BusinessException(ErrorCodeEnum.SMS0000100);
        }
    }

    @Override
    public void restoreCouponsByOrderNumber(String orderNumber) {
        LambdaUpdateWrapper<SmsCouponHistory> updateWrapper = new UpdateWrapper<SmsCouponHistory>().lambda();

        updateWrapper.eq(SmsCouponHistory::getOrderSn, orderNumber)
                .eq(SmsCouponHistory::getUseStatus, UseStatusEnum.USED.getCode());

        //不更新orderNumber,只更新使用标志位,上面查询的时候限制住了
        SmsCouponHistory couponHistory = new SmsCouponHistory();
        couponHistory.setUseStatus(UseStatusEnum.UNUSED.getCode());

        this.update(couponHistory, updateWrapper);

    }
}
