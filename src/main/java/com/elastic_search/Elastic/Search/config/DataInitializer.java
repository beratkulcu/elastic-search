package com.elastic_search.Elastic.Search.config;

import com.elastic_search.Elastic.Search.model.Product;
import com.elastic_search.Elastic.Search.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final ProductService productService;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Test verileri yükleniyor...");
        
        // Örnek ürünler
        List<Product> sampleProducts = Arrays.asList(
                createProduct("iPhone 15 Pro", "Apple'ın en yeni akıllı telefonu", "Elektronik", 
                        new BigDecimal("45000"), 50, Arrays.asList("telefon", "apple", "akıllı"), true),
                
                createProduct("Samsung Galaxy S24", "Samsung'un flagship telefonu", "Elektronik", 
                        new BigDecimal("35000"), 30, Arrays.asList("telefon", "samsung", "android"), true),
                
                createProduct("MacBook Pro M3", "Güçlü işlemci ile profesyonel laptop", "Bilgisayar", 
                        new BigDecimal("75000"), 20, Arrays.asList("laptop", "apple", "macbook"), true),
                
                createProduct("Dell XPS 13", "Ultrabook sınıfı laptop", "Bilgisayar", 
                        new BigDecimal("45000"), 25, Arrays.asList("laptop", "dell", "ultrabook"), true),
                
                createProduct("Sony WH-1000XM5", "Gürültü engelleyici kablosuz kulaklık", "Aksesuar", 
                        new BigDecimal("8500"), 100, Arrays.asList("kulaklık", "sony", "bluetooth"), true),
                
                createProduct("Apple Watch Series 9", "Akıllı saat", "Aksesuar", 
                        new BigDecimal("12000"), 75, Arrays.asList("saat", "apple", "akıllı"), true),
                
                createProduct("iPad Air", "Tablet bilgisayar", "Bilgisayar", 
                        new BigDecimal("25000"), 40, Arrays.asList("tablet", "apple", "ipad"), true),
                
                createProduct("Logitech MX Master 3", "Kablosuz mouse", "Aksesuar", 
                        new BigDecimal("2500"), 200, Arrays.asList("mouse", "logitech", "kablosuz"), true),
                
                createProduct("Samsung 4K Smart TV", "55 inç 4K televizyon", "Elektronik", 
                        new BigDecimal("35000"), 15, Arrays.asList("tv", "samsung", "4k"), true),
                
                createProduct("Nike Air Max 270", "Spor ayakkabı", "Giyim", 
                        new BigDecimal("2500"), 150, Arrays.asList("ayakkabı", "nike", "spor"), true)
        );
        
        try {
            productService.saveAllProducts(sampleProducts);
            log.info("{} adet test ürünü başarıyla yüklendi", sampleProducts.size());
        } catch (Exception e) {
            log.warn("Test verileri yüklenirken hata oluştu: {}", e.getMessage());
        }
    }
    
    private Product createProduct(String name, String description, String category, 
                                BigDecimal price, Integer stock, List<String> tags, Boolean isActive) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product.setStock(stock);
        product.setTags(tags);
        product.setIsActive(isActive);
        return product;
    }
} 