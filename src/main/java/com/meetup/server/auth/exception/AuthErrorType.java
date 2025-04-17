package com.meetup.server.auth.exception;

import com.meetup.server.global.support.error.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorType implements ErrorType {
    INVALID_KAKAO_ACCOUNT(HttpStatus.UNAUTHORIZED, "카카오 계정이 유효하지 않습니다."),

    ;

    private final HttpStatus status;

    private final String message;
}
