package com.msb.dongbao.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.base.exception.BusinessException;
import com.msb.dongbao.common.util.BeanUtils;
import com.msb.dongbao.common.util.IDUtils;
import com.msb.dongbao.common.util.SHA256Util;
import com.msb.dongbao.common.util.SortUtils;
import com.msb.dongbao.pms.db.dao.ProductDao;
import com.msb.dongbao.pms.db.dao.SkuStockDao;
import com.msb.dongbao.pms.db.dao.SpecificationDao;
import com.msb.dongbao.pms.db.dao.SpecificationOptionDao;
import com.msb.dongbao.pms.model.dto.*;
import com.msb.dongbao.pms.model.entity.Product;
import com.msb.dongbao.pms.model.entity.SkuStock;
import com.msb.dongbao.pms.model.entity.Specification;
import com.msb.dongbao.pms.model.entity.SpecificationOption;
import com.msb.dongbao.pms.service.IProductService;
import com.msb.dongbao.pms.service.ISkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements IProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private SkuStockDao skuStockDao;

    @Autowired
    private SpecificationDao specificationDao;

    @Autowired
    private SpecificationOptionDao specificationOptionDao;

    @Autowired
    private ISkuStockService skuStockService;

    /**
     * 查询商品详情
     * @param id
     * */
    @Override
    public ResultWrapper<ProductDTO> detailProduct(Integer id){
        log.info("查询商品详情  入参:【{}】",id);
        ArrayList<SkuStockDTO> skuList = Lists.newArrayList();
        ArrayList<SpecificationDTO> specList = Lists.newArrayList();
        ProductDTO productDTO = new ProductDTO();

        // 查询商品
        Product product = productDao.selectById(id);
        if(product == null){
            throw new BusinessException("该商品不存在或已下架");
        }
        BeanCopier copier = BeanCopier.create(Product.class,ProductDTO.class,false);
        copier.copy(product,productDTO,null);

        // 查询默认的SKU商品
        LambdaQueryWrapper<SkuStock> queryWrapper = new QueryWrapper<SkuStock>().lambda().eq(SkuStock::getRelProductId, id)
                .eq(SkuStock::getId, product.getRelDefaultSkuId());
        SkuStock skuStock = skuStockDao.selectOne(queryWrapper);
        if(skuStock == null){
            throw new BusinessException("该商品缺少SKU库存");
        }
        SkuStockDTO skuStockDTO = new SkuStockDTO();
        copier = BeanCopier.create(SkuStock.class,SkuStockDTO.class,false);
        copier.copy(skuStock,skuStockDTO,null);

        // 为查询默认SKU对应的规格做准备
        String optionIds = skuStockDTO.getOptionIds();
        List<String> skuOptionIds = Lists.newArrayList();
        if(StringUtils.isNotEmpty(optionIds)){
            skuOptionIds = Arrays.asList(optionIds.split(","));
        }

        // 转换spec数据
        Gson gson = new Gson();
        List<SpecTypeDTO> list = gson.fromJson(skuStock.getSpec(), List.class);
        skuStockDTO.setSpecTypeList(list);
        skuStockDTO.setDefaultFlag(skuStock.getId().equals(product.getRelDefaultSkuId()) ? true : false);
        skuList.add(skuStockDTO);
        productDTO.setSkuList(skuList);

        // 查询该商品的规格信息
        String specOptions = product.getSpecOptions();
        String[] split = specOptions.split(",");

        for (String specId : split) {
            Specification spec = specificationDao.selectById(specId);
            SpecificationDTO specificationDTO = new SpecificationDTO();
            copier = BeanCopier.create(Specification.class,SpecificationDTO.class,false);
            copier.copy(spec,specificationDTO,null);
            specList.add(specificationDTO);

            // 查询规格选项
            ArrayList<SpecificationOptionDTO> optionDTOS = Lists.newArrayList();
            List<SpecificationOption> optionList = specificationOptionDao.selectList(new QueryWrapper<SpecificationOption>().lambda().eq(SpecificationOption::getRelSpecId, specId));
            for (SpecificationOption specificationOption :optionList) {
                SpecificationOptionDTO optionDTO = new SpecificationOptionDTO();
                BeanCopier copierTemp = BeanCopier.create(SpecificationOption.class,SpecificationOptionDTO.class,false);
                copierTemp.copy(specificationOption,optionDTO,null);
                // 判断是否被默认选中
                if(skuOptionIds.contains(specificationOption.getId()+"")){
                    optionDTO.setActive(true);
                }
                optionDTOS.add(optionDTO);
            }
            specificationDTO.setOptionDTOS(optionDTOS);
        }
        productDTO.setSpecList(specList);
        return ResultWrapper.getSuccessBuilder().data(productDTO).build();

    }

    /**
     * 新增商品/SKU
     * @param dto
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultWrapper addProduct(ProductSkuDTO dto) {
        log.info("新增商品/SKU  入参:【{}】",new Gson().toJson(dto));

        Product product = new Product();
        BeanCopier copier = BeanCopier.create(ProductSkuDTO.class, Product.class, false);
        copier.copy(dto,product,null);

        product.setProductNo(IDUtils.UUID());
        product.setEnabled(false);
        product.setCreateUid(dto.getRelTenantId()+"");
        product.setGmtCreate(System.currentTimeMillis());
        productDao.insert(product);

        ArrayList<SkuStock> list = Lists.newArrayList();
        for (SkuStockDTO skuStockDTO : dto.getSkuList()) {
            SkuStock sku = new SkuStock();

            BeanCopier copy = BeanCopier.create(SkuStockDTO.class, SkuStock.class, false);
            copy.copy(skuStockDTO,sku,null);

            String sort = SortUtils.sort(skuStockDTO.getOptionIds());
            sku.setOptionIds(sort);
            sku.setRelProductId(product.getId());
            sku.setVersion(0);
            sku.setLockStockNum(0);
            sku.setSkuNo(IDUtils.UUID());
            Gson gson = new Gson();
            String spec = gson.toJson(skuStockDTO.getSpecTypeList());
            sku.setSpec(spec);
            sku.setSpecHash(SHA256Util.getHash(sku.getSpec()));
            sku.setEnabled(false);
            sku.setCreateUid(dto.getRelTenantId()+"");
            sku.setGmtCreate(System.currentTimeMillis());

            if (skuStockDTO.getDefaultFlag()){
                skuStockDao.insert(sku);

                product.setRelDefaultSkuId(sku.getId());
                product.setPrice(sku.getPrice());
                product.setDefaultPic(sku.getImage());
                productDao.updateById(product);
            }else {
                list.add(sku);
            }
        }

        if (list != null && !list.isEmpty()){
            skuStockService.saveBatch(list);
            return ResultWrapper.getSuccessBuilder().msg("新增商品/SKU成功").build();
        }
        return ResultWrapper.getErrorBuilder().msg("新增商品/SKU失败").build();
    }

    @Override
    public List<ProductDTO> findMerchantHotsales(Long merchantId) {
        LambdaQueryWrapper<Product> queryWrapper = new QueryWrapper<Product>().lambda().eq(Product::getRelTenantId, merchantId)
                .eq(Product::getEnabled, 0)
                .eq(Product::getPublishStatus, 1)
                .orderByDesc(Product::getSalesNum).last("limit 5");
        List<ProductDTO> result = Lists.newArrayList();
        Optional.of(this.list(queryWrapper)).ifPresent(list->{
            list.stream().forEach( e -> {
                ProductDTO productDTO = new ProductDTO();
                BeanUtils.copyBean(e,productDTO);
                
                result.add(productDTO);
            });
        });
        return result;
    }

}
