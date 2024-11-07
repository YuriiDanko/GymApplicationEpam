package com.urilvv.GymApplicationEpam.exceptions;

import org.springframework.http.HttpStatus;

public class LoginBlockedException extends CustomBaseException{

    public LoginBlockedException() {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("LoginAttempt is blocked due to 3 failed attempts. Try again later."));
    }

}
