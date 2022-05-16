package com.msb.dongbao.cms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 专题表 传输实体
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
@ApiModel(value="CmsSubject传输实体", description="专题表传输实体")
public class CmsSubjectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "专题分类id")
    private Long subjectCategoryId;

    @ApiModelProperty(value = "专题标题")
    private String title;

    @ApiModelProperty(value = "专题主图")
    private String pic;

    @ApiModelProperty(value = "关联产品数量")
    private Integer productCount;

    @ApiModelProperty(value = "推荐状态")
    private Integer recommendStatus;

    private LocalDateTime createTime;

    private Integer collectCount;

    private Integer readCount;

    private Integer commentCount;

    @ApiModelProperty(value = "画册图片用逗号分割")
    private String albumPics;

    private String description;

    @ApiModelProperty(value = "显示状态：0->不显示；1->显示")
    private Integer showStatus;

    private String content;

    @ApiModelProperty(value = "转发数")
    private Integer forwardCount;

    @ApiModelProperty(value = "专题热词,聚类出频率最高的前10个词,逗号分割")
    private String hotWords;

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

    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;



}
