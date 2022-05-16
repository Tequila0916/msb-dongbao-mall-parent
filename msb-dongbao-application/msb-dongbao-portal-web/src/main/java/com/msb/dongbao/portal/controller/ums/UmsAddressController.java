package com.msb.dongbao.portal.controller.ums;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.ums.model.dto.UmsAddressDTO;
import com.msb.dongbao.ums.service.IUmsAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 收货地址 前端控制器
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-07-01
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "收货地址 控制器", tags = "收货地址 控制器")
@RestController
@RequestMapping("/ums/umsAddress")
@Validated
public class UmsAddressController {

    @Autowired
    private IUmsAddressService umsAddressService;

    @ApiOperation(value = "添加收货地址")
    @PostMapping("/add")
    public ResultWrapper addAddress(@Valid @RequestBody UmsAddressDTO dto) {
        return umsAddressService.addAddress(dto);
    }

    @ApiOperation(value = "删除收货地址")
    @ApiParam(name = "收货地址ID", value = "收货地址ID", required = true)
    @PostMapping("/delete")
    public ResultWrapper deleteAddress(@NotNull(message = "收货地址ID不能为空") Integer id) {
        return umsAddressService.deleteAddress(id);
    }

    @ApiOperation(value = "更新收货地址")
    @PostMapping("/update")
    public ResultWrapper updateAddress(@Valid @RequestBody UmsAddressDTO dto) {
        return umsAddressService.updateAddress(dto);
    }

    @ApiOperation(value = "分页查询收货地址")
    @PostMapping("/list")
    public ResultWrapper<List<UmsAddressDTO>> listUmsAddress(String username) {
        return umsAddressService.listUmsAddress(username);
    }

    @ApiOperation(value = "查询收货地址详情")
    @ApiParam(name = "收货地址ID", value = "收货地址ID", required = true)
    @GetMapping("/detail")
    public ResultWrapper<UmsAddressDTO> detail(@NotNull(message = "收货地址ID不能为空") Integer id) {
        return umsAddressService.detail(id);
    }
}
