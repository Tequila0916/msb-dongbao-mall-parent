package com.msb.dongbao.portal.config.es;


import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.msb.dongbao.portal.config.es.ElasticSearchProperties.IP_KEY;
import static com.msb.dongbao.portal.config.es.ElasticSearchProperties.PORT_KEY;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.msb.dongbao.pms.db.dao")
public class ElasticSearchConfig {

    @Autowired
    private  ElasticSearchProperties searchProperties;

    @Bean
    public Client elasticsearchClient(){
        Settings settings = Settings.builder().put(ElasticSearchProperties.CLUSTER_NAME_KEY, searchProperties.getClusterName()).build();
        TransportClient client = new PreBuiltTransportClient(settings);
        searchProperties.getClusterNodes().stream().forEach(node-> {
            try {
                client.addTransportAddress(new TransportAddress(InetAddress.getByName(node.get(IP_KEY)), Integer.parseInt(node.getOrDefault(PORT_KEY,"9300"))));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        });
        return client;
    }

    @Bean(name = {"elasticsearchOperations", "elasticsearchTemplate"})
    public ElasticsearchTemplate elasticsearchTemplate(){
        return new ElasticsearchTemplate(elasticsearchClient());
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client, ElasticsearchConverter converter) {
        try {
            return new ElasticsearchTemplate(client,
                    new ElasticsearchEntityMapper(converter.getMappingContext(), null));
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }


}
