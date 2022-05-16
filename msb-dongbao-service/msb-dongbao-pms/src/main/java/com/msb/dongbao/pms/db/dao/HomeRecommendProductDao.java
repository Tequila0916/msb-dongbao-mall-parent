package com.msb.dongbao.pms.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.dongbao.pms.model.dto.HomeRecommendProductDTO;
import com.msb.dongbao.pms.model.entity.HomeRecommendProduct;

import java.util.List;

/**
 * <p>
 * 人气推荐商品表 Mapper 接口
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @since 2020-07-20
 */
public interface HomeRecommendProductDao extends BaseMapper<HomeRecommendProduct> {

    List<HomeRecommendProductDTO> selectRecommendProductsForHome();
}
