package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Component
@Slf4j
public class TrainingDAO {

    private final Map<String, Training> trainingStorage;

    public TrainingDAO(@Qualifier("trainingStorage") Map<String, Training> trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    public Training createTraining(String traineeId, String trainerId, String trainingName,
                                   TrainingType trainingType, LocalDate trainingTime, LocalTime trainingDuration) {
        Training training = new Training(traineeId, trainerId, trainingName,
                trainingType, trainingTime, trainingDuration);

        trainingStorage.put(training.getTrainingId(), training);

        log.info("Training session with given id - " + training.getTrainingId() + " was scheduled.");

        return training;
    }

    public Training selectTraining(String trainingId) {
        return trainingStorage.get(trainingId);
    }

}
