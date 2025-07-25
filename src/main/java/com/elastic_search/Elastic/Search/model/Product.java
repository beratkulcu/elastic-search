package com.elastic_search.Elastic.Search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.List;

/**
 * Product Entity - Ürün Varlık Sınıfı
 * 
 * Bu sınıf Elasticsearch'te saklanacak ürün verilerini temsil eder.
 * @Document annotation'ı ile Elasticsearch index'ini belirtir.
 * 
 * @author Berat Kulcu
 * @version 1.0
 * @since 2025-07-25
 */
@Data // Lombok: Getter, Setter, toString, equals, hashCode metodlarını otomatik oluşturur
@NoArgsConstructor // Lombok: Parametresiz constructor oluşturur
@AllArgsConstructor // Lombok: Tüm field'lar için parametreli constructor oluşturur
@Document(indexName = "products") // Elasticsearch index adını belirtir
public class Product {
    
    /**
     * Ürünün benzersiz kimliği (ID)
     * Elasticsearch tarafından otomatik olarak oluşturulur
     */
    @Id // Spring Data: Bu field'ın primary key olduğunu belirtir
    private String id;
    
    /**
     * Ürün adı
     * Text tipinde saklanır ve standart analyzer kullanır
     * Bu sayede arama yapılabilir
     */
    @Field(type = FieldType.Text, analyzer = "standard") // Elasticsearch: Text tipi, standart analiz
    private String name;
    
    /**
     * Ürün açıklaması
     * Text tipinde saklanır ve standart analyzer kullanır
     * Uzun metin aramaları için optimize edilmiştir
     */
    @Field(type = FieldType.Text, analyzer = "standard") // Elasticsearch: Text tipi, standart analiz
    private String description;
    
    /**
     * Ürün kategorisi
     * Keyword tipinde saklanır - tam eşleşme aramaları için
     * Örnek: "Elektronik", "Gaming", "Ev Aletleri"
     */
    @Field(type = FieldType.Keyword) // Elasticsearch: Keyword tipi - tam eşleşme
    private String category;
    
    /**
     * Ürün fiyatı
     * Double tipinde saklanır - sayısal aramalar için
     * BigDecimal kullanarak hassas para hesaplamaları yapılır
     */
    @Field(type = FieldType.Double) // Elasticsearch: Double tipi - sayısal değer
    private BigDecimal price;
    
    /**
     * Stok miktarı
     * Integer tipinde saklanır - tam sayı değerler için
     */
    @Field(type = FieldType.Integer) // Elasticsearch: Integer tipi - tam sayı
    private Integer stock;
    
    /**
     * Ürün etiketleri (tags)
     * Keyword listesi olarak saklanır
     * Örnek: ["telefon", "apple", "5G", "titanium"]
     */
    @Field(type = FieldType.Keyword) // Elasticsearch: Keyword listesi
    private List<String> tags;
    
    /**
     * Ürünün aktif olup olmadığı
     * Boolean tipinde saklanır
     * true: Ürün satışta, false: Ürün satışta değil
     */
    @Field(type = FieldType.Boolean) // Elasticsearch: Boolean tipi
    private Boolean isActive;
} 