package com.msb.dongbao.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.common.util.SortUtils;
import com.msb.dongbao.pms.db.dao.ProductDao;
import com.msb.dongbao.pms.db.dao.ProductFullReductionDao;
import com.msb.dongbao.pms.db.dao.SkuStockDao;
import com.msb.dongbao.pms.model.dto.*;
import com.msb.dongbao.pms.model.entity.Product;
import com.msb.dongbao.pms.model.entity.ProductFullReduction;
import com.msb.dongbao.pms.model.entity.SkuStock;
import com.msb.dongbao.pms.service.ISkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

;

/**
 * <p>
 * SKU 服务实现类
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
public class SkuStockServiceImpl extends ServiceImpl<SkuStockDao, SkuStock> implements ISkuStockService {

    @Autowired
    private SkuStockDao skuStockDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductFullReductionDao fullReductionDao;

    /**
     * 预减库存
     * @param dtos
     */
    @Override
    @Transactional
    public CutStockDTO preCutStock(List<PreCutStockDTO> dtos) {
        log.info("预减库存  入参:【{}】",new Gson().toJson(dtos));

        ArrayList<SkuStockDTO> successList = Lists.newArrayList();
        ArrayList<SkuStockDTO> failList = Lists.newArrayList();
        CutStockDTO cutStockDTO = new CutStockDTO();

        for (PreCutStockDTO dto : dtos) {
            SkuStockDTO skuStockDTO = cutSku(dto);
            if (skuStockDTO.getPreCut()){
                successList.add(skuStockDTO);
            }else {
                failList.add(skuStockDTO);
            }
        }

        if (failList != null && !failList.isEmpty()){
            cutStockDTO.setFlag(false);
        }else {
            cutStockDTO.setFlag(true);
        }
        cutStockDTO.setFailList(failList);
        cutStockDTO.setSuccessList(successList);


        ArrayList<SKUReductionDTO> skuReductionDTOS = Lists.newArrayList();
        for (PreCutStockDTO preCutStockDTO : dtos) {
            for (SkuStockDTO skuStockDTO : successList) {
                if (preCutStockDTO.getId().equals(skuStockDTO.getId())){
                    SKUReductionDTO skuReductionDTO = new SKUReductionDTO();
                    skuReductionDTO.setNum(preCutStockDTO.getPreCutNum());
                    skuReductionDTO.setSkuId(preCutStockDTO.getId());
                    skuReductionDTOS.add(skuReductionDTO);
                }
            }
        }
        List<ReductionDTO> reductionDTOS = reduction(skuReductionDTOS).getData();
        for (ReductionDTO reductionDTO : reductionDTOS) {
            for (SkuStockDTO skuStockDTO : successList) {
                if (reductionDTO.getSkuId().equals(skuStockDTO.getId())){
                    skuStockDTO.setReductionId(reductionDTO.getReductionId());
                    skuStockDTO.setDiscountPrice(reductionDTO.getDiscountPrice());
                    skuStockDTO.setReducePrice(reductionDTO.getReducePrice());
                }
            }
        }
        return cutStockDTO;
    }

    private SkuStockDTO cutSku(PreCutStockDTO dto){

        SkuStockDTO skuStockDTO = new SkuStockDTO();

        for (int i = 0; i < 3; i++) {
            SkuStock sku = skuStockDao.selectById(dto.getId());
            Product product = productDao.selectById(sku.getRelProductId());
            BeanCopier copier = BeanCopier.create(SkuStock.class, SkuStockDTO.class, false);
            copier.copy(sku,skuStockDTO,null);

            skuStockDTO.setProductName(product.getProductName());
            skuStockDTO.setRelCategory1Id(product.getRelCategory1Id());
            skuStockDTO.setRelCategory2Id(product.getRelCategory2Id());
            skuStockDTO.setRelCategory3Id(product.getRelCategory3Id());

            if (dto.getPreCutNum() > sku.getNum()){
                skuStockDTO.setMsg("库存不足,检查下单数量");
                skuStockDTO.setPreCut(false);
                return skuStockDTO;
            }

            StockUpdateDTO stock = new StockUpdateDTO();
            stock.setId(sku.getId());
            stock.setNewNum(sku.getNum()-dto.getPreCutNum());
            stock.setNewVersion(sku.getVersion()+1);
            stock.setVersion(sku.getVersion());
            int updateStock = skuStockDao.updateStock(stock);
            log.info("预减库存  update:【{}】",updateStock);

            if (updateStock > 0){
                skuStockDTO.setNum(stock.getNewNum());
                skuStockDTO.setMsg("库存充足,可以下单");
                skuStockDTO.setPreCut(true);
                return skuStockDTO;
            }else {
                skuStockDTO.setMsg("预减库存失败");
                skuStockDTO.setPreCut(false);
            }
        }
        return skuStockDTO;
    }

    /**
     * 恢复库存
     * @param vos
     */
    @Override
    public Boolean restoreStock(List<RestoreStockDTO> vos) {
        log.info("恢复库存  入参:【{}】",new Gson().toJson(vos));

        ArrayList<StockDTO> successList = Lists.newArrayList();
        ArrayList<StockDTO> failList = Lists.newArrayList();

        for (RestoreStockDTO vo : vos) {
            StockDTO stockVO = restoreSku(vo);
            if (stockVO.getFlag()){
                successList.add(stockVO);
            }else {
                failList.add(stockVO);
            }
        }
        if (failList != null && !failList.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    /**
     * SKU详细信息
     * @param dto
     */
    @Override
    public ResultWrapper detail(SkuDetailDTO dto) {
        String sort = SortUtils.sort(dto.getOptionIds());
        QueryWrapper<SkuStock> wrapper = new QueryWrapper<>();
        SkuStock skuStock = skuStockDao.selectOne(wrapper.lambda()
                .eq(SkuStock::getRelProductId, dto.getProductId()).eq(SkuStock::getOptionIds, sort));

        if (skuStock != null){
            SkuStockDTO skuStockDTO = new SkuStockDTO();
            BeanCopier copier = BeanCopier.create(SkuStock.class,SkuStockDTO.class,false);
            copier.copy(skuStock,skuStockDTO,null);

            Gson gson = new Gson();
            List<SpecTypeDTO> list = gson.fromJson(skuStock.getSpec(), List.class);
            skuStockDTO.setSpecTypeList(list);
            return ResultWrapper.getSuccessBuilder().data(skuStockDTO).build();
        }else {
            SkuStock tempSkuStock = new SkuStock();
            tempSkuStock.setId(-1L);
            tempSkuStock.setNum(0);
            // 规格组合对应的无库存商品数据
            return ResultWrapper.getSuccessBuilder().data(tempSkuStock).build();
        }

    }

    private StockDTO restoreSku(RestoreStockDTO vo){

        StockDTO stockVO = new StockDTO();
        for (int i = 0; i < 5; i++) {

            SkuStock skuStock = skuStockDao.selectById(vo.getId());

            StockUpdateDTO stock = new StockUpdateDTO();
            stock.setId(skuStock.getId());
            stock.setNewNum(skuStock.getNum()+vo.getRestoreNum());
            stock.setNewVersion(skuStock.getVersion()+1);
            stock.setVersion(skuStock.getVersion());
            int updateStock = skuStockDao.updateStock(stock);
            log.info("恢复库存  update:【{}】",updateStock);

            if (updateStock > 0 ) {
                stockVO.setFlag(true);
                stockVO.setOriginNum(skuStock.getNum());
                stockVO.setRestoreNum(vo.getRestoreNum()+skuStock.getNum());
                return stockVO;
            }else {
                stockVO.setFlag(false);
            }
        }
        return stockVO;
    }

    @Override
    public ResultWrapper<List<ReductionDTO>> reduction(List<SKUReductionDTO> skuIds) {
        log.info("根据SKUID查询满减信息  入参:{}",new Gson().toJson(skuIds));
        ArrayList<ReductionDTO> reductionDTOS = Lists.newArrayList();
        ArrayList<SkuStock> skus = Lists.newArrayList();
        Map<Long, SKUReductionDTO> map = new HashMap<>();
        for (SKUReductionDTO skuReductionVO : skuIds) {
            SkuStock skuStock = skuStockDao.selectById(skuReductionVO.getSkuId());
            skus.add(skuStock);
            map.put(skuStock.getId(), skuReductionVO);
        }
        Map<Long, List<SkuStock>> collect = skus.stream().collect(Collectors.groupingBy(SkuStock::getRelProductId));
        Set<Long> spuIds = collect.keySet();
        for (Long spuId : spuIds) {

            ProductFullReduction fullReduction = fullReductionDao.selectOne(new QueryWrapper<ProductFullReduction>().lambda().eq(ProductFullReduction::getProductId, spuId));
            List<SkuStock> skuStocks = collect.get(spuId);
            // 计算平均价格
            if (fullReduction == null) {
                reductionDTOS = addReduction(reductionDTOS, skuStocks, null,null);
            }else {
                BigDecimal spuPrice = new BigDecimal("0");
                Integer totalNum = 0;
                for (SkuStock skuStock : skuStocks) {
                    for (Long skuId : map.keySet()) {
                        if (skuStock.getId().equals(skuId)) {
                            SKUReductionDTO dto = map.get(skuId);
                            BigDecimal skuPrice = skuStock.getPrice().multiply(new BigDecimal(dto.getNum()));
                            spuPrice = spuPrice.add(skuPrice);
                            totalNum += dto.getNum();
                        }
                    }
                }
                long currentTimeMillis = System.currentTimeMillis();
                boolean b = currentTimeMillis >= fullReduction.getStartTime() && currentTimeMillis <= fullReduction.getEndTime();
                if (spuPrice.compareTo(fullReduction.getFullPrice()) != -1 && b){
                    // >=才可以用
                    for (SkuStock skuStock : skuStocks) {
                        for (Long skuId : map.keySet()) {
                            if (skuStock.getId().equals(skuId)) {
                                SKUReductionDTO dto = map.get(skuId);
                                ReductionDTO reductionDTO = new ReductionDTO();
                                BigDecimal skuPrice = skuStock.getPrice().multiply(new BigDecimal(dto.getNum()));
                                BigDecimal avgPrice = fullReduction.getReducePrice().multiply(skuPrice.divide(spuPrice,BigDecimal.ROUND_HALF_UP));
                                reductionDTO.setDiscountPrice(skuStock.getPrice().subtract(avgPrice.divide(new BigDecimal(dto.getNum()),BigDecimal.ROUND_HALF_UP)));
                                reductionDTO.setOriginPrice(skuStock.getPrice());
                                reductionDTO.setReductionId(fullReduction.getId());
                                reductionDTO.setReducePrice(fullReduction.getReducePrice());
                                reductionDTO.setSkuId(skuStock.getId());
                                reductionDTOS.add(reductionDTO);
                            }
                        }
                    }
                }else {
                    reductionDTOS = addReduction(reductionDTOS, skuStocks, fullReduction.getId(),fullReduction.getReducePrice());
                }
            }
        }
        return ResultWrapper.getSuccessBuilder().data(reductionDTOS).build();
    }

    private ArrayList<ReductionDTO> addReduction(ArrayList<ReductionDTO> reductionDTOS, List<SkuStock> skuStocks, Long fullReductionId,BigDecimal reducePrice){
        for (SkuStock skuStock : skuStocks) {
            ReductionDTO reductionVO = new ReductionDTO();
            reductionVO.setOriginPrice(skuStock.getPrice());
            reductionVO.setReductionId(fullReductionId);
            reductionVO.setSkuId(skuStock.getId());
            reductionVO.setReducePrice(reducePrice);
            reductionVO.setDiscountPrice(skuStock.getPrice());
            reductionDTOS.add(reductionVO);
        }
        return reductionDTOS;
    }
}
