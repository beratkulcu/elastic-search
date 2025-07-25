# 🚀 Elasticsearch Demo API - Ürün Yönetimi ve Arama Sistemi

Bu proje, **Spring Boot** ve **Elasticsearch** kullanarak geliştirilmiş modern bir ürün yönetimi ve arama API'sidir. Gelişmiş arama özellikleri, CRUD işlemleri ve Swagger dokümantasyonu ile birlikte gelir.

## 📋 İçindekiler

- [Özellikler](#-özellikler)
- [Teknolojiler](#-teknolojiler)
- [Sistem Gereksinimleri](#-sistem-gereksinimleri)
- [Kurulum](#-kurulum)
- [Kullanım](#-kullanım)
- [API Dokümantasyonu](#-api-dokümantasyonu)
- [Örnekler](#-örnekler)
- [Katkıda Bulunma](#-katkıda-bulunma)
- [Lisans](#-lisans)

## ✨ Özellikler

### 🔍 Arama Özellikleri
- **Basit Metin Arama**: Ürün adı ve açıklamasında arama
- **Gelişmiş Arama**: Çoklu kriterlere göre filtreleme
- **Bulanık Arama (Fuzzy Search)**: Yazım hatalarını tolere eden arama
- **Kategori Filtreleme**: Kategoriye göre ürün listeleme
- **Fiyat Aralığı Filtreleme**: Belirli fiyat aralığındaki ürünler
- **Tag Bazlı Arama**: Etiketlere göre ürün bulma

### 🛠️ CRUD İşlemleri
- **Create**: Yeni ürün oluşturma
- **Read**: Ürün listeleme ve detay görüntüleme
- **Update**: Ürün bilgilerini güncelleme
- **Delete**: Ürün silme
- **Bulk Operations**: Toplu ürün ekleme

### 📊 Diğer Özellikler
- **Swagger/OpenAPI Dokümantasyonu**: Otomatik API dokümantasyonu
- **Validation**: Bean validation ile veri doğrulama
- **Docker Desteği**: Kolay kurulum ve deployment
- **PostgreSQL Entegrasyonu**: İsteğe bağlı veritabanı desteği

## 🛠️ Teknolojiler

### Backend
- **Java 17**: Modern Java programlama dili
- **Spring Boot 3.2.0**: Enterprise Java framework
- **Spring Data Elasticsearch**: Elasticsearch entegrasyonu
- **Spring Web**: REST API geliştirme
- **Spring Validation**: Veri doğrulama

### Veritabanı & Arama
- **Elasticsearch 8.12.0**: Arama ve analitik motoru
- **Kibana 8.12.0**: Elasticsearch görselleştirme aracı
- **PostgreSQL 15**: İlişkisel veritabanı (isteğe bağlı)

### Araçlar & Kütüphaneler
- **Lombok**: Boilerplate kod azaltma
- **Jackson**: JSON işleme
- **SpringDoc OpenAPI**: Swagger dokümantasyonu
- **Docker & Docker Compose**: Containerization

## 💻 Sistem Gereksinimleri

### Minimum Gereksinimler
- **Java 17** veya üzeri
- **Docker** ve **Docker Compose**
- **Maven 3.6+**
- **4GB RAM** (Elasticsearch için)
- **2GB Disk Alanı**

### Önerilen Gereksinimler
- **Java 21**
- **8GB RAM**
- **10GB Disk Alanı**
- **SSD Depolama**

## 🚀 Kurulum
