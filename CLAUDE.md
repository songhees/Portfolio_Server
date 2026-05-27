# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 개발 환경

- **Java 21** (LTS)
- **Spring Boot 3.3.5**
- **Gradle 8.10.2** (Kotlin DSL)
- **PostgreSQL 16** (Docker로 로컬 실행)

로컬 PostgreSQL은 Docker Compose로 관리한다:
```bash
docker compose up -d    # DB 시작
docker compose down     # DB 중지
```

## 주요 명령어

```bash
# 서버 실행 (dev 프로파일 필수)
./gradlew bootRun --args='--spring.profiles.active=dev'

# 전체 빌드 (테스트 + AsciiDoc 생성 포함)
./gradlew build

# 테스트만 실행
./gradlew test

# 특정 테스트 클래스 실행
./gradlew test --tests "com.chiup.portfolio.SomeTest"

# Checkstyle 검사
./gradlew checkstyleMain
./gradlew checkstyleTest

# REST Docs AsciiDoc 생성 (test 선행 필요)
./gradlew asciidoctor
```

Windows에서는 `./gradlew` 대신 `.\gradlew.bat` 사용.

## 아키텍처

### 패키지 구조

모든 소스는 `com.chiup.portfolio` 하위에 위치한다. 도메인 기능은 **도메인별 패키지**로 구성하고, 각 패키지 안에 레이어를 둔다:

```
com.chiup.portfolio/
├── common/                  # 전 도메인 공통
│   ├── entity/BaseTimeEntity   # createdAt/updatedAt JPA Auditing 자동 관리
│   ├── exception/              # ErrorCode(enum), BusinessException, GlobalExceptionHandler
│   └── response/ApiResponse    # 모든 API 응답 래퍼
├── config/                  # JpaConfig(Auditing 활성화), SwaggerConfig
└── {domain}/                # 도메인별 패키지
    ├── domain/              # @Entity
    ├── repository/          # JpaRepository
    ├── service/             # 비즈니스 로직
    ├── controller/          # @RestController
    └── dto/                 # request / response DTO
```

### 공통 응답 형식

모든 컨트롤러는 `ApiResponse<T>`로 응답을 감싼다:
- 성공: `ApiResponse.success(data)` 또는 `ApiResponse.success(message, data)`
- 실패: `GlobalExceptionHandler`가 자동으로 `ApiResponse.fail(message)` 반환

### 예외 처리 흐름

1. 도메인 예외 → `BusinessException` 상속 후 `ErrorCode` 주입
2. `GlobalExceptionHandler`가 `BusinessException`, `BindException`, `Exception` 순으로 처리
3. 새 에러 코드는 `ErrorCode` enum에 추가 (`HttpStatus`, 코드 문자열, 메시지 3개 필드)

### Entity 작성 규칙

- 모든 엔티티는 `BaseTimeEntity`를 상속해 `createdAt` / `updatedAt` 자동 관리
- `JpaConfig`의 `@EnableJpaAuditing`이 활성화되어 있어야 동작

## 프로파일

| 프로파일 | DB | ddl-auto |
|---|---|---|
| `dev` | PostgreSQL localhost:5432/portfolio_dev | `create-drop` |
| `prod` | 환경변수 `${DB_URL}` | `validate` |
| `test` | H2 인메모리 (PostgreSQL 호환 모드) | `create-drop` |

테스트는 항상 `@ActiveProfiles("test")`를 붙여 H2를 사용한다.

## API 하나를 완성하는 절차

새 API를 추가할 때 아래 순서를 반드시 따른다.

### 1단계 — 구현

도메인 패키지 안에 레이어 순서대로 작성한다: `domain` → `repository` → `service` → `dto` → `controller`.

- 컨트롤러 반환 타입은 항상 `ApiResponse<T>`
- 도메인 예외는 `BusinessException`을 상속하고 `ErrorCode`에 코드 추가
- `@Entity`는 `BaseTimeEntity` 상속

### 2단계 — 테스트 작성

`RestDocsSupport`를 상속해서 컨트롤러 테스트를 작성한다.
테스트 파일 위치: `src/test/java/com/chiup/portfolio/{domain}/`

```java
class ProjectControllerTest extends RestDocsSupport {

    @Test
    void createProject() throws Exception {
        // given
        String requestBody = """
                {
                  "title": "포트폴리오",
                  "description": "설명"
                }
                """;

        // when & then
        mockMvc.perform(post("/projects")          // RestDocumentationRequestBuilders.post
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andDo(document("create-project",  // 스니펫 식별자 (kebab-case)
                        resource(ResourceSnippetParameters.builder()
                                .tag("Project")        // Swagger 그룹 태그
                                .summary("프로젝트 등록")
                                .description("새 프로젝트를 등록합니다.")
                                .requestFields(
                                        fieldWithPath("title")
                                                .type(JsonFieldType.STRING)
                                                .description("프로젝트 제목"),
                                        fieldWithPath("description")
                                                .type(JsonFieldType.STRING)
                                                .description("프로젝트 설명")
                                )
                                .responseFields(
                                        fieldWithPath("success")
                                                .type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("data.id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("생성된 프로젝트 ID")
                                )
                                .build()
                        )
                ));
    }
}
```

**import 규칙**
- `get/post/put/delete` → `org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders` (MockMvcRequestBuilders가 아님)
- `document` → `com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document`
- `resource` → `com.epages.restdocs.apispec.ResourceDocumentation.resource`

### 3단계 — 테스트 실행 및 문서 생성

```bash
# 테스트 실행 → 스니펫 생성 → openapi3.yaml 생성
.\gradlew.bat openapi3
```

성공하면 `build/api-spec/openapi3.yaml`이 갱신된다.

### 4단계 — Swagger UI 확인

```bash
# 서버 실행
.\gradlew.bat bootRun --args='--spring.profiles.active=dev'
```

http://localhost:8080/swagger-ui.html 에서 새 API가 추가됐는지 확인한다.

---

## API 문서

- **Swagger UI**: http://localhost:8080/swagger-ui.html
  - 스펙 소스: `build/api-spec/openapi3.yaml` (REST Docs 테스트로 생성)
- **스니펫 원본**: `build/generated-snippets/`
- **AsciiDoc 템플릿**: `src/docs/asciidoc/index.adoc`

## Checkstyle 주요 규칙

`config/checkstyle/checkstyle.xml` 기준:
- star import 금지, 미사용 import 금지
- 탭 문자 금지 (스페이스만 허용)
- 파일 당 최대 500줄
- public 메서드에 Javadoc 필수 (param/return 태그 생략 허용)
- 매직 넘버 금지 (-1, 0, 1, 2 제외)
- `equals()` 재정의 시 `hashCode()` 함께 재정의 강제
