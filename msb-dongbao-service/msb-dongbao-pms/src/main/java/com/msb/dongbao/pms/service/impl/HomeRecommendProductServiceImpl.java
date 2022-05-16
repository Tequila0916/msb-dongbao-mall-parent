package com.msb.dongbao.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.pms.db.dao.HomeRecommendProductDao;
import com.msb.dongbao.pms.model.dto.HomeRecommendProductDTO;
import com.msb.dongbao.pms.model.entity.HomeRecommendProduct;
import com.msb.dongbao.pms.service.IHomeRecommendProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 人气推荐商品表 服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-20
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
public class HomeRecommendProductServiceImpl extends ServiceImpl<HomeRecommendProductDao, HomeRecommendProduct> implements IHomeRecommendProductService {

    @Autowired
    private HomeRecommendProductDao homeRecommendProductDao;

    @Override
    public List<HomeRecommendProductDTO> selectRecommendProductsForHome(HttpServletRequest request) {
        return homeRecommendProductDao.selectRecommendProductsForHome();
    }
}
