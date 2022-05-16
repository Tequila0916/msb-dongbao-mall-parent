package com.msb.dongbao.oms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 退单实体
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
@TableName("order_refund")
public class OrderRefund implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 退单编号
     */
    private String refundNo;

    /**
     * 关联订单编号
     */
    private String orderNumber;

    /**
     * 商家ID
     */
    private String merchantId;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;


    /**
     * 物流单号
     */
    private String expressNo;

    /**
     * 收货人姓名
     */
    private String consigneeRealname;

    /**
     * 联系电话
     */
    private String consigneeTelphone;

    /**
     * 收货地址
     */
    private String consigneeAddress;

    /**
     * 退货原因
     */
    private String reason;

    /**
     * 退货状态:0待处理,1处理成功,2处理失败
     */
    private Integer orStatus;

    /**
     * 退货处理时间
     */
    private Long handlingTime;

    /**
     * 创建时间(退货申请时间)
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


}
