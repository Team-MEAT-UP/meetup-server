package com.meetup.server.global.support.error;

import lombok.Getter;

@Getter
public class ErrorMessage {

    private final String code;

    private final String message;

    public ErrorMessage(ErrorType errorType) {
        this.code = errorType.name();
        this.message = errorType.getMessage();
    }

}
