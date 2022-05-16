package com.msb.dongbao.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.dongbao.pms.db.dao.HomeRecommendSubjectDao;
import com.msb.dongbao.pms.model.dto.HomeRecommendSubjectDTO;
import com.msb.dongbao.pms.model.entity.HomeRecommendSubject;
import com.msb.dongbao.pms.service.IHomeRecommendSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 首页推荐专题表 服务实现类
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
public class HomeRecommendSubjectServiceImpl extends ServiceImpl<HomeRecommendSubjectDao, HomeRecommendSubject> implements IHomeRecommendSubjectService {

    @Autowired
    private HomeRecommendSubjectDao homeRecommendSubjectDao;

    @Override
    public List<HomeRecommendSubjectDTO> selectSubjectForHome(HttpServletRequest request) {
        return homeRecommendSubjectDao.selectSubjectForHome();
    }
}
