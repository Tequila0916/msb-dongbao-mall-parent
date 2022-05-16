package com.msb.dongbao.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.base.exception.BusinessException;
import com.msb.dongbao.pms.db.dao.ProductDao;
import com.msb.dongbao.pms.db.dao.ProductFullReductionDao;
import com.msb.dongbao.pms.model.dto.ProductFullReductionDTO;
import com.msb.dongbao.pms.model.entity.Product;
import com.msb.dongbao.pms.model.entity.ProductFullReduction;
import com.msb.dongbao.pms.service.IProductFullReductionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.msb.dongbao.common.base.enums.ErrorCodeEnum.PMS0001603;

/**
 * <p>
 * 商品满减表 服务实现类
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-17
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Service
@Slf4j
public class ProductFullReductionServiceImpl extends ServiceImpl<ProductFullReductionDao, ProductFullReduction> implements IProductFullReductionService {

    @Autowired
    private ProductFullReductionDao fullDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public ResultWrapper<String> addProductFullReduction(ProductFullReductionDTO dto) {
        log.info("新增商品满减信息：商品编号：【{}】，入参信息：【{}】", dto.getProductNo(), new Gson().toJson(dto));
        ProductFullReduction fullReduction = new ProductFullReduction();
        BeanCopier copier = BeanCopier.create(ProductFullReductionDTO.class, ProductFullReduction.class, false);
        copier.copy(dto, fullReduction, null);
        Product product = productDao.selectOne(new QueryWrapper<Product>().lambda().eq(Product::getProductNo, dto.getProductNo()));
        if (product == null) {
            throw new BusinessException(PMS0001603);
        }

        fullReduction.setProductId(product.getId());
        fullReduction.setGmtCreate(System.currentTimeMillis());
        fullDao.insert(fullReduction);

        return ResultWrapper.getSuccessBuilder().msg("新增商品满减信息成功").build();
    }

    @Override
    public ResultWrapper<String> updateProductFullReduction(ProductFullReductionDTO dto) {
        log.info("更新商品满减信息：入参信息：【{}】", new Gson().toJson(dto));
        ProductFullReduction fullReduction = new ProductFullReduction();
        BeanCopier copier = BeanCopier.create(ProductFullReductionDTO.class, ProductFullReduction.class, false);
        copier.copy(dto, fullReduction, null);
        fullReduction.setGmtModified(System.currentTimeMillis());
        fullDao.updateById(fullReduction);
        return ResultWrapper.getSuccessBuilder().data("更新商品满减信息成功").build();
    }

    @Override
    public ResultWrapper<List<ProductFullReductionDTO>> detailProductFullReduction(Integer id) {
        ArrayList<ProductFullReductionDTO> list = Lists.newArrayList();
        // 查询所有
        List<ProductFullReduction> reductions = fullDao.selectList(new QueryWrapper<ProductFullReduction>().lambda().eq(ProductFullReduction::getProductId, id));
        if (reductions == null || reductions.isEmpty()){
            return ResultWrapper.getSuccessBuilder().build();
        }
        // 过滤可使用满减活动
        long current = System.currentTimeMillis();
        List<ProductFullReduction> collect = reductions.stream().filter(reduction -> ((reduction.getStartTime() <= current) && (reduction.getEndTime() >= current))).collect(Collectors.toList());
        if (collect == null || collect.isEmpty()){
            return ResultWrapper.getSuccessBuilder().build();
        }
        collect.stream().forEach(reduction  -> {
            ProductFullReductionDTO dto = new ProductFullReductionDTO();
            BeanCopier copier = BeanCopier.create(ProductFullReduction.class, ProductFullReductionDTO.class, false);
            copier.copy(reduction,dto,null);
            list.add(dto);
        });
        return ResultWrapper.getSuccessBuilder().data(list).build();
    }

    @Override
    public ProductFullReduction detail(Long id) {
        return fullDao.selectById(id);
    }

}
