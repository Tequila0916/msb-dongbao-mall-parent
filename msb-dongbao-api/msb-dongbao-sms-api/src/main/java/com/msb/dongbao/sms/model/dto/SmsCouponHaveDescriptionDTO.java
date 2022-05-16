package com.msb.dongbao.sms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * <p>
 *  带有文案的优惠券实体
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-16
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "带有文案的优惠券实体", description = "带有文案的优惠券实体")
public class SmsCouponHaveDescriptionDTO extends SmsCouponDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "是否可以领取：0->当前用户没有领取过,1->当前用户已经领取过了")
    private Integer isReceive;

    @ApiModelProperty(value = "前端优惠券显示文案")
    private String text;

    @ApiModelProperty(value = "前端时间期限显示文案")
    private String dateText;

    /**
     * 处理优惠券的显示文案
     */
    public void handleTexts(){
        //todo 抽成常量
        String textTemplate = "满%s减%s";
        String dateTextTemplate="%s-%s";
        String minPointS = Optional.ofNullable(super.getMinPoint()).map(obj -> obj.toString()).orElse("");
        String amountS = Optional.ofNullable(super.getAmount()).map(obj -> obj.toString()).orElse("");
        String text1 = String.format(textTemplate, minPointS, amountS);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startTimeS = Optional.ofNullable(super.getStartTime()).map(obj -> sdf.format(new Date(obj))).orElse("");
        String endTimeS = Optional.ofNullable(super.getEnableTime()).map(obj -> sdf.format(new Date(obj))).orElse("");
        String dateText1 = String.format(dateTextTemplate, startTimeS, endTimeS);

        this.text = text1;
        this.dateText = dateText1;
    }





}
