package com.urilvv.GymApplicationEpam.requestResponseModels.register;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterTrainerRequest extends RegisterRequest {

    private String spec;

    public RegisterTrainerRequest(String firstName, String lastName, String spec) {
        super(firstName, lastName);
        this.spec = spec;
    }

}
