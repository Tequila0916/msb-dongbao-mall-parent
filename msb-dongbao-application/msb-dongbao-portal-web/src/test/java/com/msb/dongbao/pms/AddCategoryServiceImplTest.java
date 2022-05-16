package com.msb.dongbao.pms;

import com.google.common.collect.Lists;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.db.dao.CategoryDao;
import com.msb.dongbao.pms.model.dto.CategorySpecDTO;
import com.msb.dongbao.pms.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/20/14:58
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Slf4j
@RunWith(SpringRunner.class)
public class AddCategoryServiceImplTest {

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Mock
    CategoryDao categoryDao;

    @Test
    public void test_success (){
        CategorySpecDTO vo = new CategorySpecDTO();
        vo.setName("yiji");

        CategorySpecDTO secondCate = new CategorySpecDTO();
        secondCate.setName("erji");
        List<CategorySpecDTO> secondCateList = Lists.newArrayList();
        secondCateList.add(secondCate);
        vo.setChildCateLists(secondCateList);

        Mockito.when(categoryDao.insert(Mockito.any())).thenReturn(1);

        ResultWrapper result = categoryService.addCategorySpec(vo);
        assert result.getCode() == 200;
    }
}
