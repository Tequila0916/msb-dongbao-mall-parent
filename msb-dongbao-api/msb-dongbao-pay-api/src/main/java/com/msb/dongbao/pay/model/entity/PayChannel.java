package com.msb.dongbao.pay.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 支付方式
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-09
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@TableName("pay_channel")
public class PayChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 支付方式名称
     */
    private String channelName;

    /**
     * 支付方式code
     */
    private String channelCode;

    /**
     * 支付策略类，用于策略模式实现
     */
    private String strategyBean;

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
    private Integer enable;

    /**
     * 商户id
     */
    private String merchantId;


}
