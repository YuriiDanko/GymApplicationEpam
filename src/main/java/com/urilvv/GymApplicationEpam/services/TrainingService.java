package com.urilvv.GymApplicationEpam.services;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface TrainingService {

    Training createTraining(Trainee trainee, Trainer trainer, String trainingName,
                            TrainingType trainingType, LocalDate trainingTime, LocalTime trainingDuration);

    Optional<Training> selectTraining(String trainingId);

    boolean deleteTraining(String userId);

}
