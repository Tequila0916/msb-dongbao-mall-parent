package com.msb.dongbao.portal.controller.pms;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.*;
import com.msb.dongbao.pms.service.ISkuStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * SKU 前端控制器
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "SKU 控制器", tags = "SKU 控制器")
@RestController
@RequestMapping("/pms/skuStock")
@Validated
public class SkuStockController {

    @Autowired
    private ISkuStockService skuStockService;

    @ApiOperation(value = "预减库存")
    @PostMapping("/preCutStock")
    public CutStockDTO preCutStock(@RequestBody List<PreCutStockDTO> dtos) {
        return skuStockService.preCutStock(dtos);
    }

    @ApiOperation(value = "恢复库存")
    @PostMapping("/restoreStock")
    public Boolean restoreStock(@RequestBody List<RestoreStockDTO> dtos) {
        return skuStockService.restoreStock(dtos);
    }

    @ApiOperation(value = "SKU详细信息")
    @PostMapping("/detail")
    public ResultWrapper<SkuStockDTO> detail(@RequestBody SkuDetailDTO dto) {
        return skuStockService.detail(dto);
    }

    @ApiOperation(value = "跟据SKUID查询满减信息")
    @PostMapping("/detailSKU")
    public ResultWrapper<List<ReductionDTO>> reduction(@RequestBody List<SKUReductionDTO> dtos) {
        return skuStockService.reduction(dtos);
    }

}
