package com.meetup.server.user.exception;

import com.meetup.server.global.support.error.ErrorType;
import com.meetup.server.global.support.error.GlobalException;

public class UserException extends GlobalException {

    public UserException(ErrorType errorType) {
        super(errorType);
    }
}
