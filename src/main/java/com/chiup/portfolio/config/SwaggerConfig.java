package com.chiup.portfolio.config;

import org.springframework.context.annotation.Configuration;

/**
 * Swagger UI 설정.
 *
 * <p>API 스펙은 Spring REST Docs 테스트로 자동 생성됩니다.
 * 아래 명령으로 openapi3.yaml 을 먼저 생성해야 Swagger UI가 표시됩니다:</p>
 * <pre>./gradlew openapi3</pre>
 *
 * <p>접속 URL: http://localhost:8080/swagger-ui.html</p>
 * <p>스펙 파일: build/api-spec/openapi3.yaml (dev) / static/docs/openapi3.yaml (JAR)</p>
 */
@Configuration
public class SwaggerConfig {
    // springdoc.swagger-ui.url 설정으로 생성된 spec 파일을 로드합니다.
    // 별도 OpenAPI Bean 불필요 — application.yml 참고
}
