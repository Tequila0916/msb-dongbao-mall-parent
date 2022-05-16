package com.msb.dongbao.manager.config.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年05月28日 16时19分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Configuration
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class WebConfig implements WebMvcConfigurer {

    /**
     * 防止@EnableMvc把默认的静态资源路径覆盖
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决静态资源无法访问
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        // 解决 swagger-ui.html 404报错
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger无法访问
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

//    /**
//     * @param registry 添加拦截器
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.
//        //添加登录处理拦截器，拦截所有请求，登录请求除外
//        InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginAndAuthInterceptor());
//        //排除配置
//        interceptorRegistration.excludePathPatterns("/doc.html","/error","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
//        interceptorRegistration.excludePathPatterns("/user/login","/error/*", "/*/list","/*/list/*");
//        //配置拦截策略
//        interceptorRegistration.addPathPatterns("/**");
//    }

    /**
     * 处理跨域请求
     * @return
     */
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        // 放行哪些原始域
//        config.addAllowedOrigin("*");
//        // 是否发送cookie信息
//        config.setAllowCredentials(true);
//        // 放行哪些原始域（请求方式）
//        config.addAllowedMethod("*");
//        // 放行哪些原始域（头部信息）
//        config.addAllowedHeader("*");
//        // 暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息
//        config.addExposedHeader(HttpHeaders.ACCEPT);
//        config.setMaxAge(3600L);
//        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
//        configSource.registerCorsConfiguration("/**", config);
//        return new CorsFilter(configSource);
//    }

//    /**
//     * swagger-ui.html路径映射，浏览器中使用/api-docs访问
//     *
//     * @param registry
//     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addRedirectViewController(
//                "/api-docs",
//                "/swagger-ui.html");
//    }

}
