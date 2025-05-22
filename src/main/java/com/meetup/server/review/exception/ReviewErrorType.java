package com.meetup.server.review.exception;

import com.meetup.server.global.support.error.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorType implements ErrorType {
    ALREADY_REVIEW_EXISTS(HttpStatus.CONFLICT, "작성한 리뷰가 이미 존재합니다."),
    ;

    private final HttpStatus status;

    private final String message;
}
