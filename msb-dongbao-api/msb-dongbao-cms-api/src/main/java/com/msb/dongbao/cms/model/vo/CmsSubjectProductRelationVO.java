package com.msb.dongbao.cms.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 专题商品关系表 页面展示实体
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
@ApiModel(value="CmsSubjectProductRelation页面展示实体", description="专题商品关系表页面展示实体")
public class CmsSubjectProductRelationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long subjectId;

    private Long productId;



}
