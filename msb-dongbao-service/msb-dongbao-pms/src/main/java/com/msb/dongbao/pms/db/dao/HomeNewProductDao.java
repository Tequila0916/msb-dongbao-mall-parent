package com.msb.dongbao.pms.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.dongbao.pms.model.dto.HomeNewProductDTO;
import com.msb.dongbao.pms.model.entity.HomeNewProduct;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @since 2020-07-20
 */
public interface HomeNewProductDao extends BaseMapper<HomeNewProduct> {

    List<HomeNewProductDTO> selectNewProductsForHome();
}
