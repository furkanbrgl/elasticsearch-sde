package com.elasticfurkan.elasticfurkan.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableAsync
@EnableElasticsearchRepositories(basePackages = "com.elasticfurkan.elasticfurkan.repository")
public class ElasticsearchConfig {

    @Value("${elasticsearch.host}")
    private String EsHost;

    @Value("${elasticsearch.port}")
    private int EsPort;

    @Value("${elasticsearch.clustername}")
    private String EsClusterName;

    @Value("${pageSize}")
    private int pageSize;

    @Value("${contentSize}")
    private int contentSize;

    @Value("${esIndex}")
    private String esIndex;

    @Value("${searchHost}")
    private String searchHost;

    public String getSearchHost() {
        return searchHost;
    }

    public void setSearchHost(String searchHost) {
        this.searchHost = searchHost;
    }

    @Bean
    public Client client() {
        Settings esSettings = Settings.builder().put("cluster.name", "elasticsearch")
                .build();
        Client client = null;
        try {
            client = new PreBuiltTransportClient(esSettings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchOperations() {
        return new ElasticsearchTemplate(client());
    }

    public String getEsHost() {
        return EsHost;
    }

    public void setEsHost(String esHost) {
        EsHost = esHost;
    }

    public int getEsPort() {
        return EsPort;
    }

    public void setEsPort(int esPort) {
        EsPort = esPort;
    }

    public String getEsClusterName() {
        return EsClusterName;
    }

    public void setEsClusterName(String esClusterName) {
        EsClusterName = esClusterName;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getContentSize() {
        return contentSize;
    }

    public void setContentSize(int contentSize) {
        this.contentSize = contentSize;
    }

    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }
}