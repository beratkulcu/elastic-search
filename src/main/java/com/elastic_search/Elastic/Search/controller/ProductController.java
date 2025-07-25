package com.elastic_search.Elastic.Search.controller;

import com.elastic_search.Elastic.Search.dto.ProductRequest;
import com.elastic_search.Elastic.Search.dto.SearchRequest;
import com.elastic_search.Elastic.Search.model.Product;
import com.elastic_search.Elastic.Search.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductController - Ürün REST API Kontrolcüsü
 * @author Berat Kulcu
 * @version 1.0
 * @since 2025-07-25
 */
@RestController // Spring: Bu sınıfın REST controller olduğunu belirtir
@RequestMapping("/api/products") // Tüm endpoint'lerin base path'i
@RequiredArgsConstructor // Lombok: Final field'lar için constructor oluşturur
@Tag(name = "Product Management", description = "Ürün yönetimi ve arama API'leri") // Swagger: API grubu açıklaması
public class ProductController {
    private final ProductService productService;

    /**
     * Yeni ürün oluşturma endpoint'i
     * POST /api/products
     * 
     * @param request Ürün oluşturma isteği (JSON)
     * @return ResponseEntity<Product> - Oluşturulan ürün (201 Created)
     */
    @PostMapping
    @Operation(summary = "Yeni ürün oluştur", description = "Yeni bir ürün oluşturur ve Elasticsearch'e kaydeder")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ürün başarıyla oluşturuldu",
            content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "400", description = "Geçersiz veri"),
        @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request) {
        // Service katmanını çağır ve ürün oluştur
        Product product = productService.createProduct(request);
        // 201 Created status kodu ile döndür
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    /**
     * Tüm ürünleri listeleme endpoint'i
     * GET /api/products
     * 
     * @return ResponseEntity<List<Product>> - Tüm ürünlerin listesi (200 OK)
     */
    @GetMapping
    @Operation(summary = "Tüm ürünleri listele", description = "Elasticsearch'teki tüm ürünleri getirir")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ürünler başarıyla getirildi"),
        @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<Product>> getAllProducts() {
        // Service katmanından tüm ürünleri al
        List<Product> products = productService.getAllProducts();
        // 200 OK status kodu ile döndür
        return ResponseEntity.ok(products);
    }

    /**
     * ID ile ürün getirme endpoint'i
     * GET /api/products/{id}
     * 
     * @param id Ürün ID'si (path variable)
     * @return ResponseEntity<Product> - Bulunan ürün (200 OK) veya 404 Not Found
     */
    @GetMapping("/{id}")
    @Operation(summary = "ID ile ürün getir", description = "Belirtilen ID'ye sahip ürünü getirir")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ürün bulundu"),
        @ApiResponse(responseCode = "404", description = "Ürün bulunamadı")
    })
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "Ürün ID'si", required = true) @PathVariable String id) {
        // Service katmanından ürünü ara
        return productService.getProductById(id)
                .map(ResponseEntity::ok) // Ürün bulunduysa 200 OK ile döndür
                .orElse(ResponseEntity.notFound().build()); // Bulunamadıysa 404 Not Found
    }

    /**
     * Ürün güncelleme endpoint'i
     * PUT /api/products/{id}
     * 
     * @param id Güncellenecek ürünün ID'si (path variable)
     * @param request Güncelleme isteği (JSON)
     * @return ResponseEntity<Product> - Güncellenmiş ürün (200 OK) veya 404 Not Found
     */
    @PutMapping("/{id}")
    @Operation(summary = "Ürün güncelle", description = "Belirtilen ID'ye sahip ürünü günceller")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ürün başarıyla güncellendi"),
        @ApiResponse(responseCode = "404", description = "Ürün bulunamadı"),
        @ApiResponse(responseCode = "400", description = "Geçersiz veri")
    })
    public ResponseEntity<Product> updateProduct(
            @Parameter(description = "Ürün ID'si", required = true) @PathVariable String id,
            @Valid @RequestBody ProductRequest request) {
        // Service katmanında ürünü güncelle
        return productService.updateProduct(id, request)
                .map(ResponseEntity::ok) // Güncelleme başarılıysa 200 OK ile döndür
                .orElse(ResponseEntity.notFound().build()); // Ürün bulunamadıysa 404 Not Found
    }

    /**
     * Ürün silme endpoint'i
     * DELETE /api/products/{id}
     * 
     * @param id Silinecek ürünün ID'si (path variable)
     * @return ResponseEntity<Void> - 204 No Content (başarılı) veya 404 Not Found
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Ürün sil", description = "Belirtilen ID'ye sahip ürünü siler")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ürün başarıyla silindi"),
        @ApiResponse(responseCode = "404", description = "Ürün bulunamadı")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Ürün ID'si", required = true) @PathVariable String id) {
        // Service katmanında ürünü sil
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build(); // Başarılı silme: 204 No Content
        }
        return ResponseEntity.notFound().build(); // Ürün bulunamadı: 404 Not Found
    }

    // ==================== ARAMA İŞLEMLERİ ====================
    
    /**
     * Basit metin araması endpoint'i
     * GET /api/products/search?query=aranan_kelime
     * 
     * @param query Aranacak metin (query parameter)
     * @return ResponseEntity<List<Product>> - Arama sonuçları (200 OK)
     */
    @GetMapping("/search")
    @Operation(summary = "Basit arama", description = "Ürün adında veya açıklamasında arama yapar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arama sonuçları"),
        @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<Product>> searchProducts(
            @Parameter(description = "Arama terimi", required = true) @RequestParam String query) {
        // Service katmanında basit arama yap
        List<Product> products = productService.searchProducts(query);
        return ResponseEntity.ok(products);
    }

    /**
     * Gelişmiş arama endpoint'i
     * POST /api/products/search/advanced
     * 
     * @param request Gelişmiş arama isteği (JSON - query, category, minPrice, maxPrice)
     * @return ResponseEntity<SearchHits<Product>> - Arama sonuçları ve skorlar (200 OK)
     */
    @PostMapping("/search/advanced")
    @Operation(summary = "Gelişmiş arama", description = "Fiyat aralığı, kategori ve diğer kriterlere göre arama yapar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arama sonuçları"),
        @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<SearchHits<Product>> advancedSearch(@Valid @RequestBody SearchRequest request) {
        // Service katmanında gelişmiş arama yap
        SearchHits<Product> results = productService.advancedSearch(request);
        return ResponseEntity.ok(results);
    }

    /**
     * Bulanık arama endpoint'i (Fuzzy Search)
     * GET /api/products/search/fuzzy?query=aranan_kelime
     * 
     * @param query Aranacak metin (query parameter)
     * @return ResponseEntity<SearchHits<Product>> - Arama sonuçları ve skorlar (200 OK)
     */
    @GetMapping("/search/fuzzy")
    @Operation(summary = "Bulanık arama", description = "Yazım hatalarını tolere eden arama yapar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arama sonuçları"),
        @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<SearchHits<Product>> fuzzySearch(
            @Parameter(description = "Arama terimi", required = true) @RequestParam String query) {
        // Service katmanında fuzzy arama yap
        SearchHits<Product> results = productService.fuzzySearch(query);
        return ResponseEntity.ok(results);
    }

    // ==================== TOPLU İŞLEMLER ====================
    
    /**
     * Toplu ürün ekleme endpoint'i
     * POST /api/products/bulk
     * 
     * @param requests Ürün oluşturma istekleri listesi (JSON array)
     * @return ResponseEntity<List<Product>> - Oluşturulan ürünlerin listesi (201 Created)
     */
    @PostMapping("/bulk")
    @Operation(summary = "Toplu ürün ekleme", description = "Birden fazla ürünü tek seferde ekler")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ürünler başarıyla eklendi"),
        @ApiResponse(responseCode = "400", description = "Geçersiz veri"),
        @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<Product>> createProducts(@Valid @RequestBody List<ProductRequest> requests) {
        // Service katmanında toplu ürün oluştur
        List<Product> products = productService.createProducts(requests);
        // 201 Created status kodu ile döndür
        return ResponseEntity.status(HttpStatus.CREATED).body(products);
    }

    // ==================== FİLTRELEME İŞLEMLERİ ====================
    
    /**
     * Kategoriye göre ürün filtreleme endpoint'i
     * GET /api/products/category/{category}
     * 
     * @param category Kategori adı (path variable)
     * @return ResponseEntity<List<Product>> - Kategorideki ürünler (200 OK)
     */
    @GetMapping("/category/{category}")
    @Operation(summary = "Kategoriye göre ürünler", description = "Belirtilen kategorideki ürünleri getirir")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Kategori ürünleri"),
        @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<Product>> getProductsByCategory(
            @Parameter(description = "Kategori adı", required = true) @PathVariable String category) {
        // Service katmanından kategoriye göre ürünleri al
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    /**
     * Fiyat aralığına göre ürün filtreleme endpoint'i
     * GET /api/products/price-range?minPrice=100&maxPrice=1000
     * 
     * @param minPrice Minimum fiyat (query parameter)
     * @param maxPrice Maksimum fiyat (query parameter)
     * @return ResponseEntity<List<Product>> - Fiyat aralığındaki ürünler (200 OK)
     */
    @GetMapping("/price-range")
    @Operation(summary = "Fiyat aralığına göre ürünler", description = "Belirtilen fiyat aralığındaki ürünleri getirir")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fiyat aralığı ürünleri"),
        @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @Parameter(description = "Minimum fiyat", required = true) @RequestParam Double minPrice,
            @Parameter(description = "Maksimum fiyat", required = true) @RequestParam Double maxPrice) {
        // Service katmanından fiyat aralığına göre ürünleri al
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }
} 