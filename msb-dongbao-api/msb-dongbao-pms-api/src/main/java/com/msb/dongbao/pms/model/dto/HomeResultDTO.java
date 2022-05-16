package com.msb.dongbao.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年07月02日 18时24分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value="Product传输实体", description="商品表传输实体")
public class HomeResultDTO  implements Serializable {

    @ApiModelProperty(value = "人气推荐商品列表")
    private List<HomeRecommendProductDTO> recommendProducts;
    @ApiModelProperty(value = "新品推荐列表")
    private List<HomeNewProductDTO> newProducts;
    @ApiModelProperty(value = "专题广场列表,目前只列两个")
    private List<HomeRecommendSubjectDTO> recommendSubjects;
}
