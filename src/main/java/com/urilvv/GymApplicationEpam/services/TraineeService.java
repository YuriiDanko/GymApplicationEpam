package com.urilvv.GymApplicationEpam.services;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TraineeService {

    Trainee createTrainee(String firstName, String lastName,
                          boolean isActive, LocalDate dateOfBirth, String address);

    Trainee editTrainee(String userId, String firstName, String lastName,
                        LocalDate dateOfBirth, String address);

    boolean deleteTrainee(String userId);

    Optional<Trainee> selectTrainee(String userId);

    void changePassword(String userId, String newPassword, String oldPassword);

    void changeActiveStatus(String userId);

    List<Trainee> getAllTrainees();

    List<Training> getTraineeTrainingList(String traineeUsername, LocalDate from,
                                          LocalDate to, String trainerUsername, TrainingType type);

}
