package com.msb.dongbao.pms;

import com.msb.dongbao.pms.db.dao.CategoryDao;
import com.msb.dongbao.pms.model.dto.CategoryDTO;
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
 * @date: 2020/6/20/14:59
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RunWith(SpringRunner.class)
@Slf4j
public class CategoryListServiceImplTest {

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Mock
    CategoryDao categoryDao;

    @Test
    public void test_success(){

        Mockito.when(categoryDao.selectList(Mockito.any())).thenReturn(null);
        List<CategoryDTO> categoryDTOS = categoryService.listCategory(10, 2);
        assert categoryDTOS == null;
    }
}
