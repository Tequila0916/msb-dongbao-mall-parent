package com.msb.dongbao.cart.service.impl;

import com.msb.dongbao.cart.model.dto.CartItem;
import com.msb.dongbao.cart.model.dto.RemoveItem;
import com.msb.dongbao.cart.model.dto.ShoppingCart;
import com.msb.dongbao.cart.util.CookieUtils;
import com.msb.dongbao.common.base.ApplicationContextHolder;
import com.msb.dongbao.common.base.constant.CommonConstants;
import com.msb.dongbao.common.base.constant.RedisConstants;
import com.msb.dongbao.common.util.IDUtils;
import com.msb.dongbao.ums.model.entity.UmsMember;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * cart:
 *   storage:
 *     redis:
 *       enable: true
 *     mongo:
 *       enable: false
 * 购物车业务实现类
 * 基于Redis存储
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月18日 14时44分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
@ConditionalOnExpression("${cart.storage.redis.enabled} &&!${cart.storage.mongo.enabled}")
public class RedisShoppingCartServiceImpl extends AbstractShoppingCartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String generateUserCartKey(HttpServletRequest request, HttpServletResponse response, UmsMember currentUser) {

        String cartKey = null;
        // 用来存储 cookie中的临时key
        String tempKey = CookieUtils.getCookieValue(request, CommonConstants.COOKIE_CART_KEY);
        if(StringUtils.isNotEmpty(tempKey)){
            cartKey = tempKey;
        }

