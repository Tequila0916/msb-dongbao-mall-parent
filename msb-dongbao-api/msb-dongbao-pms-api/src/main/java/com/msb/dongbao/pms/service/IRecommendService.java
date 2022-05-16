package com.msb.dongbao.pms.service;

import com.msb.dongbao.pms.model.dto.GoodDTO;
import org.springframework.data.domain.Page;

public interface IRecommendService {

    /**
     * 根据产品ID推荐用户产品
     * @param relProductId
     * @return
     */
    Page<GoodDTO> recommendByCardProductId(Long relProductId);

}
