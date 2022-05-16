package com.msb.dongbao.portal.controller;

import com.google.gson.Gson;
import com.msb.dongbao.common.base.constant.CommonConstants;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

import static com.msb.dongbao.common.base.constant.RedisConstants.REDIS_UNLOGIN_CART_COOKIE_KEY;

/**
 * 系统状态控制器
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年05月27日 17时14分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Api(value = "状态控制器", tags = "状态控制器")
@RestController
@Slf4j
public class StatusController {

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "项目状态")
    @GetMapping("/status")
    public String status(HttpServletRequest request) {
        log.debug("项目状态:{}", "success");
        log.info("项目状态:{}", "success");
        log.warn("项目状态:{}", "success");
        log.error("项目状态:{}", "success");
        Object attribute = request.getSession(false).getAttribute(CommonConstants.SESSION_USER_KEY);
        log.info(new Gson().toJson(attribute));
        return "success";
    }

    @PostMapping("/testForm")
    public ResultWrapper<Boolean> test(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        log.info(new Gson().toJson(map));
        return ResultWrapper.getSuccessBuilder().data(true).build();
    }

    @PostMapping("/testDel")
    public ResultWrapper<Boolean> testDel(HttpServletRequest request) {
        Set<String> keys = redisTemplate.keys(REDIS_UNLOGIN_CART_COOKIE_KEY);

        keys.stream().forEach(key -> {
            log.info(key);
            Boolean delete = redisTemplate.delete(key);
        });
        keys = redisTemplate.keys(REDIS_UNLOGIN_CART_COOKIE_KEY);

        keys.stream().forEach(key -> {
            log.info(key);
            Boolean delete = redisTemplate.delete(key);
        });
        return ResultWrapper.getSuccessBuilder().data(true).build();
    }

}
