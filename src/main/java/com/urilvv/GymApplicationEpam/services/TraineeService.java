package com.urilvv.GymApplicationEpam.services;

import com.urilvv.GymApplicationEpam.daos.TraineeDAO;
import com.urilvv.GymApplicationEpam.models.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TraineeService {

    private final TraineeDAO traineeDAO;

    @Autowired
    public TraineeService(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    public Trainee createTrainee(String firstName, String lastName,
                                 boolean isActive, LocalDate dateOfBirth, String address) {
        return traineeDAO.createTrainee(firstName, lastName, firstName + "." + lastName,
                isActive, dateOfBirth, address);
    }

    public Trainee editTrainee(String userId, String firstName, String lastName,
                               boolean isActive, LocalDate dateOfBirth, String address) {
        return traineeDAO.editTrainee(userId, firstName, lastName, firstName + "." + lastName,
                isActive, dateOfBirth, address);
    }

    public boolean deleteTrainee(String userId) {
        return traineeDAO.deleteTrainee(userId);
    }

    public Trainee selectTrainee(String userId) {
        return traineeDAO.selectTrainee(userId);
    }

}
