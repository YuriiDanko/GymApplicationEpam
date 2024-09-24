package com.urilvv.GymApplicationEpam.services.servicesImpl;

import com.urilvv.GymApplicationEpam.daos.TraineeDAO;
import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Training;
import com.urilvv.GymApplicationEpam.services.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
                               LocalDate dateOfBirth, String address) {
        return traineeDAO.editTrainee(userId, firstName, lastName,
                dateOfBirth, address);
    }

    public boolean deleteTrainee(String userId) {
        return traineeDAO.deleteTrainee(userId);
    }

    public Optional<Trainee> selectTrainee(String userId) {
        return traineeDAO.selectTrainee(userId);
    }

    public void changePassword(String userId, String newPassword, String oldPassword) {
        traineeDAO.changePassword(userId, newPassword, oldPassword);
    }

    public void changeActiveStatus(String userId) {
        traineeDAO.changeActiveStatus(userId);
    }

    public List<Trainee> getAllTrainees() {
        return traineeDAO.getAllTrainees();
    }

    @Override
    public List<Training> getTraineeTrainingList(String traineeUsername, LocalDate from,
                                                 LocalDate to, String trainerUsername, TrainingType type) {
        return traineeDAO.getTraineeTrainingList(traineeUsername, from, to, trainerUsername, type);
    }

}
