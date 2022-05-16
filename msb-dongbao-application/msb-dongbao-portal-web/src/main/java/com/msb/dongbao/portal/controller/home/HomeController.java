package com.msb.dongbao.portal.controller.home;

import com.msb.dongbao.common.base.dto.ResultWrapper;
import com.msb.dongbao.pms.model.dto.HomeResultDTO;
import com.msb.dongbao.pms.service.IHomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 网站首页控制器
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年07月02日 18时16分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "网站首页控制器", tags = "网站首页控制器")
@RestController
@RequestMapping("/home/*")
@Validated
@Slf4j
public class HomeController {

    @Autowired
    private IHomeService homeService;

    @ApiOperation(value = "首页商品")
    @GetMapping("/index")
    public ResultWrapper<HomeResultDTO> index(HttpServletRequest request) {
        return homeService.homeResult(request);
    }
}
