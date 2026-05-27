package com.chiup.portfolio.common.exception;

import lombok.Getter;

/**
 * 비즈니스 로직 예외 기반 클래스.
 * 도메인별 예외는 이 클래스를 상속해서 사용합니다.
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
