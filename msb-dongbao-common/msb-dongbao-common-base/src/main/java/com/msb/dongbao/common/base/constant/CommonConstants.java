package com.msb.dongbao.common.base.constant;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月18日 15时22分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface CommonConstants {
    String SESSION_USER_KEY = "user";
    String COOKIE_CART_KEY = "shoppingCart";
    Integer COOKIE_CART_KEY_MAX_AGE = 7*24*60*60;
    /** 商品一级分类前端展示个数 */
    Integer  CATEGORY_PARENT_SIZE = 10;
    /** 商品子级分类前端展示个数 */
    Integer  CATEGORY_CHILD_SIZE = 2;
}
