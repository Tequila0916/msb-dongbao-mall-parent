package com.msb.dongbao.pms;

import com.google.common.collect.Lists;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.base.enums.StateCodeEnum;
import com.msb.dongbao.pms.db.dao.SpecificationDao;
import com.msb.dongbao.pms.db.dao.SpecificationOptionDao;
import com.msb.dongbao.pms.model.dto.SpecificationDTO;
import com.msb.dongbao.pms.model.dto.SpecificationOptionDTO;
import com.msb.dongbao.pms.service.impl.SpecificationServiceImpl;
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
 * @date: 2020/6/20/15:01
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RunWith(SpringRunner.class)
@Slf4j
public class AddSpecOptionsServiceImplTest {

    @InjectMocks
    SpecificationServiceImpl specificationService;

    @Mock
    SpecificationDao specificationDao;

    @Mock
    SpecificationOptionDao specificationOptionDao;

    @Test
    public void test_success(){
        List<SpecificationDTO> specList = Lists.newArrayList();
        SpecificationDTO specificationVO = new SpecificationDTO();
        specificationVO.setName("测试规格");
        specList.add(specificationVO);
        List<SpecificationOptionDTO> optionVOS = Lists.newArrayList();
        SpecificationOptionDTO optionVO = new SpecificationOptionDTO();
        optionVO.setName("测试规格选项");
        optionVOS.add(optionVO);
        specificationVO.setOptionDTOS(optionVOS);
        specList.add(specificationVO);

        Mockito.when(specificationDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(specificationOptionDao.insert(Mockito.any())).thenReturn(1);

        ResultWrapper result = specificationService.addCategorySpec(specList);
        assert  result.getCode() == StateCodeEnum.SUCCESS.getCode();
    }
}
