package com.onion.common.config;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.host}")
    private String host;

    @Bean
    public ElasticsearchTransport elasticsearchTransport() {
        RestClient restClient = RestClient.builder(
                        new HttpHost(host, 9200, "http"))
                .build();

        JacksonJsonpMapper mapper = new JacksonJsonpMapper();
        // Java 8 날짜 모듈 추가
        mapper.objectMapper().registerModule(new JavaTimeModule());
        // 불필요한 필드 무시 설정
        mapper.objectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 날짜를 배열이 아닌 ISO 8601 형식의 문자열로 직렬화하도록 설정
        mapper.objectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return new RestClientTransport(restClient, mapper);
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        return new ElasticsearchClient(elasticsearchTransport());
    }
}
