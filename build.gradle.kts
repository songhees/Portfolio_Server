import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("checkstyle")
    id("org.asciidoctor.jvm.convert") version "3.3.2"

    // REST Docs → OpenAPI 3.0 YAML 변환
    id("com.epages.restdocs-api-spec") version "0.19.4"
}

group = "com.chiup"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val snippetsDir = file("build/generated-snippets")

// ────────────────────────────────────────────────
//  의존성
// ────────────────────────────────────────────────
dependencies {
    // ── Web ──────────────────────────────────────
    implementation("org.springframework.boot:spring-boot-starter-web")

    // ── Data ─────────────────────────────────────
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")

    // ── Validation ───────────────────────────────
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // ── Lombok ───────────────────────────────────
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // ── Swagger UI (springdoc) ────────────────────
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // ── Test ─────────────────────────────────────
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    // REST Docs → OpenAPI 스펙 생성
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.19.4")

    testRuntimeOnly("com.h2database:h2")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

// ────────────────────────────────────────────────
//  Checkstyle
// ────────────────────────────────────────────────
checkstyle {
    toolVersion = "10.17.0"
    configFile = file("config/checkstyle/checkstyle.xml")
    isIgnoreFailures = false
}

// ────────────────────────────────────────────────
//  REST Docs
// ────────────────────────────────────────────────
tasks.test {
    outputs.dir(snippetsDir)
    useJUnitPlatform()
    jvmArgs("-Dfile.encoding=UTF-8")
    systemProperty("file.encoding", "UTF-8")
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
    baseDirFollowsSourceDir()
}

// ────────────────────────────────────────────────
//  OpenAPI 3.0 YAML 생성 (restdocs-api-spec)
//  ./gradlew openapi3 → build/api-spec/openapi3.yaml
// ────────────────────────────────────────────────
openapi3 {
    setServer("http://localhost:8080")
    title = "Portfolio API"
    description = "포트폴리오 서버 REST API 명세서"
    version = "v1.0.0"
    format = "yaml"
    outputDirectory = "build/api-spec"
}

// ────────────────────────────────────────────────
//  JAR 빌드 시 생성된 spec 포함 (정적 리소스로 제공)
// ────────────────────────────────────────────────
tasks.named<BootJar>("bootJar") {
    dependsOn("openapi3")
    // 생성된 openapi3.yaml → JAR 내 static/ 에 포함 → /openapi3.yaml 로 서빙
    from("build/api-spec") {
        into("BOOT-INF/classes/static")
    }
}
