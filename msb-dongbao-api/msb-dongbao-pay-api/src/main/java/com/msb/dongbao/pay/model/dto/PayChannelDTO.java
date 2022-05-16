package com.msb.dongbao.pay.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 支付方式 传输实体
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
@ApiModel(value="PayChannel传输实体", description="支付方式传输实体")
public class PayChannelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "支付方式名称")
    private String channelName;

    @ApiModelProperty(value = "支付方式code")
    private String channelCode;

    @ApiModelProperty(value = "支付策略类，用于策略模式实现")
    private String strategyBean;

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
