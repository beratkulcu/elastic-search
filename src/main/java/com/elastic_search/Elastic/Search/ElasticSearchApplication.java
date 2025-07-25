package com.elastic_search.Elastic.Search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Özellikler:
 * - CRUD işlemleri (Create, Read, Update, Delete)
 * - Gelişmiş arama (Advanced Search)
 * - Bulanık arama (Fuzzy Search)
 * - Kategori ve fiyat filtreleme
 * - Toplu işlemler (Bulk Operations)
 * - Swagger/OpenAPI dokümantasyonu
 * 
 * @author Berat Kulcu
 * @version 1.0
 * @since 2025-07-25
 */
@SpringBootApplication
public class ElasticSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication.class, args);
    }
}
