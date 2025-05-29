package com.meetup.server.global.support.error;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final ErrorType errorType;

    public GlobalException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
