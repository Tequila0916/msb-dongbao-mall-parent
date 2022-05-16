package com.msb.dongbao.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msb.dongbao.cart.model.dto.CartItem;
import com.msb.dongbao.cart.service.IShoppingCartService;
import com.msb.dongbao.common.base.exception.BusinessException;
import com.msb.dongbao.common.util.IDUtils;
import com.msb.dongbao.pms.model.entity.Product;
import com.msb.dongbao.pms.model.entity.SkuStock;
import com.msb.dongbao.pms.service.IProductService;
import com.msb.dongbao.pms.service.ISkuStockService;
import com.msb.dongbao.ums.model.dto.MemberDetailsDTO;
import com.msb.dongbao.ums.model.entity.UmsMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.msb.dongbao.common.base.enums.ErrorCodeEnum.*;

/**
 * 购物车抽象类
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月30日 14时55分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public abstract class AbstractShoppingCartService implements IShoppingCartService {

    @Autowired
    ISkuStockService skuStockService;
    @Autowired
    IProductService productService;

    /**
     * 验证添物车商品的合法性，并且填充相关属性
     * @param cartItem
     */
    protected void validateAndFillCartItem(CartItem cartItem) {

        String skuNo = cartItem.getSkuNo();

        // 调用商品接口查询对应的SKU信息
        LambdaQueryWrapper<SkuStock> queryWrapper = new QueryWrapper<SkuStock>().lambda()
                .eq(SkuStock::getSkuNo, skuNo);
        SkuStock skuStock = skuStockService.getOne(queryWrapper);
        if(skuStock == null){
            throw new BusinessException(PMS0001601);
        }
        Integer num = skuStock.getNum();
        if(num < cartItem.getAmount()){
            throw new BusinessException(PMS0001600);
        }

        Long relProductId = skuStock.getRelProductId();
        Product product = productService.getById(relProductId);
        if(product == null){
            throw new BusinessException(PMS0001602);
        }

        // 根据用户优惠、活动、积分等计算商品的最终价格
        calculatePrice(cartItem,skuStock);
        // 填充信息
        cartItem.setPostFree((byte)1);
        cartItem.setStatus((byte)0);
        long now = System.currentTimeMillis();
        cartItem.setAddTime(now);
        cartItem.setUpdateTime(now);
        cartItem.setItemId(IDUtils.UUID());
        cartItem.setSkuId(skuStock.getId());
        cartItem.setSkuNo(skuStock.getSkuNo());
        cartItem.setSkuSpec(skuStock.getSpec());
        cartItem.setTitle(skuStock.getTitle());
        cartItem.setImage(skuStock.getImage());
        cartItem.setCategoryId(product.getRelCategory3Id());
        cartItem.setRelProductId(skuStock.getRelProductId());
    }

    /**
     * 根据商品信息查询活动、用户优惠等，进行商品价格的计算
     * @param cartItem
     * @param skuStock
     */
    protected void calculatePrice(CartItem cartItem, SkuStock skuStock) {
        // 使用责任链模式处理活动、优惠价格计算
        cartItem.setSpecialPrice(skuStock.getPrice());
    }

    /**
     * 获取当前登录用户的信息
     */
    protected UmsMember getCurrentUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user instanceof MemberDetailsDTO){
            MemberDetailsDTO userDetails = (MemberDetailsDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return  userDetails.getUmsMember();
        }
        return null;
    }
}
