package com.msb.dongbao.pms;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.db.dao.ProductDao;
import com.msb.dongbao.pms.db.dao.SkuStockDao;
import com.msb.dongbao.pms.model.dto.SkuDetailDTO;
import com.msb.dongbao.pms.model.dto.SkuStockDTO;
import com.msb.dongbao.pms.model.dto.SpecOptionNameDTO;
import com.msb.dongbao.pms.model.dto.SpecTypeDTO;
import com.msb.dongbao.pms.model.entity.SkuStock;
import com.msb.dongbao.pms.service.impl.SkuStockServiceImpl;
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
 * @date: 2020/6/21/21:43
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RunWith(SpringRunner.class)
@Slf4j
public class SkuDetailServiceImplTest {

    @InjectMocks
    SkuStockServiceImpl skuStockService;

    @Mock
    private SkuStockDao skuStockDao;

    @Mock
    private ProductDao productDao;

    @Test
    public void test_success(){
        SkuDetailDTO vo = new SkuDetailDTO();
        vo.setOptionIds("8,2,5");
        vo.setProductId(4);

        SkuStock skuStock = new SkuStock();
        SkuStockDTO skuStockDTO = new SkuStockDTO();

        List<SpecTypeDTO> specTypeList = Lists.newArrayList();
        SpecTypeDTO specType = new SpecTypeDTO();
        specType.setSpecType("测试");
        specTypeList.add(specType);

        List<SpecOptionNameDTO> list = Lists.newArrayList();
        SpecOptionNameDTO name = new SpecOptionNameDTO();
        name.setName("颜色");
        name.setValue("黄色");
        list.add(name);
        specType.setList(list);

        skuStockDTO.setSpecTypeList(specTypeList);

        Gson gson = new Gson();
        String spec = gson.toJson(specTypeList);
        skuStock.setSpec(spec);

        Mockito.when(skuStockDao.selectOne(Mockito.any())).thenReturn(skuStock);
        Mockito.when(skuStockDao.selectById(Mockito.any())).thenReturn(skuStock);

        ResultWrapper<SkuStockDTO> detail = skuStockService.detail(vo);

        assert detail.getData().getSpec().equals(spec);
    }

}
