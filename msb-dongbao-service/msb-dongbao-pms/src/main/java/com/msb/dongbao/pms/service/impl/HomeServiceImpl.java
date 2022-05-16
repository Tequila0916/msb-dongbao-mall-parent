package com.msb.dongbao.pms.service.impl;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.HomeNewProductDTO;
import com.msb.dongbao.pms.model.dto.HomeRecommendProductDTO;
import com.msb.dongbao.pms.model.dto.HomeRecommendSubjectDTO;
import com.msb.dongbao.pms.model.dto.HomeResultDTO;
import com.msb.dongbao.pms.service.IHomeNewProductService;
import com.msb.dongbao.pms.service.IHomeRecommendProductService;
import com.msb.dongbao.pms.service.IHomeRecommendSubjectService;
import com.msb.dongbao.pms.service.IHomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页业务处理类
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年07月21日 10时41分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Slf4j
@Service
public class HomeServiceImpl implements IHomeService {

    @Autowired
    private IHomeNewProductService newProductService;
    @Autowired
    private IHomeRecommendProductService recommendProductService;
    @Autowired
    private IHomeRecommendSubjectService recommendSubjectService;

    @Override
    public ResultWrapper<HomeResultDTO> homeResult(HttpServletRequest request) {
        // TODO 优化，多线程异步优化，线程池 + Future
        // 查询新品推荐商品列表
        List<HomeNewProductDTO> newProducts = newProductService.selectNewProducsForHome(request);
        // 查询人气推荐商品列表
        List<HomeRecommendProductDTO> recommendProducts = recommendProductService.selectRecommendProductsForHome(request);
        // 查询专题广场列表
        List<HomeRecommendSubjectDTO> recommendSubjects = recommendSubjectService.selectSubjectForHome(request);
        // 组装返回数据
        HomeResultDTO homeResult = new HomeResultDTO();
        homeResult.setNewProducts(newProducts);
        homeResult.setRecommendProducts(recommendProducts);
        homeResult.setRecommendSubjects(recommendSubjects);
        return ResultWrapper.getSuccessBuilder().data(homeResult).build();
    }
}
