package com.msb.dongbao.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.CategoryDTO;
import com.msb.dongbao.pms.model.dto.CategorySpecDTO;
import com.msb.dongbao.pms.model.entity.Category;

import java.util.List;

/**
 * <p>
 * 商品类目 服务类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface ICategoryService extends IService<Category> {

  /**
   * 新增类目/规格/规格项
   * @param dto
   * @return
   */
  ResultWrapper addCategorySpec(CategorySpecDTO dto);

  /**
   * 查询类目列表
   * @param parentNum
   * @param childNum
   * @return
   */
  List<CategoryDTO> listCategory(int parentNum, int childNum);

}
