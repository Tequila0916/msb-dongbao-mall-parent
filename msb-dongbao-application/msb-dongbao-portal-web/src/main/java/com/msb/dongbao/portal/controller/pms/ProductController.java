package com.msb.dongbao.portal.controller.pms;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.*;
import com.msb.dongbao.pms.service.IGoodsESService;
import com.msb.dongbao.pms.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "商品表 控制器", tags = "商品表 控制器")
@RestController
@RequestMapping("/pms/product")
@Validated
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IGoodsESService goodsESService;


    @ApiOperation(value = "新增商品/SKU")
    @PostMapping("/addProduct")
    public ResultWrapper addProduct(@Validated @RequestBody ProductSkuDTO dto){
        return productService.addProduct(dto);
    }


    @ApiOperation(value = "查询商品详情")
    @ApiParam(name = "商品表ID",value = "商品表ID",required = true)
    @GetMapping("/detail")
    public ResultWrapper<ProductDTO> detailProduct(@NotNull(message = "商品表ID不能为空") @RequestParam(value = "id",required = false) Integer id){
        return productService.detailProduct(id);
    }


    @ApiOperation(value = "根据名称标题等查询商品列表")
    @PostMapping("/findByName")
    public ResultWrapper<Page<GoodDTO>> findByName(@RequestParam(value = "name", defaultValue = "手机") String name,
                                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                        @RequestParam(value = "size", defaultValue = "20") Integer size) {
        Page<GoodDTO> goodDTOS = goodsESService.pageQueryByName(name, page, size);
        return ResultWrapper.getSuccessBuilder().data(goodDTOS).build();
    }

    @ApiOperation(value = "根据名称标题等查询商品列表")
    @PostMapping("/findByESCondition")
    public ResultWrapper<Page<GoodDTO>> findByESCondition(@RequestBody ProductSearchESDTO productSearchESDTO,
                                                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(value = "size", defaultValue = "20") Integer size) {
        Page<GoodDTO> goodDTOS = goodsESService.pageQueryByCondition(productSearchESDTO, page, size);
        return ResultWrapper.getSuccessBuilder().data(goodDTOS).build();
    }


    @ApiOperation(value = "查询检索条件")
    @PostMapping("/findSearchCondition")
    public ResultWrapper<List<ProductSearchConditionDTO>> findSearchCondition(@RequestBody ProductSearchESDTO searchESDTO) throws NoSuchFieldException {
        List<ProductSearchConditionDTO> searchCondition = goodsESService.findSearchCondition(searchESDTO);
        return ResultWrapper.getSuccessBuilder().data(searchCondition).build();
    }

    @ApiOperation(value = "店铺热销商品")
    @GetMapping("/hotsales/merchant/{merchantId}")
    public ResultWrapper<List<ProductDTO>> merchantHotsales(@PathVariable("merchantId") Long merchantId) throws NoSuchFieldException {
        List<ProductDTO> merchantHotsales = productService.findMerchantHotsales(merchantId);
        return ResultWrapper.getSuccessBuilder().data(merchantHotsales).build();
    }

}
