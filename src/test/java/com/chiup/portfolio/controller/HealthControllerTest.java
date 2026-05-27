package com.chiup.portfolio.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chiup.portfolio.support.RestDocsSupport;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

class HealthControllerTest extends RestDocsSupport {

    @Test
    void health() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("UP"))
                .andDo(document("health",
                        resource(ResourceSnippetParameters.builder()
                                .tag("Health")
                                .summary("서버 상태 확인")
                                .description("서버가 정상 동작 중인지 확인합니다.")
                                .responseFields(
                                        fieldWithPath("success")
                                                .type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("data.status")
                                                .type(JsonFieldType.STRING)
                                                .description("서버 상태 (`UP` 고정)")
                                )
                                .build()
                        )
                ));
    }
}
