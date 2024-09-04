package com.urilvv.GymApplicationEpam.models;

import com.urilvv.GymApplicationEpam.enums.TrainingType;

import java.time.LocalDate;
import java.time.LocalTime;

public class Training {

    private String traineeId;
    private String trainerId;
    private String trainingName;
    private TrainingType trainingType;
    private LocalDate trainingTime;
    private LocalTime trainingDuration;

}
