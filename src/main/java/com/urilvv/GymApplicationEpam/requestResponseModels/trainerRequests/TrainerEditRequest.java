package com.urilvv.GymApplicationEpam.requestResponseModels.trainerRequests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TrainerEditRequest {

    private String firstName;
    private String lastName;
    private String spec;

}