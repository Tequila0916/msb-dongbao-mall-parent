package com.msb.dongbao.portal.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redis配置
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月18日 14时29分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Configuration
public class RedissonConfig {


    @Configuration
    @AutoConfigureAfter(RedisConfig.class)
    @ConditionalOnProperty(name = "spring.redis.mode", havingValue = "cluster")
    public class ClusterConfig {

        @Value("${spring.redis.cluster.nodes}")
        private String cluster;

        @Value("${spring.redis.password}")
        private String password;

        @Bean
        public RedissonClient redissonClusterClient() {
            String[] nodes = cluster.split(",");
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = "redis://" + nodes[i];
            }
            Config config = new Config();
            config.useClusterServers()
                    .setMasterConnectionMinimumIdleSize(10)
                    .setPingConnectionInterval(6000)
                    .setPassword(password);
            RedissonClient client = Redisson.create(config);
            return client;
        }

    }


    @Configuration
    @AutoConfigureAfter(RedisConfig.class)
    @ConditionalOnProperty(name = "spring.redis.mode", havingValue = "single")
    public class SingleConfig {

        @Value("${spring.redis.host}")
        private String host;

        @Value("${spring.redis.database}")
        private Integer database;

        @Value("${spring.redis.port}")
        private String port;

        @Value("${spring.redis.password}")
        private String password;

        @Bean
        RedissonClient redissonSingleClient() {
            Config config = new Config();
            String node = "redis://" + host + ":" + port;
            SingleServerConfig serverConfig = config.useSingleServer()
                    .setConnectionMinimumIdleSize(10)
                    .setPingConnectionInterval(6000)
                    .setAddress(node)
                    .setDatabase(database);
            boolean flag = password != null && !"".equalsIgnoreCase(password);
            if (flag) {
                serverConfig.setPassword(password);
            }
            return Redisson.create(config);
        }

    }


}
