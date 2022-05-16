package com.msb.dongbao.cart.model.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 购物车数据关联的活动信息
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月18日 09时23分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
public class ItemActivity implements Serializable {
    private static final long serialVersionUID = -8942500987125543799L;
    /** 活动ID */
    private Integer actId;
    /** 活动类型 */
    private String actType;
    /** 活动标题 */
    private String actTitle;
}