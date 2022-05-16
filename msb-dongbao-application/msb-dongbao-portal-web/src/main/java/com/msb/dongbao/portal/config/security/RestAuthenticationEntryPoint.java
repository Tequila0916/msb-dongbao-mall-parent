package com.msb.dongbao.portal.config.security;

import cn.hutool.json.JSONUtil;
import com.msb.dongbao.common.base.dto.ResultWrapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义返回结果：未登录或登录过期
 * Created by macro on 2018/5/14.
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ResultWrapper resultWrapper = ResultWrapper.getErrorBuilder().data(authException.getMessage()).build();
        response.getWriter().println(JSONUtil.parse(resultWrapper));
        response.getWriter().flush();
    }
}
