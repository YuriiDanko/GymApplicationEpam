package com.urilvv.GymApplicationEpam.utils;

import com.urilvv.GymApplicationEpam.dtos.TraineeDTO;
import com.urilvv.GymApplicationEpam.dtos.TrainerDTO;
import com.urilvv.GymApplicationEpam.dtos.TrainingDTO;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public TraineeDTO mapTrainee(Trainee trainee) {
        return TraineeDTO.builder()
                .firstName(trainee.getFirstName())
                .lastName(trainee.getLastName())
                .dateOfBirth(trainee.getDateOfBirth())
                .userId(trainee.getUserId())
                .username(trainee.getUsername())
                .isActive(trainee.isActive())
                .address(trainee.getAddress())
                .build();
    }

    public TrainerDTO mapTrainer(Trainer trainer) {
        return TrainerDTO.builder()
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .spec(trainer.getTrainerSpec())
                .userId(trainer.getUserId())
                .username(trainer.getUsername())
                .isActive(trainer.isActive())
                .build();
    }

    public TrainingDTO mapTraining(Training training) {
        return TrainingDTO.builder()
                .trainingId(training.getTrainingId())
                .traineeDTO(mapTrainee(training.getTrainee()))
                .trainerDTO(mapTrainer(training.getTrainer()))
                .trainingName(training.getTrainingName())
                .trainingDuration(training.getTrainingDuration())
                .trainingType(training.getTrainingType())
                .build();
    }

}
