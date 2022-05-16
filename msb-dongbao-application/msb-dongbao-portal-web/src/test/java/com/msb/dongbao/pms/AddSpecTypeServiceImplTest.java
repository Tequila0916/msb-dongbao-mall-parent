package com.msb.dongbao.pms;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.base.enums.StateCodeEnum;
import com.msb.dongbao.pms.db.dao.SpecTypeDao;
import com.msb.dongbao.pms.service.impl.SpecTypeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/20/15:00
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RunWith(SpringRunner.class)
@Slf4j
public class AddSpecTypeServiceImplTest {

    @InjectMocks
    SpecTypeServiceImpl specTypeService;

    @Mock
    SpecTypeDao specTypeDao;

    @Test
    public void test_success(){
        String name = "测试规格分类";

        Mockito.when(specTypeDao.insert(Mockito.any())).thenReturn(1);
        ResultWrapper<String> result = specTypeService.addSpecType(name);
        assert result.getCode() == StateCodeEnum.SUCCESS.getCode();
    }
}
