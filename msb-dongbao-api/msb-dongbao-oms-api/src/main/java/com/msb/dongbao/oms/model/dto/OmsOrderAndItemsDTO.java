package com.msb.dongbao.oms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单和订单详情的关联传输实体
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
@ApiModel(value = "订单和订单详情的关联传输实体", description = "订单和订单详情的关联传输实体")
public class OmsOrderAndItemsDTO extends OmsOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单详情")
    List<OmsOrderItemDTO> items;

    @ApiModelProperty(value = "优惠券总金额")
    BigDecimal couponAllAmount;


}
