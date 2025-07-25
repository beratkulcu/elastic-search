package com.elastic_search.Elastic.Search.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenApiConfig - Swagger/OpenAPI Konfigürasyonu
 * 
 * Bu sınıf Swagger UI için API dokümantasyonunu yapılandırır.
 * API'nin meta bilgilerini, sunucu bilgilerini ve lisans bilgilerini tanımlar.
 * 
 * @author Berat Kulcu
 * @version 1.0
 * @since 2025-07-25
 */
@Configuration // Spring: Bu sınıfın bir konfigürasyon sınıfı olduğunu belirtir
public class OpenApiConfig {

    /**
     * OpenAPI konfigürasyonunu oluşturan bean
     * Swagger UI'da görünecek API bilgilerini tanımlar
     * 
     * @return OpenAPI - API dokümantasyon konfigürasyonu
     */
    @Bean // Spring: Bu metodun bir bean oluşturduğunu belirtir
    public OpenAPI myOpenAPI() {
        // ==================== SUNUCU BİLGİLERİ ====================
        
        // Geliştirme ortamı sunucu bilgisi
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080"); // API'nin çalıştığı URL
        devServer.setDescription("Server URL in Development environment"); // Sunucu açıklaması

        // ==================== İLETİŞİM BİLGİLERİ ====================
        
        // API sahibi iletişim bilgileri
        Contact contact = new Contact();
        contact.setEmail("admin@elasticsearch-demo.com"); // E-posta adresi
        contact.setName("Elasticsearch Demo API"); // İletişim kişisi adı
        contact.setUrl("https://www.elasticsearch-demo.com"); // Web sitesi

        // ==================== LİSANS BİLGİLERİ ====================
        
        // API lisans bilgisi
        License mitLicense = new License()
                .name("MIT License") // Lisans adı
                .url("https://choosealicense.com/licenses/mit/"); // Lisans URL'i

        // ==================== API BİLGİLERİ ====================
        
        // API'nin genel bilgileri
        Info info = new Info()
                .title("Elasticsearch Demo API") // API başlığı
                .version("1.0") // API versiyonu
                .contact(contact) // İletişim bilgileri
                .description("Bu API Elasticsearch ile ürün yönetimi ve arama işlemleri yapar.") // API açıklaması
                .termsOfService("https://www.elasticsearch-demo.com/terms") // Kullanım şartları URL'i
                .license(mitLicense); // Lisans bilgisi

        // ==================== OPENAPI OLUŞTURMA ====================
        
        // OpenAPI nesnesini oluştur ve döndür
        return new OpenAPI()
                .info(info) // API bilgilerini ekle
                .servers(List.of(devServer)); // Sunucu listesini ekle
    }
} 