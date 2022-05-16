package com.msb.dongbao.pms;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.db.dao.ProductDao;
import com.msb.dongbao.pms.db.dao.SkuStockDao;
import com.msb.dongbao.pms.db.dao.SpecificationDao;
import com.msb.dongbao.pms.db.dao.SpecificationOptionDao;
import com.msb.dongbao.pms.model.dto.*;
import com.msb.dongbao.pms.model.entity.Product;
import com.msb.dongbao.pms.model.entity.SkuStock;
import com.msb.dongbao.pms.model.entity.Specification;
import com.msb.dongbao.pms.model.entity.SpecificationOption;
import com.msb.dongbao.pms.service.impl.ProductServiceImpl;
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
@RunWith(SpringRunner.class)
@Slf4j
public class ProductDetailServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductDao productDao;

    @Mock
    private SkuStockDao skuStockDao;

    @Mock
    private SpecificationDao specificationDao;

    @Mock
    private SpecificationOptionDao specificationOptionDao;

    @Test
    public void test_success(){

        ProductDTO productDTO = new ProductDTO();

        List<SkuStockDTO> skuList = Lists.newArrayList();
        SkuStockDTO skuStockDTO = new SkuStockDTO();
        skuList.add(skuStockDTO);

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

        List<SpecificationDTO> specList = Lists.newArrayList();
        SpecificationDTO specificationDTO = new SpecificationDTO();
        specList.add(specificationDTO);

        productDTO.setSpecList(specList);
        productDTO.setSkuList(skuList);

        Product product = new Product();
        product.setId(3l);
        product.setSpecOptions("1");
        product.setRelDefaultSkuId(4l);
        product.setProductName("测试产品");

        List<SkuStock> skuStocks = Lists.newArrayList();
        SkuStock skuStock = new SkuStock();
        skuStock.setSkuNo("132131321");
        Gson gson = new Gson();
        String spec = gson.toJson(specTypeList);
        skuStock.setSpec(spec);
        skuStock.setId(4l);
        skuStockDTO.setSkuNo(skuStock.getSkuNo());
        skuStocks.add(skuStock);

        Specification specification = new Specification();
        specification.setName("测试规格");

        List<SpecificationOption> specificationOptions = Lists.newArrayList();
        SpecificationOption specificationOption = new SpecificationOption();
        specificationOption.setName("测试规格选项");
        specificationDTO.setName(specificationOption.getName());
        specificationOptions.add(specificationOption);

        Mockito.when(productDao.selectById(Mockito.any())).thenReturn(product);
        Mockito.when(skuStockDao.selectOne(Mockito.any())).thenReturn(skuStock);
        Mockito.when(specificationDao.selectById(Mockito.any())).thenReturn(specification);
        Mockito.when(specificationOptionDao.selectList(Mockito.any())).thenReturn(specificationOptions);

        productDTO.setProductName(product.getProductName());

        ResultWrapper<ProductDTO> detail = productService.detailProduct(product.getId().intValue());
        assert detail.getData().getProductName().equals(product.getProductName());

    }
}


