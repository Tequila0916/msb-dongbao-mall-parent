package com.msb.dongbao.portal.config.es;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * elasticsearch 配置
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年08月24日 16时26分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@ConfigurationProperties(prefix = "elasticsearch")
@Configuration
public class ElasticSearchProperties {
    public static final String CLUSTER_NAME_KEY = "cluster.name";
    public static final String IP_KEY = "ip";
    public static final String PORT_KEY = "port";

    /** 集群名称 */
    private String clusterName;
    /** 集群节点 */
    private List<Map<String,String>> clusterNodes;
}
