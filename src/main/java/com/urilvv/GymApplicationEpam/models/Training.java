package com.urilvv.GymApplicationEpam.models;

import com.urilvv.GymApplicationEpam.enums.TrainingType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

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

    public String getTraineeId() {
        return traineeId;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public LocalDate getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(LocalDate trainingTime) {
        this.trainingTime = trainingTime;
    }

    public LocalTime getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(LocalTime trainingDuration) {
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
