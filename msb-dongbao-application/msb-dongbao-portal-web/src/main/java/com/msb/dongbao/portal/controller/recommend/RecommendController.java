package com.msb.dongbao.portal.controller.recommend;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.GoodDTO;
import com.msb.dongbao.pms.service.IRecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
@RequestMapping("/recommend")
@Validated
public class RecommendController {

    @Autowired
    private IRecommendService recommendService;


    @ApiOperation(value = "根据名称标题等查询商品列表")
    @PostMapping("cart/look")
    public ResultWrapper<Page<GoodDTO>> findByESCondition(@Valid @NotNull(message = "商品ID不能为空") @RequestParam(value = "relProductId", required = true) Long relProductId) {
        Page<GoodDTO> goodDTOS = recommendService.recommendByCardProductId(relProductId);
        return ResultWrapper.getSuccessBuilder().data(goodDTOS).build();
    }


}
