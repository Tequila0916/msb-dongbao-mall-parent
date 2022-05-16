package com.msb.dongbao.pms.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.dongbao.pms.model.dto.ProductSearchConditionDTO;
import com.msb.dongbao.pms.model.entity.Specification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 规格 Mapper 接口
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @since 2020-06-17
 */
public interface SpecificationDao extends BaseMapper<Specification> {
    /**
     * 根据商品分类ID查询规格列表
     * @param categoryId
     * @return
     */
    List<ProductSearchConditionDTO> findWithOptionByCategoryId(@Param("categoryId") Long categoryId);
}
