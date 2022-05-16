package com.msb.dongbao.oms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *订单详情实体
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@TableName("oms_order_item")
public class OmsOrderItem extends Model<OmsOrderItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品图片
     */
    private String productPic;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品品牌
     */
    private String productBrand;

    /**
     * 正常价格
     */
    private BigDecimal productNormalPrice;

    /**
     * 销售价格(商品满减活动后的价格)
     */
    private BigDecimal productPrice;

    /**
     * 结算价格(用完优惠券的价格)
     */
    private BigDecimal productSettlementPrice;

    /**
     * 购买数量
     */
    private Integer productQuantity;

    /**
     * 商品sku编号
     */
    private Long productSkuId;

    /**
     * 查询商品详情的时候用
     */
    private Long productSpuId;

    /**
     * 商品sku条码
     */
    private String productSkuCode;

    /**
     * 商品分类id
     */
    private Long productCategoryId;

    /**
     * 实例：商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     */
    private String productAttr;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 修改时间
     */
    private Long gmtModified;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String modifiedBy;




    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
