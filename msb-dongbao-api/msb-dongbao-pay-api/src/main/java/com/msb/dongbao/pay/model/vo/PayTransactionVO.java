package com.msb.dongbao.pay.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 支付流水表 页面展示实体
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
@ApiModel(value="PayTransaction页面展示实体", description="支付流水表页面展示实体")
public class PayTransactionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "应用id")
    private String relAppId;

    @ApiModelProperty(value = "应用方订单编号")
    private String relOrderNo;

    @ApiModelProperty(value = "支付渠道id，用来识别支付方式，如：支付宝、微信、Paypal等")
    private Integer payChannelId;

    @ApiModelProperty(value = "交易唯一编码")
    private String transactionNo;

    @ApiModelProperty(value = "第三方流水号")
    private String tradeNo;

    @ApiModelProperty(value = "订单总金额，总共10位，小数位数存2位")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "实际支付订单金额，总共10位，小数位数存2位")
    private BigDecimal payFee;

    @ApiModelProperty(value = "订单过期时间")
    private Long expireTime;

    @ApiModelProperty(value = "第三方支付成功的时间，同步接收")
    private Long paymentTime;

    @ApiModelProperty(value = "收到异步通知的时间")
    private Long notifyTime;

    @ApiModelProperty(value = "支付状态:0-等待支付中;1-待付款完成;2-支付成功;3:交易已关闭;-1:支付失败")
    private Integer orderStatus;

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

    @ApiModelProperty(value = "商户id")
    private String merchantId;



}
