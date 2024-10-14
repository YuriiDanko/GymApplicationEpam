package com.urilvv.GymApplicationEpam.requestResponseModels.register;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterTraineeRequest extends RegisterRequest {

    private LocalDate dateOfBirth;
    private String address;

    public RegisterTraineeRequest(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        super(firstName, lastName);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

}
