package com.msb.dongbao.pms;

import com.google.common.collect.Lists;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.base.enums.StateCodeEnum;
import com.msb.dongbao.pms.db.dao.ProductDao;
import com.msb.dongbao.pms.db.dao.SkuStockDao;
import com.msb.dongbao.pms.model.dto.ProductSkuDTO;
import com.msb.dongbao.pms.model.dto.SkuStockDTO;
import com.msb.dongbao.pms.service.ISkuStockService;
import com.msb.dongbao.pms.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @date: 2020/6/20/14:57
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@RunWith(SpringRunner.class)
@Slf4j
public class AddProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductDao productDao;

    @Mock
    SkuStockDao skuStockDao;

    @Mock
    ISkuStockService skuStockService;

    @Test
    public void test_success(){
        ProductSkuDTO VO = new ProductSkuDTO();
        VO.setProductName("1q2w3e4r");
        Mockito.when(productDao.insert(Mockito.any())).thenReturn(1);

        ArrayList<SkuStockDTO> skuList = Lists.newArrayList();
        SkuStockDTO skuStock = new SkuStockDTO();
        skuStock.setNum(9999);
        skuStock.setPrice(new BigDecimal(123.00));
        skuStock.setSpec("1,2,3");
        skuStock.setImage("http://...");
        skuStock.setDefaultFlag(true);
        skuList.add(skuStock);
        VO.setSkuList(skuList);
        Mockito.when(skuStockDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(skuStockService.saveBatch(Mockito.any())).thenReturn(true);

        ResultWrapper result = productService.addProduct(VO);
        assert result.getCode() == StateCodeEnum.SUCCESS.getCode();

    }
}
