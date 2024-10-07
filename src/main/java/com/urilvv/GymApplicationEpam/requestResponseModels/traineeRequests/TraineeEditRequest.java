package com.urilvv.GymApplicationEpam.requestResponseModels.traineeRequests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TraineeEditRequest {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;

}
