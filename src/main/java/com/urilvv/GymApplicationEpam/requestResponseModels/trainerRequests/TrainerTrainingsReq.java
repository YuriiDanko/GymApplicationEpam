package com.urilvv.GymApplicationEpam.requestResponseModels.trainerRequests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TrainerTrainingsReq {

    private LocalDate from;
    private LocalDate to;
    private String traineeName;

}
