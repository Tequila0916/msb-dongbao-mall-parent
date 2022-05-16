package com.msb.dongbao.ums.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 收货地址传输实体
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-01
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value="收货地址传输实体", description="收货地址传输实体")
public class UmsAddressDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地址主键")
    private Long id;

    @ApiModelProperty(value = "当前登录用户名称")
    private String loginUser;

    @ApiModelProperty(value = "收件人")
    private String recipient;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "手机号码")
    private String telephone;

    @ApiModelProperty(value = "默认收货地址 1-是,0-否")
    private Boolean defaultAddress;

}
