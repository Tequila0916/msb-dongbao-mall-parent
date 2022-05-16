package com.msb.dongbao.oms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 退单传输实体
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
@ApiModel(value="退单传输实体", description="退单传输实体")
public class OrderRefundDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "退单编号")
    private String refundNo;

    @ApiModelProperty(value = "关联订单编号")
    @NotBlank
    private String orderNumber;

    @ApiModelProperty(value = "商家id")
    private String merchantId;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "物流单号")
    private String expressNo;

    @ApiModelProperty(value = "收货人姓名")
    private String consigneeRealname;

    @ApiModelProperty(value = "联系电话")
    private String consigneeTelphone;

    @ApiModelProperty(value = "收货地址")
    private String consigneeAddress;

    @ApiModelProperty(value = "退货原因")
    @NotBlank
    private String reason;

    @ApiModelProperty(value = "退货状态:0待处理,1处理成功,2处理失败")
    private Integer orStatus;

    @ApiModelProperty(value = "退货处理时间")
    private Long handlingTime;

    @ApiModelProperty(value = "创建时间(退货申请时间)")
    private Long gmtCreate;

    @ApiModelProperty(value = "修改时间")
    private Long gmtModified;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    private String modifiedBy;



}
