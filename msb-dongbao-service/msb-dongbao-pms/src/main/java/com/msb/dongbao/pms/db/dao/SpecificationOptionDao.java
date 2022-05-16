package com.msb.dongbao.pms.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.dongbao.pms.model.dto.SpecificationOptionDTO;
import com.msb.dongbao.pms.model.entity.SpecificationOption;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 规格项 Mapper 接口
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @since 2020-06-08
 */
public interface SpecificationOptionDao extends BaseMapper<SpecificationOption> {
    SpecificationOptionDTO findListBySpecId(@Param("categoryId") Long specId);

}
