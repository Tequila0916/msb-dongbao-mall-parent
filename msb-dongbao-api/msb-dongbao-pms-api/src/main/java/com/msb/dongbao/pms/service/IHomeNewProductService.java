package com.msb.dongbao.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.pms.model.dto.HomeNewProductDTO;
import com.msb.dongbao.pms.model.entity.HomeNewProduct;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-20
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IHomeNewProductService extends IService<HomeNewProduct> {

  /**
   * 查询首页的新品推荐商品
   * @param request
   * @return
   */
  List<HomeNewProductDTO> selectNewProducsForHome(HttpServletRequest request);

}
