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
- Config — layihənin konfiqurasiyası və mühitlərin idarə olunması
- ApiClient — API sorğularının göndərilməsi
- Pages — veb səhifələrin obyekt modelləri
- UI Tests — istifadəçi interfeysi testləri
- API Tests — API testləri
- Utilities — testlər üçün köməkçi utilitlər

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
        |   |
        |   |-- ui/
        |   |   |-- LoginTest.java
        |   |   |-- HomePageTest.java
        |   |   |-- ProductPageTest.java
        |   |   |-- CartPageTest.java
        |   |   |-- EnvironmentTest.java
        |   |
        |   |-- com/veysauction/tests/api/
        |       |-- CarApiTest.java
        |       |-- CarApiCrudTest.java
        |       |-- CarCrudApiTest.java
        |       |-- CarNotFoundTest.java
        |       |-- CarPatchTest.java
        |       |-- CarPutTest.java
        |       |-- CarApiUiIntegrationTest.java
        |       |-- CarApiUiSecondIntegrationTest.java
        |
        |-- test/resources/
            |
            |-- testdata/
                |-- login-data.json

## 4. UI Automation

UI testləri Selenium WebDriver və TestNG vasitəsilə hazırlanıb.

Testlər aşağıdakı funksionallıqları yoxlayır.

### Login

- Düzgün məlumatlarla giriş
- Yanlış məlumatlarla giriş
- Boş sahələrlə giriş
- Mənfi autentifikasiya ssenariləri
- JSON məlumatlarından istifadə etməklə giriş testləri

### Home Page

- Əsas səhifənin açılması
- Avtomobillərin göstərilməsi
- Avtomobilin seçilməsi
- Avtomobil axtarışı
- Axtarış nəticələrinin yoxlanılması

### Product Page

- Avtomobil səhifəsinin açılması
- Avtomobil məlumatlarının yoxlanılması
- Qiymətin yoxlanılması
- Avtomobilin səbətə əlavə edilməsi
- "Place a Bid" düyməsinin yoxlanılması

### Cart

- Səbətin açılması
- Boş səbətin yoxlanılması
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

Layihədə API və UI inteqrasiya testləri həyata keçirilib.

### Integration Scenario 1

Testin işləmə ardıcıllığı:

1. API vasitəsilə avtomobillərin siyahısı əldə edilir.
2. API cavabından Hyundai Elantra avtomobili müəyyən edilir.
3. Selenium vasitəsilə VeysAuction veb tətbiqi açılır.
4. API-dən əldə edilən avtomobil modeli UI-də axtarılır.
5. Avtomobilin istifadəçi interfeysində göstərilməsi yoxlanılır.

### Integration Scenario 2

İkinci inteqrasiya ssenarisində API və UI məlumatlarının uyğunluğu yoxlanılır.

Testin işləmə ardıcıllığı:

1. API vasitəsilə avtomobillərin siyahısı əldə edilir.
2. API cavabından Hyundai Elantra avtomobili müəyyən edilir.
3. Elantra avtomobilinin qiyməti API-dən götürülür.
4. VeysAuction veb tətbiqi açılır.
5. Hyundai Elantra avtomobili UI-də tapılır.
6. API-dən alınan qiymət UI-də göstərilən qiymətlə müqayisə edilir.

Bu test API və UI arasında məlumat uyğunluğunun yoxlanılmasını nümayiş etdirir.

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
- Parallel test icrası üçün ThreadLocal WebDriver istifadəsi
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
- @DataProvider

Testlər aşağıdakı qruplara ayrılır:

- smoke
- regression

Bütün testlərin konfiqurasiyası layihənin kök qovluğundakı testng.xml faylında yerləşir.

TestNG konfiqurasiyasında parallel execution istifadə olunur.

Testlər bir neçə thread vasitəsilə paralel şəkildə icra edilə bilər.

## 10. DataProvider və JSON Test Data

Layihədə TestNG DataProvider istifadə olunur.

Login test məlumatları ayrıca JSON faylında saxlanılır:

    src/test/resources/testdata/login-data.json

JSON faylında müxtəlif müsbət və mənfi login ssenariləri saxlanılır.

DataProvider vasitəsilə JSON məlumatları test metoduna ötürülür.

Bu yanaşma eyni test məntiqindən istifadə etməklə müxtəlif test məlumatlarının yoxlanılmasına imkan verir.

## 11. Retry Mechanism

Layihədə TestNG RetryAnalyzer istifadə olunur.