        // key为空，说明首次加购物车
        if(StringUtils.isEmpty(cartKey)){
            // 临时key
            cartKey = RedisConstants.REDIS_UNLOGIN_CART_COOKIE_KEY + IDUtils.UUID();
            // 如果用户登录，则使用用户Code
            if(currentUser != null) {
                cartKey = RedisConstants.REDIS_LOGIN_CART_COOKIE_KEY +currentUser.getId();
            }
            // 写回Cookie
            CookieUtils.setCookie(request,response,CommonConstants.COOKIE_CART_KEY,cartKey);
        }
        // 需要合并购物车
        else if(StringUtils.isNotEmpty(cartKey) && currentUser != null){
            cartKey = RedisConstants.REDIS_LOGIN_CART_COOKIE_KEY + currentUser.getId();
            if(tempKey.startsWith(RedisConstants.REDIS_UNLOGIN_CART_COOKIE_KEY)){
                mergeShoppingCart(tempKey,currentUser);
            }
        }
        return cartKey;
    }

    @Override
    public ShoppingCart mergeShoppingCart(String tempKey, UmsMember currentUser) {

        ShoppingCart loginCart = null;
        String loginCartKey = null;

        ValueOperations valueOperations = redisTemplate.opsForValue();
        ShoppingCart unLoginShoppingCart = (ShoppingCart)valueOperations.get(tempKey);
        // 临时购物车为空
        if(unLoginShoppingCart == null){
            unLoginShoppingCart = new ShoppingCart();
            unLoginShoppingCart.setItems(new ArrayList<>());
        }

        // 用户已登录 且 等于临时 key
        if(currentUser != null && tempKey.startsWith(RedisConstants.REDIS_UNLOGIN_CART_COOKIE_KEY)){
            // 如果用户登录 并且 当前是未登录的key
            loginCartKey = RedisConstants.REDIS_LOGIN_CART_COOKIE_KEY + currentUser.getId();
            loginCart = mergeShoppingCart(loginCartKey,currentUser);
            List<CartItem> unLoginTempCartItems = unLoginShoppingCart.getItems();
            // 未登录购物车不为空
            if(CollectionUtils.isNotEmpty(unLoginTempCartItems)){
                List<CartItem> loginTempCartItems = loginCart.getItems();
                if(CollectionUtils.isNotEmpty(loginTempCartItems)){
                    // 满足未登录时的购物车不为空 并且 当前用户已经登录
                    for(CartItem cartItem:unLoginTempCartItems){
                        long count = loginTempCartItems.stream().filter(cv -> cv.getSkuNo().equalsIgnoreCase(cartItem.getSkuNo())).count();
                        if(count == 0){
                            loginTempCartItems.add(cartItem);
                        }
                        else{
                            // 重复商品，修改数量
                            loginTempCartItems.stream().filter(cv -> cv.getSkuNo().equalsIgnoreCase(cartItem.getSkuNo()))
                                    .findFirst().ifPresent(e -> e.setAmount(e.getAmount()+cartItem.getAmount()));

                        }
                    }
                }
                // 当前登录用户的购物车为空则 将未登录时的购物车合并
                else{
                    loginCart.setItems(unLoginTempCartItems);
                }

            }
            unLoginShoppingCart = loginCart;
            // 删除临时key
            redisTemplate.delete(tempKey);
            valueOperations.set(loginCartKey,unLoginShoppingCart);
        }
        return unLoginShoppingCart;
    }

    @Override
    public CartItem addCart(HttpServletRequest request, HttpServletResponse response, CartItem cartItem) {

        /**
         * 1. 获取上下文用户信息
         * 2. 判断用户是否登录
         *   2.1. 未登录，生成临时 tempKey，将商品信息写入购物车，将临时key写入cookie
         *   2.2. 登录，根据用户code生成购物车key，然后顺便将cookie中对应的 tempKey 的购物车信息，合并到用户code中
         * 3. 返回所有的购物车信息
         */
        validateAndFillCartItem(cartItem);
        UmsMember currentUser = getCurrentUser();
        String cartKey = generateUserCartKey(request, response, currentUser);
        ShoppingCart cacheCart = mergeShoppingCart(cartKey,currentUser);
        List<CartItem> items = cacheCart.getItems();
        // 购物车已经存在商品
        if(CollectionUtils.isNotEmpty(items)){
            CartItem target = items.stream().filter(item -> item.getSkuNo().equalsIgnoreCase(cartItem.getSkuNo())).findFirst().orElse(null);
            if(target == null){
                items.add(cartItem);
            }
            else{
                target.setAmount(target.getAmount() + cartItem.getAmount());
            }
        }else{
            // 此处items不会为 null
            items.add(cartItem);
        }
        // 将合并后的购物车数据写入redis
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(cartKey,cacheCart);
        return cartItem;
    }

    @Override
    public Boolean updateCart(HttpServletRequest request, HttpServletResponse response, CartItem cartItem, CartItem oldCartItem) {
        validateAndFillCartItem(cartItem);
        UmsMember currentUser = getCurrentUser();
        String cartKey = generateUserCartKey(request, response, currentUser);
        ShoppingCart cacheCart = mergeShoppingCart(cartKey,currentUser);
        cacheCart.getItems().removeIf(item -> item.getSkuNo().equalsIgnoreCase(oldCartItem.getSkuNo()));
        cacheCart.getItems().add(cartItem);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(cartKey,cacheCart);
        return true;
    }

    @Override
    public Boolean removeCartItem(HttpServletRequest request, HttpServletResponse response, List<CartItem> cartItems) {
        UmsMember currentUser = getCurrentUser();
        String cartKey = generateUserCartKey(request, response, currentUser);
        ShoppingCart cacheCart = mergeShoppingCart(cartKey,currentUser);
        if(cacheCart != null){
            List<CartItem> items = cacheCart.getItems();
            if(CollectionUtils.isNotEmpty(items)){
                // 循环处理删除的商品
                for (CartItem cv:cartItems){
                    CartItem target = items.stream().filter(item -> item.getSkuNo().equalsIgnoreCase(cv.getSkuNo())).findFirst().orElse(null);
                    if(target == null){
                        continue;
                    }
                    if(target.getAmount() > cv.getAmount()){
                        target.setAmount(target.getAmount() - cv.getAmount());
                    }
                    else if(target.getAmount() <= cv.getAmount()){
                        items.remove(target);
                    }
                }
                ValueOperations<String, ShoppingCart> valueOperations = redisTemplate.opsForValue();
                valueOperations.set(cartKey,cacheCart);
            }
        }
        return true;
    }


    @Override
    public Boolean clearCart(HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationContextHolder.SessionUser sessionUser = null;
        UmsMember currentUser = getCurrentUser();
        String cartKey = generateUserCartKey(request, response, currentUser);
        if(redisTemplate.hasKey(cartKey)){
            redisTemplate.delete(cartKey);
            return true;
        }
        return false;
    }


    @Override
    public ShoppingCart findCartItemList(HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UmsMember currentUser = getCurrentUser();
        String cartKey = generateUserCartKey(request, response, currentUser);
        return  mergeShoppingCart(cartKey,currentUser);
    }


    @Override
    public Boolean removeCartItems(List<RemoveItem> removeItems) {
        Long currentUserId = Optional.ofNullable(getCurrentUser()).orElse(new UmsMember()).getId();
        String loginCartKey = RedisConstants.REDIS_LOGIN_CART_COOKIE_KEY + currentUserId;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        ShoppingCart shoppingCart = (ShoppingCart)valueOperations.get(loginCartKey);
        if(shoppingCart != null){
            List<CartItem> items = shoppingCart.getItems();
            if(CollectionUtils.isNotEmpty(items)){
                // 循环处理删除的商品
                for (RemoveItem ci:removeItems){
                    CartItem target = items.stream().filter(item -> item.getSkuNo().equalsIgnoreCase(ci.getSkuNo())).findFirst().orElse(null);
                    if(target == null){
                        continue;
                    }
                    if(target.getAmount() > ci.getNumber()){
                        target.setAmount(target.getAmount() - ci.getNumber());
                    }
                    else if(target.getAmount() <= ci.getNumber()){
                        items.remove(target);
                    }
                }
                valueOperations.set(loginCartKey,shoppingCart);
            }
        }
        return true;
    }
}
