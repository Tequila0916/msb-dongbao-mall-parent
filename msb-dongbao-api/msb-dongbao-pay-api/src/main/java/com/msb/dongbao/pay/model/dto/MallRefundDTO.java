package com.msb.dongbao.pay.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 商城退款实体
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月08日 14时57分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Builder
@Data
public class MallRefundDTO implements Serializable {
    /**
     * 支付方式编码
     */
    private String payCode;
    /**
     *  退费订单编号
     */
    private String refundOrderNo;


}
