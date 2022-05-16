package com.msb.dongbao.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.*;
import com.msb.dongbao.pms.model.entity.SkuStock;

import java.util.List;

/**
 * <p>
 * SKU 服务类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface ISkuStockService extends IService<SkuStock> {

  /**
   * 预减库存
   * @param vos
   * @return
   */
  CutStockDTO preCutStock(List<PreCutStockDTO> vos);

  /**
   * 恢复库存
   * @param dtos
   */
  Boolean restoreStock(List<RestoreStockDTO> dtos);

  /**
   * SKU详细信息
   * @param dto
   */
  ResultWrapper detail(SkuDetailDTO dto);

  /**
   *  跟据SKUID查询满减信息
   * @param skuIds
   * @return
   * */
  ResultWrapper<List<ReductionDTO>> reduction(List<SKUReductionDTO> skuIds);
}
