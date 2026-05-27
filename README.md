# Portfolio Server

Spring Boot 기반 포트폴리오 백엔드 서버입니다.

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 21 (LTS) |
| Framework | Spring Boot 3.3.5 |
| Build | Gradle 8.10.2 (Kotlin DSL) |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Docs | Spring REST Docs + Swagger UI |
| Test | JUnit 5 + H2 (인메모리) |

## 프로젝트 구조

```
src/main/java/com/chiup/portfolio/
├── PortfolioApplication.java   # 진입점
├── common/
│   ├── entity/                 # BaseTimeEntity (Auditing)
│   ├── exception/              # 예외 처리 (ErrorCode, BusinessException, GlobalExceptionHandler)
│   └── response/               # ApiResponse 공통 래퍼
├── config/                     # JPA, Swagger 등 설정 클래스
└── {domain}/                   # 도메인별 패키지 (controller / service / repository / domain / dto)
```

## 개발 환경 실행

### 1. PostgreSQL 준비

```sql
CREATE DATABASE portfolio_dev;
CREATE USER portfolio WITH PASSWORD 'portfolio';
GRANT ALL PRIVILEGES ON DATABASE portfolio_dev TO portfolio;
```

### 2. 환경 변수 (선택)

```
DB_USERNAME=portfolio
DB_PASSWORD=portfolio
```

### 3. 애플리케이션 실행

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## API 문서

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **REST Docs**: `build/docs/asciidoc/index.html` (빌드 후)

## 빌드

```bash
./gradlew build          # 전체 빌드 + 테스트
./gradlew test           # 테스트만 실행
./gradlew checkstyleMain # Checkstyle 검사
```
