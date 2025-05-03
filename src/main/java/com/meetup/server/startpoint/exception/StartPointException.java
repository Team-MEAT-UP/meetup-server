package com.meetup.server.startpoint.exception;

import com.meetup.server.global.support.error.ErrorType;
import com.meetup.server.global.support.error.GlobalException;

public class StartPointException extends GlobalException {

    public StartPointException(ErrorType errorType) {
        super(errorType);
    }
}
