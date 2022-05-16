package com.msb.dongbao.portal.controller.pms;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.ProductFullReductionDTO;
import com.msb.dongbao.pms.service.IProductFullReductionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 商品满减表 前端控制器
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-17
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "商品满减表 控制器", tags = "商品满减表 控制器")
@RestController
@RequestMapping("/pms/productFullReduction")
@Validated
public class ProductFullReductionController {

    @Autowired
    private IProductFullReductionService productFullReductionService;

    @ApiOperation(value = "添加商品满减表")
    @PostMapping("/add")
    public ResultWrapper addProductFullReduction(@RequestBody ProductFullReductionDTO dto) {
        return productFullReductionService.addProductFullReduction(dto);
    }

    @ApiOperation(value = "更新商品满减表")
    @PostMapping("/update")
    public ResultWrapper updateProductFullReduction(@RequestBody ProductFullReductionDTO dto) {
        return productFullReductionService.updateProductFullReduction(dto);
    }

    @ApiOperation(value = "根据SPU_ID查询商品满减信息")
    @ApiParam(name = "商品ID", value = "商品ID", required = true)
    @GetMapping("/detail/{id}")
    public ResultWrapper<List<ProductFullReductionDTO>> detailProductFullReduction(@NotNull(message = "商品ID不能为空") @PathVariable("id") Integer id) {
        return productFullReductionService.detailProductFullReduction(id);
    }
}
