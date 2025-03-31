# Assignment

## Project Description
- **구현 내용**
  - [카테고리 / 브랜드 / 상품] Table DDL 정의 및 Sample 데이터 생성
  - API 개발
    - 카테고리 별 최저가격 브랜드와 상품 가격, 총액 조회 API
    - 단일 브랜드로 모든 카테고리 상품 구매시 최저가격 브랜드와 카티고리 상품 가격 총액 조회 API
    - 특정 카테고리에 대해 최저 / 최고 가격 브랜드와 상품 가격 조회 API
    - 브랜드 추가 API
    - 상품 [추가 / 업데이트 / 삭제] API

## Prerequisite
* JDK 17 (Gradle)
* docker

---
## Quick Start

### Database 설치 및 Setting

#### 1. H2 database 설치 및 셋팅

```
# h2 database download
$ wget https://github.com/h2database/h2database/releases/download/version-2.3.232/h2-2024-08-11.zip

# h2 file 압축 풀기
$ unzip h2-2024-08-11.zip

# h2 database 실행
$ chmod +x ./h2/bin/h2.sh
$ ./h2/bin/h2.sh
 ```
1.2 H2 console 접속
- driver class: org.h2.Driver
- jdbc URL : jdbc:h2:~/test
- user name : test
- password : test

1.3 Table DDL 정의 및 data insert
- 아래 경로의 두 file H2 Console에서 실행
  - src/main/resource/static/schema.sql 
  - src/main/resource/static/data.sql

1.4 H2 console 종료 및 TCP 모드 실행
 ```
$ command + x
$ ./h2/bin/h2.sh -tcp -web -pg
 ```

#### 2. redis 설치 및 실행
```
$ docker-compose up -d
```

---
### Application Build 및 Test 
#### 1. Build Application
```
$ ./gradlew clean build -x test
```
#### 2. Run Application
```
$ java -Ddd.env=local -jar build/libs/assignment-1.0.jar
```

#### 3.Product Add API로 product 데이터 Insert
```
$ ./src/main/resources/static/data.sh
```

#### 4. Application Unit 및 Integration 테스트
```
$ ./gradlew test
```

---
### Swagger UI
* local : http://localhost/swagger-ui/index.html

