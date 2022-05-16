package com.msb.dongbao.pms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-17
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品编号
     */
    private String productNo;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商户id
     */
    private Long relTenantId;

    /**
     * 默认SKU
     */
    private Long relDefaultSkuId;

    /**
     * 一级类目
     */
    private Long relCategory1Id;

    /**
     * 二级类目
     */
    private Long relCategory2Id;

    /**
     * 三级类目
     */
    private Long relCategory3Id;

    /**
     * 规格id集合
     */
    private String specOptions;

    /**
     * 商城价
     */
    private BigDecimal price;

    /**
     * 商品图片
     */
    private String defaultPic;

    /**
     * 商品组图,加上默认主图，最多允许5张图，逗号分割
     */
    private String albumPics;

    /**
     * 商品详情描述
     */
    private String detailDesc;

    /**
     * 商品详情web端页面，基本以图为主，富文本HTML样式
     */
    private String detailHtml;

    /**
     * 销量
     */
    private Integer salesNum;

    /**
     * 逻辑删除 0-未删除，1-删除
     */
    private Boolean enabled;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 更新时间
     */
    private Long gmtModified;

    /**
     * 更新人uid
     */
    private String modifiedUid;

    /**
     * 创建人uid
     */
    private String createUid;

    /**
     * 创建人昵称
     */
    private String createUname;

    /**
     * 更新人昵称
     */
    private String modifiedUname;

    /**
     * 上架状态：0->下架；1->上架
     */
    private Integer publishStatus;


}
