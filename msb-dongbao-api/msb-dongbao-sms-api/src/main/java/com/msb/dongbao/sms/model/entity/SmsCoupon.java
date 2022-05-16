package com.msb.dongbao.sms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 优惠券实体
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-16
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@TableName("sms_coupon")
public class SmsCoupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券
     */
    private Integer type;

    /**
     * 名称
     */
    private String name;

    /**
     * 使用平台：0->全部；1->移动；2->PC
     */
    private Integer platform;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 每人限领张数
     */
    private Integer perLimit;

    /**
     * 使用门槛；0表示无门槛
     */
    private BigDecimal minPoint;

    /**
     * 开始使用时间
     */
    private Long startTime;

    /**
     * 结束使用时间
     */
    private Long endTime;

    /**
     * 使用类型：0->全场通用；1->指定分类；2->指定商品
     */
    private Integer useType;

    /**
     * 备注
     */
    private String note;

    /**
     * 发行数量
     */
    private Integer publishCount;

    /**
     * 已使用数量
     */
    private Integer useCount;

    /**
     * 领取数量
     */
    private Integer receiveCount;

    /**
     * 可以领取的日期
     */
    private Long enableTime;

    /**
     * 优惠码
     */
    private String code;

    /**
     * 可领取的会员类型：0->无限制
     */
    private Integer memberLevel;


}
