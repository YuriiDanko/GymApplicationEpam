package com.urilvv.GymApplicationEpam.services;

import com.urilvv.GymApplicationEpam.daos.TraineeDAO;
import com.urilvv.GymApplicationEpam.models.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TraineeService {

    private TraineeDAO traineeDAO;

    @Autowired
    public TraineeService(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    public Trainee createTrainee(String firstName, String lastName, String password, String username,
                                 boolean isActive, LocalDate dateOfBirth, String address) {
        return traineeDAO.createTrainee(firstName, lastName, password, username,
                isActive, dateOfBirth, address);
    }

    public Trainee editTrainee(String userId, String firstName, String lastName, String password, String username,
                               boolean isActive, LocalDate dateOfBirth, String address) {
        return traineeDAO.editTrainee(userId, firstName, lastName, password,
                username, isActive, dateOfBirth, address);
    }

    public boolean deleteTrainee(String userId) {
        return traineeDAO.deleteTrainee(userId);
    }

    public Trainee selectTrainee(String userId) {
        return traineeDAO.selectTrainee(userId);
    }

}
