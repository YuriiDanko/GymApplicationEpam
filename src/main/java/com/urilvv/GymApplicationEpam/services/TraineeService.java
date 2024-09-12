package com.urilvv.GymApplicationEpam.services;

import com.urilvv.GymApplicationEpam.models.Trainee;

import java.time.LocalDate;

public interface TraineeService {

    Trainee createTrainee(String firstName, String lastName,
                                 boolean isActive, LocalDate dateOfBirth, String address);

    Trainee editTrainee(String userId, String firstName, String lastName,
                               boolean isActive, LocalDate dateOfBirth, String address);

    boolean deleteTrainee(String userId);

    Trainee selectTrainee(String userId);

}
