
# VeysAuctionAutomation

VeysAuction veb tətbiqi üçün hazırlanmış UI və API testlərinin avtomatlaşdırılması layihəsi.

## 1. Layihə haqqında

VeysAuctionAutomation Java proqramlaşdırma dili əsasında hazırlanmış QA Automation layihəsidir.

Layihənin məqsədi VeysAuction veb tətbiqinin funksionallığını UI və API səviyyəsində avtomatlaşdırılmış testlər vasitəsilə yoxlamaqdır.

Layihədə aşağıdakı texnologiya və yanaşmalardan istifadə olunur:

- Java
- Selenium WebDriver
- TestNG
- Rest Assured
- Maven
- Page Object Model (POM)
- IntelliJ IDEA
- Git və GitHub

Testlər müsbət və mənfi ssenariləri əhatə edir.

## 2. Test arxitekturası

Layihə Page Object Model arxitekturası əsasında təşkil edilib.

Əsas komponentlər:

- BaseTest — UI testlərinin ümumi idarə olunması
- DriverManager — WebDriver idarəetməsi
- Config — layihənin konfiqurasiyası
- ApiClient — API sorğularının göndərilməsi
- Pages — veb səhifələrin obyekt modelləri
- UI Tests — istifadəçi interfeysi testləri
- API Tests — API testləri

## 3. Layihənin strukturu

    VeysAuctionAutomation/
    |
    |-- .gitignore
    |-- pom.xml
    |-- README.md
    |-- testng.xml
    |
    |-- src/
        |
        |-- main/java/com/veysauction/
        |   |
        |   |-- api/
        |   |   |-- ApiClient.java
        |   |
        |   |-- base/
        |   |   |-- BaseTest.java
        |   |
        |   |-- config/
        |   |   |-- Config.java
        |   |
        |   |-- driver/
        |   |   |-- DriverManager.java
        |   |
        |   |-- pages/
        |       |-- LoginPage.java
        |       |-- HomePage.java
        |       |-- ProductPage.java
        |       |-- CartPage.java
        |
        |-- test/java/
            |
            |-- ui/
            |   |-- LoginTest.java
            |   |-- HomePageTest.java
            |   |-- ProductPageTest.java
            |   |-- CartPageTest.java
            |
            |-- com/veysauction/tests/api/
                |-- CarApiTest.java
                |-- CarApiCrudTest.java
                |-- CarCrudApiTest.java
                |-- CarNotFoundTest.java
                |-- CarPatchTest.java
                |-- CarPutTest.java
                |-- CarApiUiIntegrationTest.java

## 4. UI Automation

UI testləri Selenium WebDriver və TestNG vasitəsilə hazırlanıb.

Testlər aşağıdakı funksionallıqları yoxlayır.

### Login

- Düzgün məlumatlarla giriş
- Yanlış məlumatlarla giriş
- Boş sahələrlə giriş
- Mənfi autentifikasiya ssenariləri

### Home Page

- Əsas səhifənin açılması
- Avtomobillərin göstərilməsi
- Avtomobilin seçilməsi

### Product Page

- Avtomobil səhifəsinin açılması
- Avtomobil məlumatlarının yoxlanılması
- Qiymətin yoxlanılması
- Avtomobilin səbətə əlavə edilməsi

### Cart

- Səbətin açılması
- Səbətdəki məhsulların yoxlanılması
- Məhsul sayının dəyişdirilməsi
- Məhsulun səbətdən silinməsi
- Ümumi məbləğin yoxlanılması

UI testlərinin sayı: 13.

## 5. API Automation

API testləri Rest Assured və TestNG istifadə edilərək hazırlanıb.

Aşağıdakı HTTP metodları yoxlanılır:

- GET
- POST
- PUT
- PATCH
- DELETE

Əsas test ssenariləri:

- Avtomobillərin siyahısının əldə edilməsi
- Konkret avtomobilin məlumatlarının əldə edilməsi
- Yeni avtomobilin yaradılması
- Avtomobil məlumatlarının tam yenilənməsi
- Avtomobil məlumatlarının qismən yenilənməsi
- Avtomobilin silinməsi
- Mövcud olmayan avtomobilin sorğulanması
- Mövcud olmayan avtomobilin silinməsi

API testlərində aşağıdakılar yoxlanılır:

- HTTP status kodları
- Response Body
- JSON məlumatları
- Response Headers
- Response Time

