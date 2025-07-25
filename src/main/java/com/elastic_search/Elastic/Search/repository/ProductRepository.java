package com.elastic_search.Elastic.Search.repository;

import com.elastic_search.Elastic.Search.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * ProductRepository - Ürün Veri Erişim Katmanı
 * 
 * Bu interface Elasticsearch ile ürün verilerini yönetmek için kullanılır.
 * Spring Data Elasticsearch'in ElasticsearchRepository'sini extend eder.
 * 
 * @author Berat Kulcu
 * @version 1.0
 * @since 2025-07-25
 */
@Repository // Spring: Bu sınıfın bir repository bean'i olduğunu belirtir
public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    
    // ==================== TEMEL ARAMA METODLARI ====================
    
    /**
     * İsme göre ürün arama
     * @param name Aranacak ürün adı (kısmi eşleşme)
     * @return İsmi aranan kelimeyi içeren ürünlerin listesi
     */
    List<Product> findByNameContaining(String name);
    
    /**
     * İsim veya açıklamaya göre ürün arama
     * @param name Aranacak ürün adı
     * @param description Aranacak açıklama
     * @return İsmi veya açıklaması aranan kelimeyi içeren ürünlerin listesi
     */
    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
    
    /**
     * Kategoriye göre ürün arama
     * @param category Aranacak kategori adı (tam eşleşme)
     * @return Belirtilen kategorideki ürünlerin listesi
     */
    List<Product> findByCategory(String category);
    
    /**
     * Fiyat aralığına göre ürün arama
     * @param minPrice Minimum fiyat (dahil)
     * @param maxPrice Maksimum fiyat (dahil)
     * @return Belirtilen fiyat aralığındaki ürünlerin listesi
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Stok miktarına göre ürün arama
     * @param stock Minimum stok miktarı
     * @return Stok miktarı belirtilen değerden fazla olan ürünlerin listesi
     */
    List<Product> findByStockGreaterThan(Integer stock);
    
    /**
     * Aktif ürünleri getirme
     * @return isActive=true olan ürünlerin listesi
     */
    List<Product> findByIsActiveTrue();
    
    // ==================== TAG'LERE GÖRE ARAMA ====================
    
    /**
     * Tag'lere göre ürün arama
     * @param tag Aranacak tag (kısmi eşleşme)
     * @return Belirtilen tag'i içeren ürünlerin listesi
     */
    List<Product> findByTagsContaining(String tag);
    
    // ==================== FİYAT ARALIĞINA GÖRE ARAMA ====================
    
    /**
     * Belirli fiyatın altındaki ürünleri getirme
     * @param price Maksimum fiyat
     * @return Belirtilen fiyatın altındaki ürünlerin listesi
     */
    List<Product> findByPriceLessThan(BigDecimal price);
    
    /**
     * Belirli fiyatın üstündeki ürünleri getirme
     * @param price Minimum fiyat
     * @return Belirtilen fiyatın üstündeki ürünlerin listesi
     */
    List<Product> findByPriceGreaterThan(BigDecimal price);
    
    // ==================== KOMBİNE ARAMA METODLARI ====================
    
    /**
     * Kategori ve fiyat aralığına göre ürün arama
     * @param category Aranacak kategori
     * @param minPrice Minimum fiyat
     * @param maxPrice Maksimum fiyat
     * @return Belirtilen kategoride ve fiyat aralığındaki ürünlerin listesi
     */
    List<Product> findByCategoryAndPriceBetween(String category, BigDecimal minPrice, BigDecimal maxPrice);
    
    // ==================== STOK DURUMUNA GÖRE ARAMA ====================
    
    /**
     * Aktif ve belirli stok miktarının üstündeki ürünleri getirme
     * @param stock Minimum stok miktarı
     * @return Aktif ve stok miktarı yeterli olan ürünlerin listesi
     */
    List<Product> findByIsActiveTrueAndStockGreaterThan(Integer stock);
} 