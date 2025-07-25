package com.elastic_search.Elastic.Search.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.elastic_search.Elastic.Search.repository")
public class ElasticsearchConfig {
    // Basit konfigürasyon - application.properties'den ayarları alacak
} 