package com.msb.dongbao.pms;

import com.google.common.collect.Lists;
import com.msb.dongbao.pms.db.dao.ProductDao;
import com.msb.dongbao.pms.db.dao.SkuStockDao;
import com.msb.dongbao.pms.model.dto.CutStockDTO;
import com.msb.dongbao.pms.model.dto.PreCutStockDTO;
import com.msb.dongbao.pms.model.entity.Product;
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
 * @date: 2020/6/20/15:00
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RunWith(SpringRunner.class)
@Slf4j
public class PreCutStockServiceImplTest {

    @InjectMocks
    SkuStockServiceImpl skuStockService;

    @Mock
    private SkuStockDao skuStockDao;

    @Mock
    private ProductDao productDao;


    @Test
    public void test_success(){
        List<PreCutStockDTO> vos = Lists.newArrayList();
        PreCutStockDTO preCutStockVO = new PreCutStockDTO();
        preCutStockVO.setId(3l);
        preCutStockVO.setPreCutNum(2);
        vos.add(preCutStockVO);

        SkuStock skuStock = new SkuStock();
        skuStock.setSkuNo("1231");
        skuStock.setNum(999);
        skuStock.setVersion(1);

        Product product = new Product();
        product.setProductName("测试产品");

        Mockito.when(skuStockDao.selectById(Mockito.any())).thenReturn(skuStock);
        Mockito.when(skuStockDao.updateStock(Mockito.any())).thenReturn(1);
        Mockito.when(productDao.selectById(Mockito.any())).thenReturn(product);

        CutStockDTO cutStockDTO = skuStockService.preCutStock(vos);

        assert true == cutStockDTO.isFlag();
    }
}
