package com.urilvv.GymApplicationEpam.utils;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;
import com.urilvv.GymApplicationEpam.requestResponseModels.trainingRequests.CreateTrainingRequest;
import com.urilvv.GymApplicationEpam.services.TrainingService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TrainingFacade {

    @PersistenceContext
    private final EntityManager entityManager;
    private final TrainingService trainingService;

    public Training createTraining(CreateTrainingRequest createRequest) {
        Trainee trainee = entityManager.find(Trainee.class, createRequest.getTraineeId());
        Trainer trainer = entityManager.find(Trainer.class, createRequest.getTrainerId());
        return trainingService.createTraining(trainee, trainer, createRequest.getTrainingName(),
                TrainingType.getType(createRequest.getTrainingType()), createRequest.getTrainingTime(), createRequest.getTrainingDuration());
    }

    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

}
