package com.msb.dongbao.pms.model.dto;

import com.msb.dongbao.pms.model.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 首页推荐专题表 传输实体
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-20
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode
@ApiModel(value="HomeRecommendSubject传输实体", description="首页推荐专题表传输实体")
public class HomeRecommendSubjectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "专题ID")
    private Long subjectId;

    @ApiModelProperty(value = "专题名称")
    private String subjectName;

    @ApiModelProperty(value = "推荐状态")
    private Integer recommendStatus;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;

    @ApiModelProperty(value = "专题主图")
    private String pic;

    @ApiModelProperty(value = "画册图片用逗号分割")
    private String albumPics;

    @ApiModelProperty(value = "专题热词")
    private String hotWords;


    @ApiModelProperty(value = "专题商品列表，取前10个")
    private List<Product> productList;


    @ApiModelProperty(value = "热词列表，原字段为逗号分隔，取其中10个")
    private List<String> hotWordList;

    @ApiModelProperty(value = "专题图集，原字段为逗号分隔，最多5个")
    private List<String> albumPicList;


    public List<String> getHotWordList() {
        if(hotWords != null && !"".equals(hotWords)){
            return Arrays.asList(hotWords.split(","));
        }
        return null;
    }

    public List<String> getAlbumPicList() {
        if(albumPics != null && !"".equals(albumPics)){
            return Arrays.asList(albumPics.split(","));
        }
        return null;
    }
}
