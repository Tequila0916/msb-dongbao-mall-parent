package com.msb.dongbao.pms.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.dongbao.pms.model.dto.StockUpdateDTO;
import com.msb.dongbao.pms.model.entity.SkuStock;

/**
 * <p>
 * SKU Mapper 接口
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @since 2020-06-21
 */
public interface SkuStockDao extends BaseMapper<SkuStock> {

    int updateStock(StockUpdateDTO stock);

}
