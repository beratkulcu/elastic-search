package com.elastic_search.Elastic.Search.service;

import com.elastic_search.Elastic.Search.dto.ProductRequest;
import com.elastic_search.Elastic.Search.dto.SearchRequest;
import com.elastic_search.Elastic.Search.model.Product;
import com.elastic_search.Elastic.Search.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ProductService - Ürün İş Mantığı Katmanı
 * 
 * Bu sınıf ürünlerle ilgili tüm iş mantığını içerir.
 * CRUD işlemleri, arama işlemleri ve gelişmiş sorgular burada yapılır.
 * 
 * @author Berat Kulcu
 * @version 1.0
 * @since 2025-07-25
 */
@Service // Spring: Bu sınıfın bir service bean'i olduğunu belirtir
@RequiredArgsConstructor // Lombok: Final field'lar için constructor oluşturur
@Slf4j // Lombok: Logging için slf4j logger oluşturur
public class ProductService {
    private final ProductRepository productRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * Yeni ürün oluşturma
     * ProductRequest'ten Product entity'sine dönüştürür ve kaydeder
     * 
     * @param request Ürün oluşturma isteği
     * @return Oluşturulan ve kaydedilen ürün
     */
    public Product createProduct(ProductRequest request) {
        // ProductRequest'ten Product entity'sine dönüştürme
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setTags(request.getTags());
        product.setIsActive(request.getIsActive());
        
        // Ürünü kaydet ve döndür
        return saveProduct(product);
    }
    
