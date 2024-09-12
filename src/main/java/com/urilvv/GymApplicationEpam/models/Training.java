package com.urilvv.GymApplicationEpam.models;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class Training {

    private String trainingId;
    private final String traineeId;
    private final String trainerId;
    private String trainingName;
    private TrainingType trainingType;
    private LocalDate trainingTime;
    private LocalTime trainingDuration;

    public Training(String traineeId, String trainerId, String trainingName, TrainingType trainingType, LocalDate trainingTime, LocalTime trainingDuration) {
        this.trainingId = UUID.randomUUID().toString();
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingTime = trainingTime;
        this.trainingDuration = trainingDuration;
    }

    @Override
    public String toString() {
        return "Training{" +
                "trainingId='" + trainingId + '\'' +
                ", traineeId='" + traineeId + '\'' +
                ", trainerId='" + trainerId + '\'' +
                ", trainingName='" + trainingName + '\'' +
                ", trainingType=" + trainingType +
                ", trainingTime=" + trainingTime +
                ", trainingDuration=" + trainingDuration +
                '}';
    }

}
