package com.msb.dongbao.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.ProductDTO;
import com.msb.dongbao.pms.model.dto.ProductSkuDTO;
import com.msb.dongbao.pms.model.entity.Product;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-17
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IProductService extends IService<Product> {

  /**
   * 查询商品详情
   * @param id
   * */
  ResultWrapper<ProductDTO> detailProduct(Integer id);

  /**
   * 新增商品/SKU
   * @param dto
   * @return
   * */
  ResultWrapper addProduct(ProductSkuDTO dto);

  /**
   * 查询店铺热销商品，取 5个
   * @param merchantId
   * @return
   */
  List<ProductDTO> findMerchantHotsales(Long merchantId);

}
