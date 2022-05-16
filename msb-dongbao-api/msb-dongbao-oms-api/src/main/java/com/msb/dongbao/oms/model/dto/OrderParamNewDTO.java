package com.msb.dongbao.oms.model.dto;

import com.msb.dongbao.pms.model.dto.ProductItemParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 生成订单时传入的传输实体
 * Created by macro on 2018/8/30.
 * @author www
 */
@Data
@ApiModel("生成订单时传入的传输实体")
@EqualsAndHashCode
public class OrderParamNewDTO {
    @ApiModelProperty("收货地址ID")
    private Long memberReceiveAddressId;

    @ApiModelProperty("收货地址")
    @NotBlank
    private String receiverDetailAddress;

    @ApiModelProperty("收货电话")
    @NotBlank
    private String receiverPhone;

    @ApiModelProperty("收货人姓名")
    @NotBlank
    private String receiverName;

    @ApiModelProperty("支付方式")
    @NotNull
    private Integer payType;

    @ApiModelProperty("被选中的购商品列表")
    @NotEmpty
    private List<ProductItemParam> items;

    @ApiModelProperty("使用的优惠券id集合")
    private List<Long> useCouponIds;



}