Retry mexanizmi uğursuz olan testin müəyyən sayda yenidən icra edilməsinə imkan verir.

Bu yanaşma müvəqqəti UI və ya environment problemləri səbəbindən yaranan test uğursuzluqlarının idarə olunmasına kömək edir.

## 12. Multiple Environments

Layihədə müxtəlif test mühitləri üçün konfiqurasiya dəstəyi mövcuddur.

Dəstəklənən mühitlər:

- local
- qa

Local mühit:

    http://127.0.0.1:8000

QA mühiti:

    https://veysauction.onrender.com

Mühit `env` sistem parametri vasitəsilə seçilir.

Məsələn:

    mvn test -Denv=qa

Əgər `env` parametri göstərilməzsə, local mühit istifadə olunur.

## 13. Testlərin işə salınması

Layihəni IntelliJ IDEA vasitəsilə açın.

Bütün testləri işə salmaq üçün layihənin kök qovluğunda yerləşən `testng.xml` faylını açaraq Run əməliyyatını seçin.

Maven vasitəsilə testlərin işə salınması:

    mvn test

QA mühitində testləri işə salmaq üçün:

    mvn test -Denv=qa

Headless rejimdə testləri işə salmaq üçün:

    mvn test -Dheadless=true

Testlərin icrası zamanı nəticələr IntelliJ IDEA-nın Run bölməsində göstərilir.

## 14. Test nəticələri

Layihənin son tam test icrasının nəticələri:

| Göstərici | Nəticə |
|---|---:|
| Ümumi test sayı | 26 |
| Uğurlu testlər | 26 |
| Uğursuz testlər | 0 |
| Buraxılmış testlər | 0 |

Son tam test icrasında bütün 26 test uğurla tamamlanıb.

Test nəticəsi:

    26 Passed, 0 Failed, 0 Skipped

## 15. Konfiqurasiya

Layihənin əsas konfiqurasiya parametrləri Config.java faylında yerləşir.

Test edilən veb tətbiq:

https://veysauction.onrender.com

Local test mühiti:

http://127.0.0.1:8000

API sorğularının hazırlanması üçün ApiClient sinfindən istifadə olunur.

## 16. Təhlükəsizlik

Layihənin GitHub repozitoriyasında məxfi məlumatlar saxlanılmamalıdır.

Bura daxildir:

- Parollar
- API açarları
- Tokenlər
- Digər məxfi məlumatlar

.gitignore faylı vasitəsilə lazımsız və lokal fayllar versiya nəzarətindən kənarda saxlanılır.

## 17. Layihənin məqsədi

Bu layihə aşağıdakı praktiki bacarıqları nümayiş etdirmək üçün hazırlanıb:

- UI Automation Testing
- API Automation Testing
- UI və API inteqrasiya testləri
- Page Object Model
- Selenium WebDriver
- Rest Assured
- TestNG
- DataProvider
- JSON Test Data
- Retry Mechanism
- Parallel Execution
- Multiple Environments
- Maven
- Git və GitHub
- Müsbət və mənfi test ssenarilərinin hazırlanması

## 18. Layihə üçün hazırlanmış test tətbiqi

Bu avtomatlaşdırma layihəsinin fərqli xüsusiyyətlərindən biri ondan ibarətdir ki, testlərin həyata keçirilməsi üçün VeysAuction veb tətbiqi xüsusi olaraq hazırlanıb.

VeysAuction avtomobil hərracı konsepsiyası əsasında hazırlanmış test tətbiqidir. Tətbiq üzərində real istifadəçi ssenarilərinə uyğun funksionallıqlar yaradılıb və daha sonra həmin funksionallıqlar UI və API səviyyəsində avtomatlaşdırılmış testlərlə yoxlanılıb.

Beləliklə, layihə yalnız hazır bir veb saytın test edilməsindən ibarət deyil. Əvvəlcə test ediləcək tətbiq hazırlanıb, daha sonra həmin tətbiq üçün avtomatlaşdırılmış QA test infrastrukturu qurulub.

Tətbiq:
https://veysauction.onrender.com

## 19. Yekun

VeysAuctionAutomation layihəsində UI, API və inteqrasiya testləri hazırlanıb.

Layihədə Page Object Model, DataProvider, JSON test məlumatları, Retry Mechanism, Parallel Execution və Multiple Environments kimi əlavə avtomatlaşdırma yanaşmalarından istifadə olunub.

Son tam test icrasında 26 testdən 26-sı uğurla keçib.

Test nəticəsi:

    26 Passed, 0 Failed, 0 Skipped