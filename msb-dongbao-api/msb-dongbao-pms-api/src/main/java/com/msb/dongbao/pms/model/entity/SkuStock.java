package com.msb.dongbao.pms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * SKU
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-21
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode

public class SkuStock implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * SKU主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * SKU编号
     */
    private String skuNo;

    /**
     * 商品id
     */
    private Long relProductId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 卖点
     */
    private String sellPoint;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 库存数量
     */
    private Integer num;

    /**
     * 冻结库存数量
     */
    private Integer lockStockNum;

    /**
     * 库存版本
     */
    private Integer version;

    /**
     * 商品照片
     */
    private String image;

    /**
     * 规格哈希值
     */
    private String specHash;

    /**
     * 规格集合
     */
    private String spec;

    /**
     * 规格选项id集合
     */
    private String optionIds;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 更新时间
     */
    private Long gmtModified;

    /**
     * 逻辑删除 0-未删除，1-删除
     */
    private Boolean enabled;

    /**
     * 更新人uid
     */
    private String modifiedUid;

    /**
     * 创建人uid
     */
    private String createUid;

    /**
     * 更新人昵称
     */
    private String modifiedUname;

    /**
     * 创建人昵称
     */
    private String createUname;


}
