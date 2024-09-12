package com.urilvv.GymApplicationEpam.services.servicesImpl;

import com.urilvv.GymApplicationEpam.daos.TraineeDAO;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.services.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDAO traineeDAO;

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
