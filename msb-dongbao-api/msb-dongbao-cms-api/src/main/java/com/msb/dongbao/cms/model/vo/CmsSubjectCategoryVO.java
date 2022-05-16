package com.msb.dongbao.cms.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 专题分类表 页面展示实体
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
@ApiModel(value="CmsSubjectCategory页面展示实体", description="专题分类表页面展示实体")
public class CmsSubjectCategoryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    @ApiModelProperty(value = "分类图标")
    private String icon;

    @ApiModelProperty(value = "专题数量")
    private Integer subjectCount;

    private Integer showStatus;

    private Integer sort;

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
