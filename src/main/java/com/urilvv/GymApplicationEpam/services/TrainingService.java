package com.urilvv.GymApplicationEpam.services;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Training;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TrainingService {

    Training createTraining(String traineeId, String trainerId, String trainingName,
                                   TrainingType trainingType, LocalDate trainingTime, LocalTime trainingDuration);

    Training selectTraining(String trainingId);

}
