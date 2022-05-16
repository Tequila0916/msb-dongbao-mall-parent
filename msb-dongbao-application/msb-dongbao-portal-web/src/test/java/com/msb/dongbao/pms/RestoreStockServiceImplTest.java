package com.msb.dongbao.pms;

import com.google.common.collect.Lists;
import com.msb.dongbao.pms.db.dao.SkuStockDao;
import com.msb.dongbao.pms.model.dto.RestoreStockDTO;
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
public class RestoreStockServiceImplTest {

    @InjectMocks
    SkuStockServiceImpl skuStockService;

    @Mock
    private SkuStockDao skuStockDao;

    @Test
    public void test_success(){

        List<RestoreStockDTO> vos = Lists.newArrayList();
        RestoreStockDTO vo = new RestoreStockDTO();
        vo.setId(1l);
        vo.setRestoreNum(2);
        vos.add(vo);

        SkuStock skuStock = new SkuStock();
        skuStock.setSkuNo("1231");
        skuStock.setNum(999);
        skuStock.setVersion(1);

        Mockito.when(skuStockDao.selectById(Mockito.any())).thenReturn(skuStock);
        Mockito.when(skuStockDao.updateStock(Mockito.any())).thenReturn(1);

        Boolean flag = skuStockService.restoreStock(vos);

        assert true == flag;
    }
}