    /**
     * Ürün kaydetme
     * 
     * @param product Kaydedilecek ürün
     * @return Kaydedilen ürün
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    
    /**
     * ID'ye göre ürün getirme
     * 
     * @param id Ürün ID'si
     * @return Optional<Product> - Ürün bulunursa içinde ürün, bulunamazsa boş
     */
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }
    
    /**
     * Tüm ürünleri getirme
     * Iterable'dan List'e dönüştürme işlemi yapar
     * 
     * @return Tüm ürünlerin listesi
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }
    
    /**
     * ID'ye göre ürün bulma (getProductById ile aynı)
     * 
     * @param id Ürün ID'si
     * @return Optional<Product> - Ürün bulunursa içinde ürün, bulunamazsa boş
     */
    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }
    
    /**
     * Tüm ürünleri getirme (getAllProducts ile aynı)
     * 
     * @return Tüm ürünlerin listesi
     */
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }
    
    /**
     * Sayfalama ile ürünleri getirme
     * 
     * @param pageable Sayfalama parametreleri
     * @return Sayfalanmış ürün listesi
     */
    public Page<Product> findAllWithPagination(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    
    /**
     * Ürün güncelleme
     * 
     * @param id Güncellenecek ürünün ID'si
     * @param request Güncelleme isteği
     * @return Optional<Product> - Güncelleme başarılıysa güncellenmiş ürün
     */
    public Optional<Product> updateProduct(String id, ProductRequest request) {
        // Ürünü ID'ye göre bul
        Optional<Product> existingProduct = productRepository.findById(id);
        
        if (existingProduct.isPresent()) {
            // Ürün bulundu, güncelle
            Product product = existingProduct.get();
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setCategory(request.getCategory());
            product.setPrice(request.getPrice());
            product.setStock(request.getStock());
            product.setTags(request.getTags());
            product.setIsActive(request.getIsActive());
            
            // Güncellenmiş ürünü kaydet ve döndür
            return Optional.of(productRepository.save(product));
        }
        
        // Ürün bulunamadı
        return Optional.empty();
    }
    
    /**
     * Ürün silme
     * 
     * @param id Silinecek ürünün ID'si
     * @return boolean - Silme başarılıysa true, ürün bulunamazsa false
     */
    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Ürün silme (deleteProduct ile aynı, void döndürür)
     * 
     * @param id Silinecek ürünün ID'si
     */
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }
    
    // ==================== TEMEL ARAMA İŞLEMLERİ ====================
    
    /**
     * Basit metin araması
     * İsim veya açıklamada aranan kelimeyi içeren ürünleri bulur
     * 
     * @param query Aranacak metin
     * @return Arama sonuçları
     */
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingOrDescriptionContaining(query, query);
    }
    
    /**
     * İsme göre arama
     * 
     * @param name Aranacak ürün adı
     * @return İsmi aranan kelimeyi içeren ürünler
     */
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContaining(name);
    }
    
    /**
     * Kategoriye göre arama
     * 
     * @param category Aranacak kategori
     * @return Belirtilen kategorideki ürünler
     */
    public List<Product> searchByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    /**
     * Fiyat aralığına göre arama (BigDecimal)
     * 
     * @param minPrice Minimum fiyat
     * @param maxPrice Maksimum fiyat
     * @return Belirtilen fiyat aralığındaki ürünler
     */
    public List<Product> searchByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    /**
     * Fiyat aralığına göre arama (Double)
     * Double değerleri BigDecimal'a çevirir
     * 
     * @param minPrice Minimum fiyat
     * @param maxPrice Maksimum fiyat
     * @return Belirtilen fiyat aralığındaki ürünler
     */
    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(
            BigDecimal.valueOf(minPrice), 
            BigDecimal.valueOf(maxPrice)
        );
    }
    
    /**
     * Kategoriye göre ürünleri getirme
     * 
     * @param category Kategori adı
     * @return Belirtilen kategorideki ürünler
     */
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    /**
     * Tag'lere göre arama
     * 
     * @param tag Aranacak tag
     * @return Belirtilen tag'i içeren ürünler
     */
    public List<Product> searchByTag(String tag) {
        return productRepository.findByTagsContaining(tag);
    }
    
    /**
     * Aktif ürünleri getirme
     * 
     * @return isActive=true olan ürünler
     */
    public List<Product> findActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }
    
    /**
     * Stokta olan ürünleri getirme
     * 
     * @param minStock Minimum stok miktarı
     * @return Aktif ve yeterli stoku olan ürünler
     */
    public List<Product> findProductsInStock(Integer minStock) {
        return productRepository.findByIsActiveTrueAndStockGreaterThan(minStock);
    }
    
    // ==================== GELİŞMİŞ ARAMA İŞLEMLERİ ====================
    
    /**
     * Gelişmiş arama (SearchRequest ile)
     * Çoklu kriterlere göre arama yapar
     * 
     * @param request Arama isteği (query, category, minPrice, maxPrice)
     * @return SearchHits<Product> - Arama sonuçları ve skorlar
     */
    public SearchHits<Product> advancedSearch(SearchRequest request) {
        // Elasticsearch Criteria oluştur
        Criteria criteria = new Criteria();
        
        // Ana arama sorgusu (isim, açıklama, tag'lerde arama)
        if (request.getQuery() != null && !request.getQuery().trim().isEmpty()) {
            criteria.or("name").contains(request.getQuery())
                    .or("description").contains(request.getQuery())
                    .or("tags").contains(request.getQuery());
        }
        
        // Kategori filtresi
        if (request.getCategory() != null && !request.getCategory().trim().isEmpty()) {
            criteria.and("category").is(request.getCategory());
        }
        
        // Fiyat aralığı filtresi
        if (request.getMinPrice() != null || request.getMaxPrice() != null) {
            Criteria priceCriteria = new Criteria("price");
            if (request.getMinPrice() != null) {
                priceCriteria.greaterThanEqual(request.getMinPrice());
            }
            if (request.getMaxPrice() != null) {
                priceCriteria.lessThanEqual(request.getMaxPrice());
            }
            criteria.and(priceCriteria);
        }
        
        // Sadece aktif ürünleri getir
        criteria.and("isActive").is(true);
        
        // Sorguyu oluştur ve çalıştır
        Query searchQuery = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(searchQuery, Product.class);
    }
    
    /**
     * Gelişmiş arama (parametreler ile)
     * 
     * @param query Arama metni
     * @param category Kategori filtresi
     * @param minPrice Minimum fiyat
     * @param maxPrice Maksimum fiyat
     * @return SearchHits<Product> - Arama sonuçları
     */
    public SearchHits<Product> advancedSearch(String query, String category, BigDecimal minPrice, BigDecimal maxPrice) {
        // Elasticsearch Criteria oluştur
        Criteria criteria = new Criteria();
        
        // Ana arama sorgusu
        if (query != null && !query.trim().isEmpty()) {
            criteria.or("name").contains(query)
                    .or("description").contains(query)
                    .or("tags").contains(query);
        }
        
        // Kategori filtresi
        if (category != null && !category.trim().isEmpty()) {
            criteria.and("category").is(category);
        }
        
        // Fiyat aralığı filtresi
        if (minPrice != null || maxPrice != null) {
            Criteria priceCriteria = new Criteria("price");
            if (minPrice != null) {
                priceCriteria.greaterThanEqual(minPrice);
            }
            if (maxPrice != null) {
                priceCriteria.lessThanEqual(maxPrice);
            }
            criteria.and(priceCriteria);
        }
        
        // Sadece aktif ürünleri getir
        criteria.and("isActive").is(true);
        
        // Sorguyu oluştur ve çalıştır
        Query searchQuery = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(searchQuery, Product.class);
    }
    
    // ==================== FUZZY SEARCH ====================
    
    /**
     * Bulanık arama (Fuzzy Search)
     * Yazım hatalarını tolere eden arama
     * 
     * @param query Aranacak metin
     * @return SearchHits<Product> - Arama sonuçları
     */
    public SearchHits<Product> fuzzySearch(String query) {
        // İsimde fuzzy arama yap
        Criteria criteria = new Criteria("name").fuzzy(query);
        // Sadece aktif ürünleri getir
        criteria.and("isActive").is(true);
        
        // Sorguyu oluştur ve çalıştır
        Query searchQuery = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(searchQuery, Product.class);
    }
    
    // ==================== AGGREGATION İŞLEMLERİ ====================
    
    /**
     * Kategori bazında istatistikler
     * 
     * @param category Kategori adı
     * @return Belirtilen kategorideki ürünler
     */
    public List<Product> getProductsByCategoryWithStats(String category) {
        // Bu metod kategori bazında istatistikler de döndürebilir
        return productRepository.findByCategory(category);
    }
    
    // ==================== BULK İŞLEMLER ====================
    
    /**
     * Toplu ürün oluşturma
     * 
     * @param requests Ürün oluşturma istekleri listesi
     * @return Oluşturulan ürünlerin listesi
     */
    public List<Product> createProducts(List<ProductRequest> requests) {
        // Her request'i Product'a çevir
        List<Product> products = requests.stream()
                .map(this::createProduct)
                .toList();
        
        // Toplu kaydet
        return saveAllProducts(products);
    }
    
    /**
     * Toplu ürün kaydetme
     * 
     * @param products Kaydedilecek ürünler listesi
     * @return Kaydedilen ürünlerin listesi
     */
    public List<Product> saveAllProducts(List<Product> products) {
        List<Product> savedProducts = new ArrayList<>();
        productRepository.saveAll(products).forEach(savedProducts::add);
        return savedProducts;
    }
} 