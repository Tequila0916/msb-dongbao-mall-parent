package com.msb.dongbao.pms.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.dongbao.pms.model.dto.HomeRecommendSubjectDTO;
import com.msb.dongbao.pms.model.entity.HomeRecommendSubject;

import java.util.List;

/**
 * <p>
 * 首页推荐专题表 Mapper 接口
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @since 2020-07-20
 */
public interface HomeRecommendSubjectDao extends BaseMapper<HomeRecommendSubject> {

    List<HomeRecommendSubjectDTO> selectSubjectForHome();
}
