package com.msb.dongbao.pms.service.impl;

import com.msb.dongbao.pms.model.dto.GoodDTO;
import com.msb.dongbao.pms.model.dto.ProductSearchESDTO;
import com.msb.dongbao.pms.service.IGoodsESService;
import com.msb.dongbao.pms.service.IRecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RecommendService implements IRecommendService {

    @Autowired
    private IGoodsESService goodsESService;

    @Override
    public Page<GoodDTO> recommendByCardProductId(Long relProductId) {
        ProductSearchESDTO productSearchESDTO = new ProductSearchESDTO();
        productSearchESDTO.setId(relProductId);
        Page<GoodDTO> goodDTOS = goodsESService.pageQueryByCondition(productSearchESDTO);
        List<GoodDTO> content = goodDTOS.getContent();
        GoodDTO goodDTO = content.get(content.size() - 1);

        ProductSearchESDTO recommend = new ProductSearchESDTO();
        recommend.setRelCategory1Id(goodDTO.getRelCategory1Id());
        recommend.setRelCategory2Id(goodDTO.getRelCategory2Id());
        recommend.setRelCategory3Id(goodDTO.getRelCategory3Id());
        recommend.setSortField("salesNum");
        recommend.setSortDesc(true);
        return goodsESService.pageQueryByCondition(recommend);
    }
}
