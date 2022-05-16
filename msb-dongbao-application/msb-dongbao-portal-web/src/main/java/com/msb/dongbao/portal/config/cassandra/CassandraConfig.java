package com.msb.dongbao.portal.config.cassandra;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * Cassandra配置类
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月10日 14时33分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Configuration
@EnableCassandraRepositories(basePackages = {"com.msb.dongbao.pay.repository"})
public class CassandraConfig {

}
