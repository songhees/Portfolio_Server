package com.chiup.portfolio.support;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Spring REST Docs + epages restdocs-api-spec 통합 테스트 베이스 클래스.
 *
 * <p>모든 API 문서화 테스트는 이 클래스를 상속해서 작성합니다.</p>
 *
 * <pre>
 * class SomeControllerTest extends RestDocsSupport {
 *     &#64;Test
 *     void someApi() throws Exception {
 *         mockMvc.perform(get("/some"))
 *                .andExpect(status().isOk())
 *                .andDo(MockMvcRestDocumentationWrapper.document("some-api",
 *                        resource(ResourceSnippetParameters.builder()
 *                                .tag("태그명")
 *                                .summary("API 한줄 요약")
 *                                .build()
 *                        )
 *                ));
 *     }
 * }
 * </pre>
 */
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class RestDocsSupport {

    protected MockMvc mockMvc;

    @BeforeEach
    void setUpRestDocs(
            @Autowired WebApplicationContext context,
            RestDocumentationContextProvider provider) {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider)
                        .operationPreprocessors()
                        .withRequestDefaults(
                                modifyUris().scheme("https").host("api.portfolio.com").removePort(),
                                prettyPrint()
                        )
                        .withResponseDefaults(prettyPrint())
                )
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }
}
