package com.msb.dongbao.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.ProductFullReductionDTO;
import com.msb.dongbao.pms.model.entity.ProductFullReduction;

import java.util.List;

/**
 * <p>
 * 商品满减表 服务类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-17
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IProductFullReductionService extends IService<ProductFullReduction> {

  /**
   * 新增商品满减
   * @param dto
   * @return
   */
  ResultWrapper addProductFullReduction(ProductFullReductionDTO dto);

  /**
   * 更新商品满减
   * @param vo
   * @return
   */
  ResultWrapper<String> updateProductFullReduction(ProductFullReductionDTO vo);

  /**
   * 查询商品满减活动详细信息
   * @param id
   * @return ProductFullReductionVO
   * */
  ResultWrapper<List<ProductFullReductionDTO>> detailProductFullReduction(Integer id);

  /**
   * 根据满减活动主键id查询
   * @param id
   * @return ProductFullReduction
   * */
  ProductFullReduction detail (Long id);

}

