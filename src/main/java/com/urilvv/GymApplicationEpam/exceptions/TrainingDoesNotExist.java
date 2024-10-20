package com.urilvv.GymApplicationEpam.exceptions;

import org.springframework.http.HttpStatus;

public class TrainingDoesNotExist extends CustomBaseException {

    public TrainingDoesNotExist() {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse("There is no Training with such training_id"));
    }

}
