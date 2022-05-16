package com.msb.dongbao.portal.controller.cart;

import com.msb.dongbao.cart.model.dto.CartItem;
import com.msb.dongbao.cart.model.dto.UpdateCartItem;
import com.msb.dongbao.cart.service.IShoppingCartService;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商城购物车控制器
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月18日 09时12分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "购物车控制器", tags = "购物车控制器")
@Slf4j
@RestController
@RequestMapping(value = "/cart/*")
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加购物车
     *
     * @param cartItem
     * @return
     */
    @PostMapping(value = "/item/add")
    @ApiOperation(value = "添加购物车")
    public ResultWrapper<CartItem> addCartItem(@Validated @RequestBody CartItem cartItem, HttpServletRequest request, HttpServletResponse response) {
        CartItem newCartItem = shoppingCartService.addCart(request, response, cartItem);
        return ResultWrapper.getSuccessBuilder().data(newCartItem).build();
    }


    /**
     * 更换购物车商品中的规格或者数量信息
     *
     * @param updateCartItem
     * @return
     */
    @PostMapping(value = "/items/update")
    @ApiOperation(value = "更新购物车中的商品信息")
    public ResultWrapper<Boolean> updateCartItem(@Validated @RequestBody UpdateCartItem updateCartItem, HttpServletRequest request, HttpServletResponse response) {
        Boolean aBoolean = shoppingCartService.updateCart(request, response, updateCartItem.getNewCartItem(), updateCartItem.getOldCartItem());
        return ResultWrapper.getSuccessBuilder().data(aBoolean).build();
    }


    /**
     * 移除部分购物车商品
     * 1、移除单个
     * 2、移除多个
     *
     * @param cartItems
     * @return
     */
    @PostMapping(value = "/items/remove")
    @ApiOperation(value = "移除部分购物车商品")
    public ResultWrapper<Boolean> removeCartItem(@Validated @RequestBody List<CartItem> cartItems, HttpServletRequest request, HttpServletResponse response) {
        Boolean aBoolean = shoppingCartService.removeCartItem(request, response, cartItems);
        return ResultWrapper.getSuccessBuilder().data(aBoolean).build();
    }


    /**
     * 清空购物车
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/clear")
    @ApiOperation(value = "清空购物车")
    public ResultWrapper<Boolean> clearCart(HttpServletRequest request, HttpServletResponse response) {
        Boolean aBoolean = shoppingCartService.clearCart(request, response);
        return ResultWrapper.getSuccessBuilder().data(aBoolean).build();
    }


    /**
     * 查询我的购物车商品列表
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/item/list")
    @ApiOperation(value = "查询我的购物车商品列表")
    public ResultWrapper<List<CartItem>> findCartItemList(HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> items = shoppingCartService.findCartItemList(request, response).getItems();
        return ResultWrapper.getSuccessBuilder().data(items).build();
    }
}
