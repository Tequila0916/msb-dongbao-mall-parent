package com.msb.dongbao.portal.controller.pay;

import com.alipay.api.AlipayApiException;
import com.msb.dongbao.pay.common.PayChannelEnum;
import com.msb.dongbao.pay.model.dto.MallPayDTO;
import com.msb.dongbao.pay.strategy.PayContextStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * 统一支付接口
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月07日 13时21分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "第三方支付控制器", tags = "第三方支付控制器")
@Slf4j
@RestController
@RequestMapping(value = "/pay/*")
public class PayController {

    @Autowired
    private PayContextStrategy payContextStrategy;

    @RequestMapping(value = "/test/index", method = RequestMethod.GET)
    @ApiOperation(value = "测试支付宝页面")
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    @ApiOperation(value = "支付成功页面")
    public ModelAndView successPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("success");
        return mav;
    }

    /**
     * 下单支付
     *
     * @param mallPay
     * @return
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    @ApiOperation(value = "统一收单下单并支付页面接口")
    @GetMapping(value = "/goPay")
    public String goPay(MallPayDTO mallPay) throws Exception {
        return payContextStrategy.goPayHtml(mallPay);
    }


    /**
     * 退款
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    @ApiOperation(value = "统一交易退款接口")
    @PostMapping("/refund")
    public String refund(HttpServletRequest request) {
        return payContextStrategy.refund(request);
    }


    /**
     * 支付宝服务器异步通知
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    @ApiOperation(value = "支付宝服务器异步通知")
    @RequestMapping(value = "/aliPay/notify", method = RequestMethod.POST)
    public String payNotify(HttpServletRequest request) {
        return payContextStrategy.payNotify(request, PayChannelEnum.ALI_PAY.getCode());
    }

    /**
     * 支付宝服务器同步通知接口
     * 1. 如果是前后端分离项目,同步回调的地址应该是一个前端地址
     * 订单支付成功的通用页面地址，该页面通过截取路径参数，发起ajax请求到该接口，进行同步通知，
     * 更新订单与支付流水状态
     * 2. 如果同步通知到后台地址，需要再次进行转发统一支付成功页面
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "支付宝服务器同步通知页面")
    @RequestMapping(value = "/aliPay/return", method = {RequestMethod.GET, RequestMethod.POST})
    public String payReturn(HttpServletRequest request, HttpServletResponse response) {
        return payContextStrategy.payReturn(request, response, PayChannelEnum.ALI_PAY.getCode());
    }

    /**
     * 关闭交易
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    @ApiOperation(value = "统一关闭交易")
    @PostMapping("/close")
    public String tradeClose(HttpServletRequest request) {
        return payContextStrategy.tradeClose(request);
    }

    /**
     * 支付结果查询
     *
     * @return
     */
    @ApiOperation(value = "统一交易查询")
    @PostMapping("/query/trade")
    public String queryTrade(HttpServletRequest request) {
        return payContextStrategy.tradeQuery(request);
    }

    /**
     * 退款查询
     *
     * @return
     */
    @ApiOperation(value = "统一交易退款查询")
    @PostMapping("/query/refund")
    public String queryRefund(HttpServletRequest request) {
        return payContextStrategy.refundQuery(request);
    }


    /**
     * 获取账单
     *
     * @return
     */
    @ApiOperation(value = "获取账单")
    @PostMapping("/query/bill")
    public String queryBill() {
        // TODO 获取账单
        return "queryBill";
    }


    /**
     * 支付订单同步
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "支付订单同步")
    @PostMapping("/order/sync")
    public String syncOrder(HttpServletRequest request) {
        // TODO 结算明细
        return payContextStrategy.orderSync(request);
    }

}
