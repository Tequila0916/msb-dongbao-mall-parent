package com.msb.dongbao.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.dongbao.pms.model.dto.HomeRecommendSubjectDTO;
import com.msb.dongbao.pms.model.entity.HomeRecommendSubject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 首页推荐专题表 服务类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-20
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public interface IHomeRecommendSubjectService extends IService<HomeRecommendSubject> {

  /**
   * 查询首页专题广场商品列表
   * @param request
   * @return
   */
  List<HomeRecommendSubjectDTO> selectSubjectForHome(HttpServletRequest request);
}
