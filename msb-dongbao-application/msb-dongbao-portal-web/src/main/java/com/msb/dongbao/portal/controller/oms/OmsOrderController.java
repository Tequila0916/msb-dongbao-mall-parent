package com.msb.dongbao.portal.controller.oms;

import com.msb.dongbao.common.base.dto.PageResult;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.oms.model.dto.OmsOrderAndItemsDTO;
import com.msb.dongbao.oms.model.dto.OmsOrderPageDTO;
import com.msb.dongbao.oms.model.dto.OrderParamNewDTO;
import com.msb.dongbao.oms.model.entity.OmsOrder;
import com.msb.dongbao.oms.service.IOmsOrderService;
import com.msb.dongbao.pay.common.PayChannelEnum;
import com.msb.dongbao.pay.model.dto.MallPayDTO;
import com.msb.dongbao.pay.strategy.PayContextStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "订单 控制器", tags = "订单 控制器")
@RestController
@RequestMapping("/oms/omsOrder")
@Validated
@Slf4j
public class OmsOrderController {

    @Autowired
    private IOmsOrderService omsOrderService;
    @Autowired
    private PayContextStrategy payContextStrategy;


    @ApiOperation(value = "分页查询")
    @GetMapping("/list")
    public ResultWrapper<PageResult<OmsOrderAndItemsDTO>> pageListProduct(@RequestParam @NotNull Integer pageIndex,
                                                                          @RequestParam @NotNull Integer pageSize) {
        OmsOrderPageDTO pageDto = new OmsOrderPageDTO();
        pageDto.setPageIndex(pageIndex);
        pageDto.setLength(pageSize);
        return omsOrderService.pageListOmsOrder(pageDto);
    }


    @ApiOperation(value = "查询详情")
    @ApiParam(name = "ID", value = "ID", required = true)
    @GetMapping("/detail")
    public ResultWrapper<OmsOrderAndItemsDTO> detail(@NotNull(message = "订单编号不能为空") String orderNumber) {
        OmsOrderAndItemsDTO detail = omsOrderService.orderAndItemsDetail(orderNumber);
        return ResultWrapper.getSuccessBuilder().data(detail).build();
    }


    @ApiOperation(value = "提交订单")
    @PostMapping("/createOrderNew")
    public ResultWrapper<String> createOrderNew(@RequestBody @Validated OrderParamNewDTO orderParamNewDTO) throws Exception {
        ResultWrapper<OmsOrder> resultWrapper = omsOrderService.generateOrder(orderParamNewDTO);
        OmsOrder omsOrder = resultWrapper.getData();
            MallPayDTO mallPayDTO = MallPayDTO.builder()
                    .payCode(PayChannelEnum.ALI_PAY.getCode())
                    .orderNo(omsOrder.getOrderNumber()).build();
            String payHtml = payContextStrategy.goPayHtml(mallPayDTO);
            return ResultWrapper.getSuccessBuilder().data(payHtml).build();
    }
}
