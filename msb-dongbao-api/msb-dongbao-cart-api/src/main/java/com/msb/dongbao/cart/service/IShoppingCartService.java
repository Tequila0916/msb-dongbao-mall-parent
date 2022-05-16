package com.msb.dongbao.cart.service;

import com.msb.dongbao.cart.model.dto.CartItem;
import com.msb.dongbao.cart.model.dto.RemoveItem;
import com.msb.dongbao.cart.model.dto.ShoppingCart;
import com.msb.dongbao.ums.model.entity.UmsMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车业务接口
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月18日 09时33分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IShoppingCartService {

    /**
     * 获得用户购物车key
     *
     * 1.用户未登录情况下第一次进入购物车
     *          ->  生成 key 保存至 cookie中
     * 2.用户未登录情况下第n进入购物车
     *          ->  从cookie中取出key
     * 3.用户登录情况下，cookie中没有key
     *          -> 根据用户code生成key
     * 4.用户登录情况下并且cookie中存在key
     *          -> 从cookie取的的key从缓存取得购物车 合并至用户code生成key的购物车中去,
     *          这样后面才能根据用户code 取得正确的购物车
     * @param request
     * @param response
     * @param currentUser
     * @return
     */
    String generateUserCartKey(HttpServletRequest request, HttpServletResponse response, UmsMember currentUser);

    /**
     * 合并cookie中的购物车到用户key购物车，返回最终信息
     * @param tempKey
     * @param currentUser
     * @return
     */
    ShoppingCart mergeShoppingCart(String tempKey, UmsMember currentUser);

    /**
     * 添加购物车
     * 1. 未登录
     * 2. 已经登录
     * @param request
     * @param response
     * @param cartItem
     * @return
     */
    CartItem addCart(HttpServletRequest request, HttpServletResponse response, CartItem cartItem);

    /**
     * 更新购物车的商品
     * egg: 将白色裤子 3条 -> 直接换成黑色 3条，其实算是先删除，再添加的操作
     * @param request
     * @param response
     * @param cartItem
     * @param oldCartItem
     * @return
     */
    Boolean updateCart(HttpServletRequest request, HttpServletResponse response, CartItem cartItem, CartItem oldCartItem);

    /**
     * 将商品移除购物车
     * @param request
     * @param response
     * @param cartItems
     * @return
     */
    Boolean removeCartItem(HttpServletRequest request, HttpServletResponse response, List<CartItem> cartItems);

    /**
     * 清空购物车
     * @param request
     * @param response
     * @return
     */
    Boolean clearCart(HttpServletRequest request, HttpServletResponse response);

    /**
     * 查看购物车的详细信息、商品列表
     * @param request
     * @param response
     * @return
     */
    ShoppingCart findCartItemList(HttpServletRequest request, HttpServletResponse response);


    /**
     * 下单操作完成后，根据sku列表移除购物车数据，用户肯定是在在登录情况下
     * @param removeItems
     * @return
     */
    Boolean removeCartItems(List<RemoveItem> removeItems);
}
