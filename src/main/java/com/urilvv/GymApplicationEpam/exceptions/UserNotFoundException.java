package com.urilvv.GymApplicationEpam.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomBaseException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, new SimpleResponse("User was not found."));
    }

}
