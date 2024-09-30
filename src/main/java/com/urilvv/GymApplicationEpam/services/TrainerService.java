package com.urilvv.GymApplicationEpam.services;

import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainerService {

    Trainer createTrainer(String firstName, String lastName,
                          boolean isActive, String spec);

    Optional<Trainer> selectTrainer(String username);

    Trainer editTrainer(String userId, String firstName, String lastName, String spec);

    List<Trainer> getAllTrainers();

    void changePassword(String userId, String newPassword, String oldPassword);

    void changeActiveStatus(String userId);

    List<Training> getTrainerTrainingList(String trainerUsername, LocalDate from, LocalDate to, String traineeUsername);

}
