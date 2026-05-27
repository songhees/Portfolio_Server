package com.chiup.portfolio.controller;

import com.chiup.portfolio.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 서버 상태 확인 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
public class HealthController {

    /**
     * 서버 상태를 반환합니다.
     */
    @GetMapping("/health")
    public ApiResponse<HealthResponse> health() {
        return ApiResponse.success(new HealthResponse("UP"));
    }

    /** 서버 상태 응답 DTO */
    public record HealthResponse(String status) {
    }
}
