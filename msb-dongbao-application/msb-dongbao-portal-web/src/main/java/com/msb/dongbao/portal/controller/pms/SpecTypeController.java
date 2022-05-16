package com.msb.dongbao.portal.controller.pms;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.service.ISpecTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 规格类别 前端控制器
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-17
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "规格类别 控制器",tags = "规格类别 控制器")
@RestController
@RequestMapping("/pms/specType")
@Validated
public class SpecTypeController {

    @Autowired
    private ISpecTypeService specTypeService;

    @ApiOperation(value = "新增规格类别")
    @ApiParam(name = "规格类别名称",value = "规格类别名称",required = true)
    @PostMapping("/addSpecType")
    public ResultWrapper addSpecType(@RequestParam("name") String name){
        return specTypeService.addSpecType(name);
    }

}
