package com.msb.dongbao.pms.service;

import com.msb.dongbao.pms.model.dto.GoodDTO;
import com.msb.dongbao.pms.model.dto.ProductSearchConditionDTO;
import com.msb.dongbao.pms.model.dto.ProductSearchESDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IGoodsESService {


    /**
     * 根据产品名搜索ES
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<GoodDTO>pageQueryByName(String name, Integer pageNo, Integer pageSize);

    /**
     * 查询分类条件
     * @param searchESDTO 查询条件对象
     * @return
     */
    List<ProductSearchConditionDTO> findSearchCondition(ProductSearchESDTO searchESDTO) throws NoSuchFieldException;

    /**
     * 根据字段条件组合查询
     * @param productSearchESDTO
     * @return
     */
    Page<GoodDTO> pageQueryByCondition(ProductSearchESDTO productSearchESDTO);

    /**
     * 根据字段条件组合查询
     * @param productSearchESDTO
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<GoodDTO> pageQueryByCondition(ProductSearchESDTO productSearchESDTO, Integer pageNo, Integer pageSize);
}
