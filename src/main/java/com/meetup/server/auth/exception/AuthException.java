package com.meetup.server.auth.exception;

import com.meetup.server.global.support.error.ErrorType;
import com.meetup.server.global.support.error.GlobalException;

public class AuthException extends GlobalException {

    public AuthException(ErrorType errorType) {
        super(errorType);
    }
}
