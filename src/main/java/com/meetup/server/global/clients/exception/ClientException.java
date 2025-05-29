package com.meetup.server.global.clients.exception;

import com.meetup.server.global.support.error.ErrorType;
import com.meetup.server.global.support.error.GlobalException;

public class ClientException extends GlobalException {

    public ClientException(ErrorType errorType) {
        super(errorType);
    }
}
