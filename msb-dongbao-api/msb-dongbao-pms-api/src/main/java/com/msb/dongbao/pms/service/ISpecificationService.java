package com.msb.dongbao.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.SpecificationDTO;
import com.msb.dongbao.pms.model.entity.Specification;

import java.util.List;

/**
 * <p>
 * 规格 服务类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface ISpecificationService extends IService<Specification> {

    /**
     * 新增类目/规格/规格项
     * @param specList
     */
    ResultWrapper addCategorySpec(List<SpecificationDTO> specList);
}
