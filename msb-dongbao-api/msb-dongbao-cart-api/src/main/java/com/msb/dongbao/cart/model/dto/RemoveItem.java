package com.msb.dongbao.cart.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 移除购物车数据实体
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年08月20日 17时47分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
public class RemoveItem implements Serializable {
    /**
     * sku编号
     */
    private String skuNo;
    /**
     * 移除数量
     */
    private Integer number;
}
