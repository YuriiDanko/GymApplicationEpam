package com.urilvv.GymApplicationEpam.exceptions;

import org.springframework.http.HttpStatus;

public class PasswordMatchersException extends CustomBaseException {

    public PasswordMatchersException(String message) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message));
    }

}
