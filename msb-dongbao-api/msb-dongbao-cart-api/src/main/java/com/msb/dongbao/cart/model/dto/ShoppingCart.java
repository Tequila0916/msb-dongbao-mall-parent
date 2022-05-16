package com.msb.dongbao.cart.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车实体
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月18日 09时16分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@NoArgsConstructor
@Data
@Document(value = "shopping_cart")
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 3077125665949233592L;

    @MongoId(targetType = FieldType.STRING)
    private String shoopingCartId;
    /**购物车商品集合,默认初始化为空集合,不需要再次初始化 */
    private List<CartItem> items;
    /** 更新时间 */
    private Long updateTime;
    /** 预留字段,版本 */
    private Integer version;
}
