package com.elastic_search.Elastic.Search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * SearchRequest - Gelişmiş Arama İsteği DTO'su
 * 
 * Bu sınıf gelişmiş arama işlemleri için kullanılır.
 * Çoklu kriterlere göre arama yapmak için kullanılır.
 * 
 * @author Berat Kulcu
 * @version 1.0
 * @since 2025-07-25
 */
@Data // Lombok: Getter, Setter, toString, equals, hashCode metodlarını otomatik oluşturur
@NoArgsConstructor // Lombok: Parametresiz constructor oluşturur
@AllArgsConstructor // Lombok: Tüm field'lar için parametreli constructor oluşturur
public class SearchRequest {
    
    /**
     * Arama metni
     * İsim, açıklama ve tag'lerde aranacak kelime
     * Boş olabilir (sadece filtreleme için kullanılabilir)
     */
    private String query;
    
    /**
     * Kategori filtresi
     * Belirli bir kategorideki ürünleri filtrelemek için
     * Boş olabilir (tüm kategoriler dahil edilir)
     */
    private String category;
    
    /**
     * Minimum fiyat filtresi
     * Bu fiyattan yüksek veya eşit ürünleri getirir
     * Null olabilir (alt sınır yok)
     */
    private BigDecimal minPrice;
    
    /**
     * Maksimum fiyat filtresi
     * Bu fiyattan düşük veya eşit ürünleri getirir
     * Null olabilir (üst sınır yok)
     */
    private BigDecimal maxPrice;
} 