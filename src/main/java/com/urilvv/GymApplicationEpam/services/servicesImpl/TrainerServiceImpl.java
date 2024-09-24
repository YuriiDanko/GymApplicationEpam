package com.urilvv.GymApplicationEpam.services.servicesImpl;

import com.urilvv.GymApplicationEpam.daos.TrainerDAO;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;
import com.urilvv.GymApplicationEpam.services.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDAO trainerDAO;

    public Trainer createTrainer(String firstName, String lastName,
                                 boolean isActive, String spec) {
        return trainerDAO.createTrainer(firstName, lastName, firstName + "." + lastName,
                isActive, spec);
    }

    public Optional<Trainer> selectTrainer(String username) {
        return trainerDAO.selectTrainer(username);
    }

    public Trainer editTrainer(String userId, String firstName, String lastName, String spec) {
        return trainerDAO.editTrainer(userId, firstName, lastName, spec);
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerDAO.getAllTrainers();
    }

    @Override
    public void changePassword(String userId, String newPassword, String oldPassword) {
        trainerDAO.changePassword(userId, newPassword, oldPassword);
    }

    @Override
    public void changeActiveStatus(String userId) {
        trainerDAO.changeActiveStatus(userId);
    }

    @Override
    public List<Training> getTrainerTrainingList(String trainerUsername, LocalDate from, LocalDate to, String traineeUsername) {
        return trainerDAO.getTrainerTrainingList(trainerUsername, from, to, traineeUsername);
    }

}