Testlərdə müsbət və mənfi ssenarilər mövcuddur.

## 6. UI + API Integration Testing

Layihədə API və UI inteqrasiya testi həyata keçirilib.

Testin işləmə ardıcıllığı:

1. API vasitəsilə avtomobillərin siyahısı əldə edilir.
2. API cavabından Hyundai Elantra avtomobili müəyyən edilir.
3. Selenium vasitəsilə VeysAuction veb tətbiqi açılır.
4. API-dən əldə edilən avtomobil modeli UI-də axtarılır.
5. Avtomobilin istifadəçi interfeysində göstərilməsi yoxlanılır.

Bu test API-dən alınan məlumatların UI testində istifadəsini nümayiş etdirir.

## 7. Page Object Model

Page Object Model testlərin strukturlaşdırılması üçün istifadə olunur.

Layihədə aşağıdakı Page Object sinifləri mövcuddur:

- LoginPage
- HomePage
- ProductPage
- CartPage

Bu yanaşmanın üstünlükləri:

- Kod təkrarının azaldılması
- Testlərin oxunaqlılığının artırılması
- Testlərin dəstəklənməsinin asanlaşdırılması
- Locator və test məntiqinin ayrılması

## 8. DriverManager və BaseTest

DriverManager ChromeDriver-in yaradılması və idarə olunmasına cavabdehdir.

Əsas funksiyaları:

- ChromeDriver yaradılması
- ChromeOptions konfiqurasiyası
- Headless rejiminin dəstəklənməsi
- Timeout parametrlərinin təyin edilməsi
- Brauzerin bağlanması

BaseTest sinfində TestNG annotasiyalarından istifadə olunur.

@BeforeMethod — hər UI testindən əvvəl WebDriver-in hazırlanması.

@AfterMethod — hər UI testindən sonra brauzerin bağlanması.

## 9. TestNG

Testlərin icrası və idarə olunması TestNG vasitəsilə həyata keçirilir.

İstifadə olunan annotasiyalar:

- @Test
- @BeforeMethod
- @AfterMethod

Testlər aşağıdakı qruplara ayrılır:

- smoke
- regression

Bütün testlərin konfiqurasiyası layihənin kök qovluğundakı testng.xml faylında yerləşir.

## 10. Testlərin işə salınması

Layihəni IntelliJ IDEA vasitəsilə açın.

Bütün testləri işə salmaq üçün testng.xml faylını açaraq Run əməliyyatını seçin.

Maven vasitəsilə testlərin işə salınması:

    mvn test

Testlərin icrası zamanı nəticələr IntelliJ IDEA-nın Run bölməsində göstərilir.

## 11. Test nəticələri

Layihənin son tam test icrasının nəticələri:

| Göstərici | Nəticə |
|---|---:|
| Ümumi test sayı | 21 |
| Uğurlu testlər | 21 |
| Uğursuz testlər | 0 |
| Buraxılmış testlər | 0 |

Bütün 21 test uğurla tamamlanıb.

## 12. Konfiqurasiya

Layihənin əsas konfiqurasiya parametrləri Config.java faylında yerləşir.

Test edilən veb tətbiq:

https://veysauction.onrender.com

API sorğularının hazırlanması üçün ApiClient sinfindən istifadə olunur.

## 13. Təhlükəsizlik

Layihənin GitHub repozitoriyasında məxfi məlumatlar saxlanılmamalıdır.

Bura daxildir:

- Parollar
- API açarları
- Tokenlər
- Digər məxfi məlumatlar

.gitignore faylı vasitəsilə lazımsız və lokal fayllar versiya nəzarətindən kənarda saxlanılır.

## 14. Layihənin məqsədi

Bu layihə aşağıdakı praktiki bacarıqları nümayiş etdirmək üçün hazırlanıb:

- UI Automation Testing
- API Automation Testing
- UI və API inteqrasiya testləri
- Page Object Model
- Selenium WebDriver
- Rest Assured
- TestNG
- Maven
- Git və GitHub
- Müsbət və mənfi test ssenarilərinin hazırlanması

## 15. Yekun

VeysAuctionAutomation layihəsində UI, API və inteqrasiya testləri hazırlanıb.

Son tam test icrasında 21 testdən 21-i uğurla keçib.

Test nəticəsi: 21 Passed, 0 Failed, 0 Skipped.