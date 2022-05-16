package com.msb.dongbao.ums.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 *
 * 滑块验证传输实体
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 2020/8/20 18:36
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
public class CheckDataParamDTO {

    @ApiModelProperty(value = "滑块轨迹", required = true)
    @NotEmpty(message = "滑块轨迹传输异常")
    private List<Integer> tracks;

}
