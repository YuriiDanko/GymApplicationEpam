package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Training;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Component
public class TrainingDAO {

    private Map<String, Training> trainingStorage;

    public TrainingDAO(@Qualifier("trainingStorage") Map<String, Training> trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    public Training createTraining(String traineeId, String trainerId, String trainingName,
                                   TrainingType trainingType, LocalDate trainingTime, LocalTime trainingDuration) {
        Training training = new Training(traineeId, trainerId, trainingName,
                trainingType, trainingTime, trainingDuration);

        trainingStorage.put(training.getTrainingId(), training);

        return training;
    }

    public Training selectTraining(String trainingId) {
        return trainingStorage.get(trainingId);
    }

}
