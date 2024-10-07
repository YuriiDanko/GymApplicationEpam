package com.urilvv.GymApplicationEpam.requestResponseModels.traineeRequests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TraineeTrainingsReq {

    private LocalDate from;
    private LocalDate to;
    private String trainerName;
    private String trainingType;

}
