package com.msb.dongbao.portal.config.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * Spring-Session-Redis 配置
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月18日 14时11分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Slf4j
@EnableRedisHttpSession(
        redisNamespace = "${spring.session.nameSpace:MSB-DONGBAO-MALL}",
        maxInactiveIntervalInSeconds = 3600)
@Configuration
public class RedisHttpSessionConfig {

    @Value("${spring.session.cookieName:'MSB-JSESSIONID'}")
    private String cookieName;

    @Bean
    public DefaultCookieSerializer defaultCookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setCookiePath("/");
        // sessionId名称
        defaultCookieSerializer.setCookieName(cookieName);
        defaultCookieSerializer.setDomainNamePattern("");
        return defaultCookieSerializer;
    }

    /**
     * 自定义HttpSessionId解析器
     */
    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        HeaderAndCookieHttpSessionIdResolver headerAndCookieHttpSessionIdResolver = new HeaderAndCookieHttpSessionIdResolver(cookieName);
        headerAndCookieHttpSessionIdResolver.setCookieSerializer(defaultCookieSerializer());
        return headerAndCookieHttpSessionIdResolver;
    }

    /**
     * Spring-session-redis执行线程池
     *
     * @return
     */
    @Bean
    public ThreadPoolTaskScheduler springSessionRedisTaskExecutor() {
        ThreadPoolTaskScheduler taskSchedule = new ThreadPoolTaskScheduler();
        taskSchedule.setPoolSize(10);
        return taskSchedule;
    }


    /**
     * Redis内session过期事件监听
     *
     * @param expiredEvent
     */
    @EventListener
    public void onSessionExpired(SessionExpiredEvent expiredEvent) {
        String sessionId = expiredEvent.getSessionId();
        log.info(expiredEvent.getSession().getAttribute("user"));
        log.info("[{}]session过期", sessionId);
        // TODO session过期触发事件
    }

    /**
     * Redis内session删除事件监听
     *
     * @param deletedEvent
     */
    @EventListener
    public void onSessionDeleted(SessionDeletedEvent deletedEvent) {
        String sessionId = deletedEvent.getSessionId();
        log.info(deletedEvent.getSession().getAttribute("user"));
        log.info("删除session[{}]", sessionId);
    }


    /**
     * Redis内session保存事件监听
     *
     * @param createdEvent
     */
    @EventListener
    public void onSessionCreated(SessionCreatedEvent createdEvent) {
        String sessionId = createdEvent.getSessionId();
        log.info(createdEvent.getSession().getAttribute("user"));
        log.info("保存session[{}]", sessionId);
    }
}
