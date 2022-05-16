package com.msb.dongbao.pms.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品分类表
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-08-21
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@TableName("category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 中文
     */
    private String name;

    /**
     * 英语
     */
    private String en;

    /**
     * 排序，暂未使用
     */
    private Integer sort;

    /**
     * 类目id，关联pid使用
     */
    private Integer catid;

    /**
     * 是否使用catid查询
     */
    private Integer catidUse;

    /**
     * 淘宝查询
     */
    private String query;

    /**
     * 是否使用query
     */
    private Integer queryUse;

    /**
     * 类目单元配重
     */
    private Float weight;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 更新时间
     */
    private Long gmtModified;

    /**
     * 创建人uid
     */
    private String createUid;

    /**
     * 创建人昵称
     */
    private String createUname;

    /**
     * 更新人uid
     */
    private String modifiedUid;

    /**
     * 更新人昵称
     */
    private String modifiedUname;

    /**
     * 是否删除:0-未删除;1-删除
     */
    private Integer enabled;

    /**
     * 商户ID
     */
    private String merchantId;


}
