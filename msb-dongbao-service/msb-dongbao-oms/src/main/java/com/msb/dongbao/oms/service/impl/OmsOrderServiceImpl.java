package com.msb.dongbao.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.cart.model.dto.RemoveItem;
import com.msb.dongbao.cart.service.IShoppingCartService;
import com.msb.dongbao.common.base.dto.PageResult;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.base.enums.ErrorCodeEnum;
import com.msb.dongbao.common.base.exception.BusinessException;
import com.msb.dongbao.common.util.BeanUtils;
import com.msb.dongbao.oms.db.dao.OmsOrderDao;
import com.msb.dongbao.oms.model.dto.*;
import com.msb.dongbao.oms.model.entity.OmsOrder;
import com.msb.dongbao.oms.model.entity.OmsOrderItem;
import com.msb.dongbao.oms.model.enums.OderTypeEnum;
import com.msb.dongbao.oms.model.enums.OrderStatusEnum;
import com.msb.dongbao.oms.service.IOmsCancelOrderService;
import com.msb.dongbao.oms.service.IOmsOrderItemService;
import com.msb.dongbao.oms.service.IOmsOrderService;
import com.msb.dongbao.pms.model.dto.*;
import com.msb.dongbao.pms.model.entity.ProductFullReduction;
import com.msb.dongbao.pms.service.IProductFullReductionService;
import com.msb.dongbao.pms.service.ISkuStockService;
import com.msb.dongbao.sms.model.dto.SmsCouponDTO;
import com.msb.dongbao.sms.model.enums.UseTypeEnum;
import com.msb.dongbao.sms.service.ISmsCouponHistoryService;
import com.msb.dongbao.sms.service.ISmsCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * ?????????????????????
 * </p>
 *
 * @author ????????? ?? ???????????????
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company ????????????????????????????????????????????? (http://www.mashibing.com/)
 * @copyright ????????????????????????????????????????????? ?? ???????????????
 */
@Service
@Slf4j
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderDao, OmsOrder> implements IOmsOrderService {

    private static AtomicLong orderNum = new AtomicLong();

    public static final BigDecimal ZERO_BIG = new BigDecimal("0");

    @Autowired
    private IOmsOrderItemService omsOrderItemService;

    @Autowired
    private OmsOrderDao omsOrderDao;

    @Autowired
    private ISkuStockService skuStockService;

    @Autowired
    private IOmsCancelOrderService omsCancelOrderService;

    @Autowired
    private ISmsCouponHistoryService smsCouponHistoryService;

    @Autowired
    private ISmsCouponService smsCouponService;

    @Autowired
    private IShoppingCartService shoppingCartService;


    @Autowired
    IProductFullReductionService productFullReductionService;

    @Override
    public ResultWrapper<PageResult<OmsOrderAndItemsDTO>> pageListOmsOrder(OmsOrderPageDTO pageDTO) {
        PageResult<OmsOrderAndItemsDTO> pageResult = new PageResult<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String createBy = Optional.ofNullable(authentication).map(obj -> obj.getName()).orElse("");

        Wrapper<OmsOrder> wrapper = new QueryWrapper<OmsOrder>().lambda()
                .eq(OmsOrder::getCreateBy, createBy)
                .orderByDesc(OmsOrder::getGmtCreate);
        Page<OmsOrder> page = new Page();
        page.setCurrent(pageDTO.getPageIndex());
        page.setSize(pageDTO.getLength());
        this.page(page, wrapper);
        BeanCopier beanCopier = BeanCopier.create(OmsOrder.class, OmsOrderAndItemsDTO.class, false);
        List<OmsOrderAndItemsDTO> orderList = new ArrayList<>();
        List<String> orderNumbers = new ArrayList<>();

        page.getRecords().stream().forEach(model -> {
            OmsOrderAndItemsDTO vo = new OmsOrderAndItemsDTO();
            beanCopier.copy(model, vo, null);
            orderList.add(vo);
            orderNumbers.add(vo.getOrderNumber());
        });

        //??????????????????
        if (!CollectionUtils.isEmpty(orderNumbers)) {
            Wrapper<OmsOrderItem> orderItemQueryWrapper = new QueryWrapper<OmsOrderItem>().lambda()
                    .in(OmsOrderItem::getOrderNumber, orderNumbers);
            List<OmsOrderItem> orderItemList = omsOrderItemService.list(orderItemQueryWrapper);
            Map<String, List<OmsOrderItemDTO>> orderItemVoMapByOrderNum = orderItemList.stream().map(entity ->
                    BeanUtils.copyBeanNoException(entity, OmsOrderItemDTO.class)
            ).collect(Collectors.groupingBy(OmsOrderItemDTO::getOrderNumber));
            List empty = new ArrayList();
            //?????????????????????vo???
            orderList.stream().forEach(vo -> {
                List items = Optional.ofNullable(orderItemVoMapByOrderNum.get(vo.getOrderNumber())).orElse(empty);
                vo.setItems(items);
            });
        }
        pageResult.setTotalElements(page.getTotal());
        pageResult.setContent(orderList);
        return ResultWrapper.getSuccessBuilder().data(pageResult).build();
    }


    @Override
    public OmsOrderDTO orderDetail(String orderNumber) {
        Wrapper<OmsOrder> wrapper = new QueryWrapper<OmsOrder>().lambda().eq(OmsOrder::getOrderNumber, orderNumber);
        OmsOrder one = this.getOne(wrapper);
        OmsOrderDTO dto = BeanUtils.copyBeanNoException(one, OmsOrderDTO.class);
        return dto;
    }

    @Override
    public OmsOrderAndItemsDTO orderAndItemsDetail(String orderNumber) {
        Wrapper<OmsOrder> wrapper = new QueryWrapper<OmsOrder>().lambda().eq(OmsOrder::getOrderNumber, orderNumber);
        OmsOrder one = this.getOne(wrapper);
        OmsOrderAndItemsDTO vo;
        vo = BeanUtils.copyBeanNoException(one, OmsOrderAndItemsDTO.class);
        //??????????????????
        Wrapper<OmsOrderItem> orderItemQueryWrapper = new QueryWrapper<OmsOrderItem>().lambda()
                .eq(OmsOrderItem::getOrderNumber, orderNumber);
        List<OmsOrderItem> orderItems = omsOrderItemService.list(orderItemQueryWrapper);
        List<OmsOrderItemDTO> OrderItemVOList = orderItems.stream()
                .map(dto -> BeanUtils.copyBeanNoException(dto, OmsOrderItemDTO.class))
                .collect(Collectors.toList());
        vo.setItems(OrderItemVOList);
        return vo;
    }

    @Override
    public List<OmsOrder> getUnpaidOrders() {
        Wrapper<OmsOrder> wrapper = new QueryWrapper<OmsOrder>().lambda().
                eq(OmsOrder::getOrderStatus, OrderStatusEnum.WAIT_PAY);
        return omsOrderDao.selectList(wrapper);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultWrapper<OmsOrder> generateOrder(OrderParamNewDTO orderParamNewDTO) {
        if (CollectionUtils.isEmpty(orderParamNewDTO.getItems())) {
            throw new BusinessException(ErrorCodeEnum.OMS0000111);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = Optional.ofNullable(authentication).map(obj -> obj.getName()).orElse("");
        List<OmsOrderItem> orderItemList;
        //?????????????????????????????????????????????
        List<ProductItemParam> cartPromotionItemList = orderParamNewDTO.getItems();
        //??????????????????ids
        List<Long> useCouponIds = orderParamNewDTO.getUseCouponIds();

        //??????id ??? ???????????????
        Map<Long, Integer> productNumMap = cartPromotionItemList.stream()
                .collect(Collectors.toMap(ProductItemParam::getProductId, ProductItemParam::getQuantity));
        //????????????skuId
        List<PreCutStockDTO> preCutStockVOList = cartPromotionItemList.stream()
                .map(obj -> {
                    PreCutStockDTO preCutStockVO = new PreCutStockDTO();
                    preCutStockVO.setId(obj.getProductId());
                    preCutStockVO.setPreCutNum(obj.getQuantity());
                    return preCutStockVO;
                }).collect(Collectors.toList());
        //???????????? ????????????????????????????????????
        CutStockDTO cutStockDTO = skuStockService.preCutStock(preCutStockVOList);
        if (!cutStockDTO.isFlag()) {
            throw new BusinessException(ErrorCodeEnum.OMS0000010);
        }
        //????????????????????????????????????
        List<SkuStockDTO> skuStockDTOS = cutStockDTO.getSuccessList();
        // ??????????????????????????????
        List<RemoveItem> removeCartItems = new ArrayList<>(skuStockDTOS.size());
        orderItemList = skuStockDTOS.stream().map(obj -> {
            //????????????????????????
            OmsOrderItem orderItem = new OmsOrderItem();
            orderItem.setProductId(obj.getId());
            orderItem.setProductName(obj.getTitle());
            orderItem.setProductSpuId(obj.getRelProductId());
            orderItem.setProductPic(obj.getImage());
            orderItem.setProductAttr(obj.getSpec());
            //????????????
            orderItem.setProductNormalPrice(obj.getPrice());
            //????????????(??????????????????)
            orderItem.setProductPrice(obj.getDiscountPrice());
            orderItem.setProductCategoryId(obj.getRelCategory3Id());
            Integer cutNum = productNumMap.get(obj.getId());
            orderItem.setProductQuantity(cutNum);
            orderItem.setGmtCreate(System.currentTimeMillis());
            orderItem.setCreateBy(userName);
            // ????????????????????????
            RemoveItem updateItem = new RemoveItem();
            updateItem.setSkuNo(obj.getSkuNo());
            updateItem.setNumber(cutNum);
            removeCartItems.add(updateItem);
            return orderItem;
        }).collect(Collectors.toList());


        OmsOrder order = new OmsOrder();
        //???????????????0->????????????1->????????????2->??????
        order.setPayType(orderParamNewDTO.getPayType());
        //???????????????0->PC?????????1->app??????  ?????????PC??????
        order.setSourceType(0);
        //??????????????? ?????????????????????id

        String orderNumber = generateOrderSn(order);
        order.setOrderNumber(orderNumber);


        order.setCreateBy(userName);

        //????????????  ??????????????????
        List<SmsCouponDTO> usedCouponDTOS = handleAmount(order, orderItemList, useCouponIds, skuStockDTOS);


        order.setTitle("??????????????????");
        //???????????????????????????????????????
        order.setUserId(userName);
        order.setGmtCreate(System.currentTimeMillis());

        //????????????
        order.setOrderStatus(OrderStatusEnum.WAIT_PAY.getCode());
        //???????????????0->NORMAL_ORDER???1->????????????
        order.setOrderType(OderTypeEnum.NORMAL_ORDER.getCode());
        order.setReceiverName(orderParamNewDTO.getReceiverName());
        order.setReceiverPhone(orderParamNewDTO.getReceiverPhone());
        order.setReceiverDetailAddress(orderParamNewDTO.getReceiverDetailAddress());
        //0:????????????1:?????????
        order.setConfirmStatus(0);
        order.setDeleteStatus(0);
        order.setParentOrderNumber("");
        order.setMerchantId("1");
        //???????????????????????? ??????7???.???????????????????????????
        order.setAutoConfirmDay(7);

        //??????order??????order_item???
        omsOrderDao.insert(order);

        for (OmsOrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderNumber(order.getOrderNumber());
        }
        //??????????????????
        omsOrderItemService.saveBatch(orderItemList);
        //????????????????????????????????????
        smsCouponHistoryService.useCoupons(usedCouponDTOS, orderNumber);
        //??????????????????????????????
        sendDelayMessageCancelOrder(order);
        // ?????????????????????
        shoppingCartService.removeCartItems(removeCartItems);
        return ResultWrapper.getSuccessBuilder().data(order).build();
    }

    /**
     * ????????????
     *
     * @param order         ????????????,????????????
     * @param orderItemList ???????????? ????????????
     * @param useCouponIds  ??????????????????
     * @param skuStockDTOS  ??????????????????
     * @return
     */
    private List<SmsCouponDTO> handleAmount(OmsOrder order,
                                            List<OmsOrderItem> orderItemList,
                                            List<Long> useCouponIds,
                                            List<SkuStockDTO> skuStockDTOS) {

        //??????????????????????????????spuId set
        Set<String> fullReduceIds = skuStockDTOS.stream()
                .filter(obj -> obj.getReductionId() != null)
                .map(obj -> obj.getReductionId().toString())
                .collect(Collectors.toSet());
        //????????????????????????
        BigDecimal fullReduceAllAmount = new BigDecimal("0");
        for (String fullReduceId : fullReduceIds) {
            ProductFullReduction detail = productFullReductionService.detail(new Long(fullReduceId));
            fullReduceAllAmount = fullReduceAllAmount.add(detail.getReducePrice());
        }

        //??????????????????????????????
        List<SmsCouponDTO> usedCouponDTOS = handleOrderItemRealAmount(orderItemList, useCouponIds);
        //??????order_item??????????????? ??? ????????????  ???????????? ??????????????????????????????????????????
        //?????????????????????
        BigDecimal totalAmount = ZERO_BIG;
        for (OmsOrderItem item : orderItemList) {
            totalAmount = totalAmount.add(item.getProductNormalPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        // ??????1=???????????????-????????????
        BigDecimal payAmount = totalAmount.subtract(fullReduceAllAmount);
        //?????????????????? = ??????1-???????????????
        payAmount = calcTotalAmountByCoupon(payAmount, usedCouponDTOS);

        //?????????????????????
        order.setTotalAmount(totalAmount);
        order.setPayAmount(payAmount);

        return usedCouponDTOS;
    }

    @Override
    public boolean orderPaySuccess(String orderNumber) {
        if (StringUtils.isEmpty(orderNumber)) {
            throw new BusinessException(ErrorCodeEnum.OMS0000111);
        }

        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderStatus(OrderStatusEnum.WAIT_SHIP.getCode());
        Wrapper<OmsOrder> updateWrapper = new UpdateWrapper<OmsOrder>()
                .lambda()
                .eq(OmsOrder::getOrderNumber, orderNumber)
                .eq(OmsOrder::getOrderStatus, OrderStatusEnum.WAIT_PAY.getCode());
        int updateCount = omsOrderDao.update(omsOrder, updateWrapper);
        if (updateCount != 1) {
            log.info("??????{},????????????:{}??????", orderNumber, omsOrder.getOrderStatus());
            throw new BusinessException(ErrorCodeEnum.OMS0000001);
        }
        return updateCount == 1;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelTimeOutOrder(String orderNumber) {
        if (StringUtils.isEmpty(orderNumber)) {
            throw new BusinessException(ErrorCodeEnum.OMS0000111);
        }
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderStatus(OrderStatusEnum.TIMEOUT_CANCEL.getCode());
        boolean success = cancelOrderStatusAndSku(orderNumber, omsOrder);
        smsCouponHistoryService.restoreCouponsByOrderNumber(orderNumber);

        return success;
    }

    private boolean cancelOrderStatusAndSku(String orderNumber, OmsOrder omsOrder) {
        Wrapper<OmsOrder> updateWrapper = new UpdateWrapper<OmsOrder>()
                .lambda()
                .eq(OmsOrder::getOrderNumber, orderNumber)
                .eq(OmsOrder::getOrderStatus, OrderStatusEnum.WAIT_PAY.getCode());
        int updateCount = omsOrderDao.update(omsOrder, updateWrapper);
        if (updateCount != 1) {
            log.info("??????{},????????????", orderNumber);
            throw new BusinessException(ErrorCodeEnum.OMS0000011);
        }
        log.info("??????{}????????????", orderNumber);
        //?????????????????? ????????????
        List<RestoreStockDTO> restoreStockVOS = getRestoreStockVOS(orderNumber);
        Boolean restoreFlag = skuStockService.restoreStock(restoreStockVOS);
        log.info("??????:{},????????????????????????", orderNumber);
        return updateCount == 1 && restoreFlag;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelOrder(String orderNumber) {
        if (StringUtils.isEmpty(orderNumber)) {
            throw new BusinessException(ErrorCodeEnum.OMS0000111);
        }
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        boolean success = cancelOrderStatusAndSku(orderNumber, omsOrder);
        smsCouponHistoryService.restoreCouponsByOrderNumber(orderNumber);
        return success;
    }

    /**
     * ???????????????????????? ????????????
     *
     * @param orderNumber
     * @return
     */
    private List<RestoreStockDTO> getRestoreStockVOS(String orderNumber) {
        Wrapper<OmsOrderItem> wrapper = new QueryWrapper<OmsOrderItem>().lambda()
                .eq(OmsOrderItem::getOrderNumber, orderNumber);
        return omsOrderItemService.list(wrapper).stream().map(dto -> {
            RestoreStockDTO restoreStockVO = new RestoreStockDTO();
            restoreStockVO.setId(dto.getProductId());
            restoreStockVO.setRestoreNum(dto.getProductQuantity());
            return restoreStockVO;
        }).collect(Collectors.toList());
    }

    /**
     * ????????????????????????
     *
     * @param
     */
    public void sendDelayMessageCancelOrder(OmsOrder order) {
        omsCancelOrderService.addCancelOrder(order.getOrderNumber(), order.getGmtCreate());

    }


    /**
     * ??????????????????????????????
     *
     * @param orderItemList ????????????
     * @param couponIds     ??????????????????ids
     * @return ???????????????????????????
     */
    private List<SmsCouponDTO> handleOrderItemRealAmount(List<OmsOrderItem> orderItemList, List<Long> couponIds) {
        if (CollectionUtils.isEmpty(couponIds)) {
            //???????????????
            orderItemList.stream().forEach(entity -> {
                //??????????????????
                entity.setProductSettlementPrice(entity.getProductPrice());
            });
            return new ArrayList<>();
        }
        //??????????????? ?????????????????? ??????
        List<SmsCouponDTO> coupons = smsCouponService.listByCouponIds(couponIds);
        //key:????????????id value:??????????????? todo ???????????????<????????????>??????????????????
        Map<Long, SmsCouponDTO> pcIdCouponMap = coupons.stream()
                .filter(obj -> obj.getUseType() - UseTypeEnum.CLASSIFICATION.getCode() == 0)
                .collect(Collectors.toMap(
                        obj -> obj.getCouponProductCategoryRelationDTO().getProductCategoryId(),
                        Function.identity()
                ));
        //key:????????????id  value:????????????
        Map<Long, List<OmsOrderItem>> pcIdListMap = orderItemList.stream()
                .collect(Collectors.groupingBy(obj -> obj.getProductCategoryId()));
        //??????????????????????????????
        List<SmsCouponDTO> couponDTOs = new ArrayList<>();
        pcIdListMap.entrySet().stream().forEach(obj -> {
            //???????????????????????????
            //????????????
            Long key = obj.getKey();
            //??????????????????
            List<OmsOrderItem> items = obj.getValue();
            //???????????????????????????
            BigDecimal pcIdPriceSum = items.stream()
                    .map(item -> item.getProductNormalPrice().multiply(new BigDecimal(item.getProductQuantity())))
                    .reduce(ZERO_BIG,
                            (a, b) -> a.add(b));
            //???????????????????????? ????????????????????????1????????????
            SmsCouponDTO coupon = pcIdCouponMap.get(key);
            //??????????????????????????????
            if (pcIdPriceSum.compareTo(coupon.getMinPoint()) >= 0) {
                couponDTOs.add(coupon);
                //?????????????????????
                items.stream().forEach(item -> {
                    //?????????????????????   ????????????/???????????? * ???????????????
                    BigDecimal price = item.getProductPrice().multiply(coupon.getAmount()).divide(pcIdPriceSum, 3, RoundingMode.HALF_EVEN);
                    //?????? ????????????-?????????????????????
                    BigDecimal settlementPrice = item.getProductPrice().subtract(price);
                    //??????????????????
                    item.setProductSettlementPrice(settlementPrice);
                });
            }
        });
        return couponDTOs;
    }


    /**
     * ???????????????????????????????????????????????????
     *
     * @param totalAmount ???????????????
     * @param couponDTOS  ????????????????????????
     * @return
     */
    private BigDecimal calcTotalAmountByCoupon(BigDecimal totalAmount, List<SmsCouponDTO> couponDTOS) {
        //????????????????????????
        for (SmsCouponDTO couponDTO : couponDTOS) {
            totalAmount = totalAmount.subtract(couponDTO.getAmount());
        }
        int flag = totalAmount.compareTo(ZERO_BIG);
        if (flag < 0) {
            throw new BusinessException(ErrorCodeEnum.OMS0001010);
        }
        if (flag == 0) {
            totalAmount = new BigDecimal("0.01");
        }
        return totalAmount;
    }


    /**
     * ????????????????????????
     * ???????????????????????????????????????
     */
    private BigDecimal calcPayAmount(OmsOrder order) {
        //?????????
        BigDecimal payAmount = order.getTotalAmount();
        return payAmount;
    }

    /**
     * ??????28???????????????:13????????????+2???????????????+2???????????????+11???????????????
     */
    private String generateOrderSn(OmsOrder order) {
        StringBuilder sb = new StringBuilder();
        Long time = System.currentTimeMillis();
        Long increment = orderNum.incrementAndGet();
        sb.append(time);
        sb.append(String.format("%02d", order.getSourceType()));
        sb.append(String.format("%02d", order.getPayType()));
        sb.append(String.format("%06d", increment));
        return sb.toString();
    }

    @Override
    public boolean orderRefundPre(String orderNumber) {
        if (StringUtils.isEmpty(orderNumber)) {
            throw new BusinessException(ErrorCodeEnum.OMS0000111);
        }
        Wrapper<OmsOrder> wrapper = new UpdateWrapper<OmsOrder>().lambda()
                .eq(OmsOrder::getOrderNumber, orderNumber)
                .eq(OmsOrder::getOrderStatus, OrderStatusEnum.WAIT_SHIP.getCode());
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderStatus(OrderStatusEnum.REFUNDING.getCode());
        boolean success = omsOrderDao.update(omsOrder, wrapper) == 1;
        if (!success) {
            throw new BusinessException(ErrorCodeEnum.OMS0000100);
        }
        return true;

    }

    @Override
    public boolean orderRefundFailEnd(String orderNumber) {
        if (StringUtils.isEmpty(orderNumber)) {
            throw new BusinessException(ErrorCodeEnum.OMS0000111);
        }
        log.info("????????????:????????????{}", orderNumber);
        Wrapper<OmsOrder> wrapper = new UpdateWrapper<OmsOrder>().lambda()
                .eq(OmsOrder::getOrderNumber, orderNumber)
                .eq(OmsOrder::getOrderStatus, OrderStatusEnum.REFUNDING.getCode());
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderStatus(OrderStatusEnum.WAIT_SHIP.getCode());
        boolean success = omsOrderDao.update(omsOrder, wrapper) == 1;
        if (!success) {
            throw new BusinessException(ErrorCodeEnum.OMS0000101);
        }
        return true;
    }

    @Override
    public boolean orderRefundSuccessEnd(String orderNumber) {
        if (StringUtils.isEmpty(orderNumber)) {
            throw new BusinessException(ErrorCodeEnum.OMS0000111);
        }
        log.info("????????????:????????????{}", orderNumber);
        Wrapper<OmsOrder> wrapper = new UpdateWrapper<OmsOrder>().lambda()
                .eq(OmsOrder::getOrderNumber, orderNumber)
                .eq(OmsOrder::getOrderStatus, OrderStatusEnum.REFUNDING.getCode());
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderStatus(OrderStatusEnum.REFUND_COMPLETED.getCode());
        boolean success = omsOrderDao.update(omsOrder, wrapper) == 1;
        //?????????????????? ????????????
        if (success) {
            List<RestoreStockDTO> restoreStockVOS = getRestoreStockVOS(orderNumber);
            skuStockService.restoreStock(restoreStockVOS);
        }
        return success;
    }
}
