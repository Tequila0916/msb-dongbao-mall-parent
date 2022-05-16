package com.msb.dongbao.portal.controller.oms;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.oms.model.dto.OrderRefundDTO;
import com.msb.dongbao.oms.service.IOrderRefundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 退单表  前端控制器
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-09
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "退单表  控制器", tags = "退单表  控制器")
@RestController
@RequestMapping("/oms/orderRefund")
@Validated
public class OrderRefundController {


    @Autowired
    private IOrderRefundService orderRefundService;

    @ApiOperation(value = "退单")
    @ApiParam(name = "订单编号", value = "订单编号", required = true)
    @PostMapping("/orderRefund")
    public ResultWrapper<Boolean> orderRefund(@Validated @RequestBody OrderRefundDTO orderRefundDTO) throws Exception {
        boolean success = orderRefundService.orderRefund(orderRefundDTO);
        return ResultWrapper.getSuccessBuilder().data(success).build();

    }
}
