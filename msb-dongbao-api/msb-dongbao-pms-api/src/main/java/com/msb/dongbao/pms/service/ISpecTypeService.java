package com.msb.dongbao.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.entity.SpecType;

/**
 * <p>
 * 规格类别 服务类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-17
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface ISpecTypeService extends IService<SpecType> {

  /**
   * 新增规格类别
   * @param name
   * @return
   */
  ResultWrapper addSpecType(String name);

}
