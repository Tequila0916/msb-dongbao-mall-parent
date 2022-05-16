package com.msb.dongbao.ums.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 收货地址实体
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
@TableName("ums_address")
public class UmsAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收货地址主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 当前登录用户名称
     */
    private String loginUser;

    /**
     * 收件人
     */
    private String recipient;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 默认收货地址 1-是,0-否
     */
    private Boolean defaultAddress;

    /**
     * 逻辑删除 1-已删除,0-未删除
     */
    private Boolean enabled;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 更新时间
     */
    private Long gmtModified;


}
