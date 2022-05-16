package com.msb.dongbao.portal.controller.pms;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.SpecificationDTO;
import com.msb.dongbao.pms.service.ISpecificationService;
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
 * 规格 前端控制器
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "规格 控制器",tags = "规格 控制器")
@RestController
@RequestMapping("/pms/specification")
@Validated
public class SpecificationController {

    @Autowired
    private ISpecificationService specificationService;

    @ApiOperation(value = "新增类目/规格/规格项")
    @PostMapping("/addCategorySpec")
    public ResultWrapper addCategorySpec(@RequestBody List<SpecificationDTO> specList){
        return specificationService.addCategorySpec(specList);

    }
}
