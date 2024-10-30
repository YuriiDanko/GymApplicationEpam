package com.urilvv.GymApplicationEpam.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomBaseException extends RuntimeException{

    private HttpStatus statusCode;
    private SimpleResponse response;

    public CustomBaseException(HttpStatus statusCode, SimpleResponse response) {
        this.statusCode = statusCode;
        this.response = response;
    }
}
