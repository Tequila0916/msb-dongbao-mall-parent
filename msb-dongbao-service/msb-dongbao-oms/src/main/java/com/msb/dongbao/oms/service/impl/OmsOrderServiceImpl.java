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
 * 订单业务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
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

        //获取商品详情
        if (!CollectionUtils.isEmpty(orderNumbers)) {
            Wrapper<OmsOrderItem> orderItemQueryWrapper = new QueryWrapper<OmsOrderItem>().lambda()
                    .in(OmsOrderItem::getOrderNumber, orderNumbers);
            List<OmsOrderItem> orderItemList = omsOrderItemService.list(orderItemQueryWrapper);
            Map<String, List<OmsOrderItemDTO>> orderItemVoMapByOrderNum = orderItemList.stream().map(entity ->
                    BeanUtils.copyBeanNoException(entity, OmsOrderItemDTO.class)
            ).collect(Collectors.groupingBy(OmsOrderItemDTO::getOrderNumber));
            List empty = new ArrayList();
            //设置信息到订单vo中
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
        //获取商品详情
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
        //获取选中的商品信息和优惠券信息
        List<ProductItemParam> cartPromotionItemList = orderParamNewDTO.getItems();
        //使用的优惠券ids
        List<Long> useCouponIds = orderParamNewDTO.getUseCouponIds();

        //商品id 和 数量的映射
        Map<Long, Integer> productNumMap = cartPromotionItemList.stream()
                .collect(Collectors.toMap(ProductItemParam::getProductId, ProductItemParam::getQuantity));
        //收集所有skuId
        List<PreCutStockDTO> preCutStockVOList = cartPromotionItemList.stream()
                .map(obj -> {
                    PreCutStockDTO preCutStockVO = new PreCutStockDTO();
                    preCutStockVO.setId(obj.getProductId());
                    preCutStockVO.setPreCutNum(obj.getQuantity());
                    return preCutStockVO;
                }).collect(Collectors.toList());
        //订单系统 预减并且返回优惠后的金额
        CutStockDTO cutStockDTO = skuStockService.preCutStock(preCutStockVOList);
        if (!cutStockDTO.isFlag()) {
            throw new BusinessException(ErrorCodeEnum.OMS0000010);
        }
        //获得减库存成功的商品列表
        List<SkuStockDTO> skuStockDTOS = cutStockDTO.getSuccessList();
        // 需要移除的购物车数据
        List<RemoveItem> removeCartItems = new ArrayList<>(skuStockDTOS.size());
        orderItemList = skuStockDTOS.stream().map(obj -> {
            //生成下单商品信息
            OmsOrderItem orderItem = new OmsOrderItem();
            orderItem.setProductId(obj.getId());
            orderItem.setProductName(obj.getTitle());
            orderItem.setProductSpuId(obj.getRelProductId());
            orderItem.setProductPic(obj.getImage());
            orderItem.setProductAttr(obj.getSpec());
            //正常价格
            orderItem.setProductNormalPrice(obj.getPrice());
            //销售价格(满减优惠后的)
            orderItem.setProductPrice(obj.getDiscountPrice());
            orderItem.setProductCategoryId(obj.getRelCategory3Id());
            Integer cutNum = productNumMap.get(obj.getId());
            orderItem.setProductQuantity(cutNum);
            orderItem.setGmtCreate(System.currentTimeMillis());
            orderItem.setCreateBy(userName);
            // 生成移除购车数据
            RemoveItem updateItem = new RemoveItem();
            updateItem.setSkuNo(obj.getSkuNo());
            updateItem.setNumber(cutNum);
            removeCartItems.add(updateItem);
            return orderItem;
        }).collect(Collectors.toList());


        OmsOrder order = new OmsOrder();
        //支付方式：0->未支付；1->支付宝；2->微信
        order.setPayType(orderParamNewDTO.getPayType());
        //订单来源：0->PC订单；1->app订单  默认为PC订单
        order.setSourceType(0);
        //生成订单号 后期采用分布式id

        String orderNumber = generateOrderSn(order);
        order.setOrderNumber(orderNumber);


        order.setCreateBy(userName);

        //计算金额  使用的优惠券
        List<SmsCouponDTO> usedCouponDTOS = handleAmount(order, orderItemList, useCouponIds, skuStockDTOS);


        order.setTitle("我是订单标题");
        //转化为订单信息并插入数据库
        order.setUserId(userName);
        order.setGmtCreate(System.currentTimeMillis());

        //订单状态
        order.setOrderStatus(OrderStatusEnum.WAIT_PAY.getCode());
        //订单类型：0->NORMAL_ORDER；1->秒杀订单
        order.setOrderType(OderTypeEnum.NORMAL_ORDER.getCode());
        order.setReceiverName(orderParamNewDTO.getReceiverName());
        order.setReceiverPhone(orderParamNewDTO.getReceiverPhone());
        order.setReceiverDetailAddress(orderParamNewDTO.getReceiverDetailAddress());
        //0:未确认；1:已确认
        order.setConfirmStatus(0);
        order.setDeleteStatus(0);
        order.setParentOrderNumber("");
        order.setMerchantId("1");
        //设置自动收货天数 默认7天.后期可以维护一张表
        order.setAutoConfirmDay(7);

        //插入order表和order_item表
        omsOrderDao.insert(order);

        for (OmsOrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderNumber(order.getOrderNumber());
        }
        //保存订单详情
        omsOrderItemService.saveBatch(orderItemList);
        //修改用户的优惠券相关信息
        smsCouponHistoryService.useCoupons(usedCouponDTOS, orderNumber);
        //发送延迟消息取消订单
        sendDelayMessageCancelOrder(order);
        // 移除购物车数据
        shoppingCartService.removeCartItems(removeCartItems);
        return ResultWrapper.getSuccessBuilder().data(order).build();
    }

    /**
     * 处理金额
     *
     * @param order         订单实体,设置金额
     * @param orderItemList 商品详情 计算金额
     * @param useCouponIds  使用的优惠券
     * @param skuStockDTOS  商品满减优惠
     * @return
     */
    private List<SmsCouponDTO> handleAmount(OmsOrder order,
                                            List<OmsOrderItem> orderItemList,
                                            List<Long> useCouponIds,
                                            List<SkuStockDTO> skuStockDTOS) {

        //使用的满减优惠商品的spuId set
        Set<String> fullReduceIds = skuStockDTOS.stream()
                .filter(obj -> obj.getReductionId() != null)
                .map(obj -> obj.getReductionId().toString())
                .collect(Collectors.toSet());
        //商品的满减总优惠
        BigDecimal fullReduceAllAmount = new BigDecimal("0");
        for (String fullReduceId : fullReduceIds) {
            ProductFullReduction detail = productFullReductionService.detail(new Long(fullReduceId));
            fullReduceAllAmount = fullReduceAllAmount.add(detail.getReducePrice());
        }

        //处理优惠券和商品详情
        List<SmsCouponDTO> usedCouponDTOS = handleOrderItemRealAmount(orderItemList, useCouponIds);
        //计算order_item的实付金额 和 销售金额  结算金额 返回的是参与计算的优惠券信息
        //计算订单总金额
        BigDecimal totalAmount = ZERO_BIG;
        for (OmsOrderItem item : orderItemList) {
            totalAmount = totalAmount.add(item.getProductNormalPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        // 金额1=订单总金额-满减优惠
        BigDecimal payAmount = totalAmount.subtract(fullReduceAllAmount);
        //实际支付金额 = 金额1-优惠券金额
        payAmount = calcTotalAmountByCoupon(payAmount, usedCouponDTOS);

        //给订单设置金额
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
            log.info("订单{},更新状态:{}失败", orderNumber, omsOrder.getOrderStatus());
            throw new BusinessException(ErrorCodeEnum.OMS0000001);
        }
        return updateCount == 1;
    }

    /**
     * 订单超时取消
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
            log.info("订单{},取消失败", orderNumber);
            throw new BusinessException(ErrorCodeEnum.OMS0000011);
        }
        log.info("订单{}取消成功", orderNumber);
        //调用商品系统 释放库存
        List<RestoreStockDTO> restoreStockVOS = getRestoreStockVOS(orderNumber);
        Boolean restoreFlag = skuStockService.restoreStock(restoreStockVOS);
        log.info("订单:{},商品释放库存成功", orderNumber);
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
     * 根据订单编号获取 商品库存
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
     * 发送订单超时消息
     *
     * @param
     */
    public void sendDelayMessageCancelOrder(OmsOrder order) {
        omsCancelOrderService.addCancelOrder(order.getOrderNumber(), order.getGmtCreate());

    }


    /**
     * 处理优惠券和商品详情
     *
     * @param orderItemList 商品详情
     * @param couponIds     选择的优惠券ids
     * @return 使用了的优惠券集合
     */
    private List<SmsCouponDTO> handleOrderItemRealAmount(List<OmsOrderItem> orderItemList, List<Long> couponIds) {
        if (CollectionUtils.isEmpty(couponIds)) {
            //没有优惠券
            orderItemList.stream().forEach(entity -> {
                //设置最终价格
                entity.setProductSettlementPrice(entity.getProductPrice());
            });
            return new ArrayList<>();
        }
        //获取优惠券 和商品分类的 关系
        List<SmsCouponDTO> coupons = smsCouponService.listByCouponIds(couponIds);
        //key:商品分类id value:优惠券集合 todo 暂时只考虑<商品分类>类型的优惠券
        Map<Long, SmsCouponDTO> pcIdCouponMap = coupons.stream()
                .filter(obj -> obj.getUseType() - UseTypeEnum.CLASSIFICATION.getCode() == 0)
                .collect(Collectors.toMap(
                        obj -> obj.getCouponProductCategoryRelationDTO().getProductCategoryId(),
                        Function.identity()
                ));
        //key:商品分类id  value:商品集合
        Map<Long, List<OmsOrderItem>> pcIdListMap = orderItemList.stream()
                .collect(Collectors.groupingBy(obj -> obj.getProductCategoryId()));
        //最终使用的优惠券集合
        List<SmsCouponDTO> couponDTOs = new ArrayList<>();
        pcIdListMap.entrySet().stream().forEach(obj -> {
            //计算商品的均摊金额
            //商品分类
            Long key = obj.getKey();
            //具体商品详情
            List<OmsOrderItem> items = obj.getValue();
            //当前品类的累计金额
            BigDecimal pcIdPriceSum = items.stream()
                    .map(item -> item.getProductNormalPrice().multiply(new BigDecimal(item.getProductQuantity())))
                    .reduce(ZERO_BIG,
                            (a, b) -> a.add(b));
            //当前品类的优惠券 暂时一个品类只用1张优惠券
            SmsCouponDTO coupon = pcIdCouponMap.get(key);
            //优惠券的门槛金额校验
            if (pcIdPriceSum.compareTo(coupon.getMinPoint()) >= 0) {
                couponDTOs.add(coupon);
                //可以使用优惠券
                items.stream().forEach(item -> {
                    //优惠券均摊价格   商品价格/品类总价 * 优惠券金额
                    BigDecimal price = item.getProductPrice().multiply(coupon.getAmount()).divide(pcIdPriceSum, 3, RoundingMode.HALF_EVEN);
                    //使用 促销价格-优惠券分摊价格
                    BigDecimal settlementPrice = item.getProductPrice().subtract(price);
                    //设置最终价格
                    item.setProductSettlementPrice(settlementPrice);
                });
            }
        });
        return couponDTOs;
    }


    /**
     * 根据订单总金额和优惠券金额算出差价
     *
     * @param totalAmount 订单总金额
     * @param couponDTOS  使用的优惠券集合
     * @return
     */
    private BigDecimal calcTotalAmountByCoupon(BigDecimal totalAmount, List<SmsCouponDTO> couponDTOS) {
        //减去优惠券的金额
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
     * 计算订单应付金额
     * 后期可以增加运费之类的金额
     */
    private BigDecimal calcPayAmount(OmsOrder order) {
        //总金额
        BigDecimal payAmount = order.getTotalAmount();
        return payAmount;
    }

    /**
     * 生成28位订单编号:13位时间戳+2位平台号码+2位支付方式+11位自增数字
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
        log.info("退单失败:订单编号{}", orderNumber);
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
        log.info("退单成功:订单编号{}", orderNumber);
        Wrapper<OmsOrder> wrapper = new UpdateWrapper<OmsOrder>().lambda()
                .eq(OmsOrder::getOrderNumber, orderNumber)
                .eq(OmsOrder::getOrderStatus, OrderStatusEnum.REFUNDING.getCode());
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderStatus(OrderStatusEnum.REFUND_COMPLETED.getCode());
        boolean success = omsOrderDao.update(omsOrder, wrapper) == 1;
        //调用商品系统 释放库存
        if (success) {
            List<RestoreStockDTO> restoreStockVOS = getRestoreStockVOS(orderNumber);
            skuStockService.restoreStock(restoreStockVOS);
        }
        return success;
    }
}
