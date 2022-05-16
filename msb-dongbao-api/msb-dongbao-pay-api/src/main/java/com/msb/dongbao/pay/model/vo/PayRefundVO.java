package com.msb.dongbao.pay.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 退款记录表 页面展示实体
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
@ApiModel(value="PayRefund页面展示实体", description="退款记录表页面展示实体")
public class PayRefundVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "应用id")
    private String relAppId;

    @ApiModelProperty(value = "上游应用的退款订单编码")
    private String relRefundOrderNo;

    @ApiModelProperty(value = "支付系统交易唯一编码")
    private String relTransactionNo;

    @ApiModelProperty(value = "第三方交易号")
    private String tradeNo;

    @ApiModelProperty(value = "支付平台生成的唯一退款单号")
    private String refundNo;

    @ApiModelProperty(value = "支付方式")
    private Integer payMethodId;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundFee;

    @ApiModelProperty(value = "退款理由")
    private String refundReason;

    @ApiModelProperty(value = "退款类型；0:业务退款; 1:重复退款")
    private Integer refundType;

    @ApiModelProperty(value = "退款方式：1-自动原路返回; 2-人工打款")
    private Integer refundMethod;

    @ApiModelProperty(value = "0-未退款; 1-退款处理中; 2-退款成功; 3-退款不成功")
    private Integer refundStatus;

    @ApiModelProperty(value = "创建时间")
    private Long gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Long gmtModified;

    @ApiModelProperty(value = "创建人uid")
    private String createUid;

    @ApiModelProperty(value = "创建人昵称")
    private String createUname;

    @ApiModelProperty(value = "更新人uid")
    private String modifiedUid;

    @ApiModelProperty(value = "更新人昵称")
    private String modifiedUname;

    @ApiModelProperty(value = "是否删除:0-未删除;1-删除")
    private Integer enable;



}
