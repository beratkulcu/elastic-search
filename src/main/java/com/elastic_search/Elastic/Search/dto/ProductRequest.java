package com.elastic_search.Elastic.Search.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * ProductRequest - Ürün Oluşturma/Güncelleme İsteği DTO'su
 * 
 * Bu sınıf API'den gelen ürün oluşturma ve güncelleme isteklerini temsil eder.
 * Validation kuralları burada tanımlanır.
 * 
 * @author Berat Kulcu
 * @version 1.0
 * @since 2025-07-25
 */
@Data // Lombok: Getter, Setter, toString, equals, hashCode metodlarını otomatik oluşturur
@NoArgsConstructor // Lombok: Parametresiz constructor oluşturur
@AllArgsConstructor // Lombok: Tüm field'lar için parametreli constructor oluşturur
public class ProductRequest {
    
    /**
     * Ürün adı
     * Boş olamaz, minimum 2 karakter olmalı
     */
    @NotBlank(message = "Ürün adı boş olamaz") // Validation: Boş string veya sadece boşluk olamaz
    @Size(min = 2, max = 100, message = "Ürün adı 2-100 karakter arasında olmalı") // Validation: Uzunluk kontrolü
    private String name;
    
    /**
     * Ürün açıklaması
     * Boş olamaz, minimum 10 karakter olmalı
     */
    @NotBlank(message = "Ürün açıklaması boş olamaz") // Validation: Boş string veya sadece boşluk olamaz
    @Size(min = 10, max = 1000, message = "Ürün açıklaması 10-1000 karakter arasında olmalı") // Validation: Uzunluk kontrolü
    private String description;
    
    /**
     * Ürün kategorisi
     * Boş olamaz, minimum 2 karakter olmalı
     */
    @NotBlank(message = "Kategori boş olamaz") // Validation: Boş string veya sadece boşluk olamaz
    @Size(min = 2, max = 50, message = "Kategori 2-50 karakter arasında olmalı") // Validation: Uzunluk kontrolü
    private String category;
    
    /**
     * Ürün fiyatı
     * Pozitif sayı olmalı, 0'dan büyük olmalı
     */
    @NotNull(message = "Fiyat boş olamaz") // Validation: Null olamaz
    @DecimalMin(value = "0.01", message = "Fiyat 0.01'den büyük olmalı") // Validation: Minimum değer kontrolü
    @DecimalMax(value = "999999.99", message = "Fiyat 999999.99'dan küçük olmalı") // Validation: Maksimum değer kontrolü
    private BigDecimal price;
    
    /**
     * Stok miktarı
     * Negatif olamaz, 0 veya daha büyük olmalı
     */
    @NotNull(message = "Stok miktarı boş olamaz") // Validation: Null olamaz
    @Min(value = 0, message = "Stok miktarı 0'dan küçük olamaz") // Validation: Minimum değer kontrolü
    @Max(value = 999999, message = "Stok miktarı 999999'dan büyük olamaz") // Validation: Maksimum değer kontrolü
    private Integer stock;
    
    /**
     * Ürün etiketleri (tags)
     * Boş liste olabilir, her tag 1-20 karakter arasında olmalı
     */
    @NotNull(message = "Etiketler boş olamaz") // Validation: Null olamaz (boş liste olabilir)
    private List<@Size(min = 1, max = 20, message = "Her etiket 1-20 karakter arasında olmalı") String> tags;
    
    /**
     * Ürünün aktif olup olmadığı
     * Varsayılan değer true olmalı
     */
    @NotNull(message = "Aktiflik durumu boş olamaz") // Validation: Null olamaz
    private Boolean isActive = true; // Varsayılan değer: true
} 