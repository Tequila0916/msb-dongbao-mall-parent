package com.msb.dongbao.portal.config.session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * 同时支持Header和Cookie的HttpSession id解析器
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月18日 14时23分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public class HeaderAndCookieHttpSessionIdResolver implements HttpSessionIdResolver {

    private static final String WRITTEN_SESSION_ID_ATTR = CookieHttpSessionIdResolver.class
            .getName().concat(".WRITTEN_SESSION_ID_ATTR");
    private static final String HEADER_X_AUTH_TOKEN = "X-Auth-Token";

    private static final String HEADER_AUTHENTICATION_INFO = "Authentication-Info";

    private final String sessionIdheaderName;


    /**
     * Cookie序列化类
     */
    private CookieSerializer cookieSerializer = new DefaultCookieSerializer();

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        // 先解析header
        String headerValue = request.getHeader(HEADER_X_AUTH_TOKEN);
        // 再处理cookie
        if (StringUtils.isNotEmpty(headerValue)) {
            return Collections.singletonList(headerValue);
        }
        return this.cookieSerializer.readCookieValues(request);
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response,
                             String sessionId) {
        // 返回响应头
        response.setHeader(this.sessionIdheaderName, sessionId);
        if (sessionId.equals(request.getAttribute(WRITTEN_SESSION_ID_ATTR))) {
            return;
        }
        request.setAttribute(WRITTEN_SESSION_ID_ATTR, sessionId);
        this.cookieSerializer
                .writeCookieValue(new CookieSerializer.CookieValue(request, response, sessionId));
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        // 失效session
        response.setHeader(this.sessionIdheaderName, "");
        this.cookieSerializer.writeCookieValue(new CookieSerializer.CookieValue(request, response, ""));
    }

    /**
     * Sets the {@link CookieSerializer} to be used.
     *
     * @param cookieSerializer the cookieSerializer to set. Cannot be null.
     */
    public void setCookieSerializer(CookieSerializer cookieSerializer) {
        if (cookieSerializer == null) {
            throw new IllegalArgumentException("cookieSerializer cannot be null");
        }
        this.cookieSerializer = cookieSerializer;
    }


    /**
     * Convenience factory to create {@link HeaderHttpSessionIdResolver} that uses
     * "X-Auth-Token" header.
     *
     * @return the instance configured to use "X-Auth-Token" header
     */
    public static HeaderAndCookieHttpSessionIdResolver xAuthToken() {
        return new HeaderAndCookieHttpSessionIdResolver(HEADER_X_AUTH_TOKEN);
    }

    /**
     * Convenience factory to create {@link HeaderHttpSessionIdResolver} that uses
     * "Authentication-Info" header.
     *
     * @return the instance configured to use "Authentication-Info" header
     */
    public static HeaderAndCookieHttpSessionIdResolver authenticationInfo() {
        return new HeaderAndCookieHttpSessionIdResolver(HEADER_AUTHENTICATION_INFO);
    }

    /**
     * The name of the header to obtain the session id from.
     *
     * @param sessionIdheaderName the name of the header to obtain the session id from.
     */
    public HeaderAndCookieHttpSessionIdResolver(String sessionIdheaderName) {
        if (sessionIdheaderName == null) {
            throw new IllegalArgumentException("headerName cannot be null");
        }
        this.sessionIdheaderName = sessionIdheaderName;
    }
}
