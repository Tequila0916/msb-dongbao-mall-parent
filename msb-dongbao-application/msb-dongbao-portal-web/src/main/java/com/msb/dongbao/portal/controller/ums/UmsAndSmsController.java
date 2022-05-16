package com.msb.dongbao.portal.controller.ums;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.sms.model.dto.SmsCouponBySpuDTO;
import com.msb.dongbao.sms.model.dto.SmsCouponByPcDTO;
import com.msb.dongbao.sms.model.dto.SmsCouponHaveDescriptionDTO;
import com.msb.dongbao.sms.service.ISmsCouponHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户和优惠券相关  的前端控制器
 *
 * @author 马士兵 · 项目架构部--www
 * @version V1.0
 * @date: 17:23
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "优惠券相关", tags = "优惠券相关")
@RestController
@RequestMapping("/umsAndSms")
@Validated
public class UmsAndSmsController {

    @Autowired
    private ISmsCouponHistoryService smsCouponHistoryService;

    @ApiOperation("领取指定优惠券")
    @GetMapping(value = "/addCoupon")
    public ResultWrapper<String> add(@NotNull Long couponId) {
        smsCouponHistoryService.doSave(couponId);
        return ResultWrapper.getSuccessBuilder().data("领取成功").build();
    }

    @ApiOperation("根据商品分类Ids获取当前用户领取了的优惠券信息")
    @ApiImplicitParam(name = "productCategoryIdsS", value = "商品分类id集合,号分割", paramType = "query")
    @GetMapping(value = "/list/productCategoryIds")
    public ResultWrapper<List<SmsCouponByPcDTO>> listBySpuIdsAndUser(@NotBlank String productCategoryIdsS) {
        List<Long> productCategoryIds = Arrays.asList(productCategoryIdsS.split(",")).stream().map(Long::new).collect(Collectors.toList());
        return smsCouponHistoryService.listByProductCategoryIdsAndUser(productCategoryIds);
    }

    @ApiOperation("(商品详情)根据spuId 或者 商品分类id 或者 用户,获取优惠券信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spuId", value = "商品spuId", paramType = "query"),
            @ApiImplicitParam(name = "productCategoryId", value = "商品分类id", paramType = "query")
    })
    @GetMapping(value = "/list/spuIdOrPcId")
    public ResultWrapper<List<SmsCouponHaveDescriptionDTO>> listBySkuIdOrSpuIdOrUser(@NotNull Long spuId, @NotNull Long productCategoryId) {
        return smsCouponHistoryService.listBySpuIdOrProductCategoryIdOrUser(spuId, productCategoryId);
    }


    @ApiOperation("根据spuIds获取优惠券信息")
    @ApiImplicitParam(name = "spuIdsS", value = "spuId集合", paramType = "query")
    @GetMapping(value = "/list/spuIds")
    public ResultWrapper<List<SmsCouponBySpuDTO>> listBySkuIds(@NotBlank String spuIdsS) {
        List<Long> spuIds = Arrays.asList(spuIdsS.split(",")).stream()
                .map(Long::new)
                .collect(Collectors.toList());
        return smsCouponHistoryService.listBySpuIds(spuIds);
    }

    @ApiOperation("根据登录用户获取优惠券信息")
    @GetMapping(value = "/list/allCouponsByUser")
    public ResultWrapper<List<SmsCouponHaveDescriptionDTO>> listByUser() {
        return smsCouponHistoryService.listByUser();
    }


}
