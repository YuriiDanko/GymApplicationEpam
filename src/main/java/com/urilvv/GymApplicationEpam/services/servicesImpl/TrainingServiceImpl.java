package com.urilvv.GymApplicationEpam.services.servicesImpl;

import com.urilvv.GymApplicationEpam.daos.TrainingDAO;
import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;
import com.urilvv.GymApplicationEpam.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDAO trainingDAO;

    public Training createTraining(Trainee trainee, Trainer trainer, String trainingName,
                                   TrainingType trainingType, LocalDate trainingTime, LocalTime trainingDuration) {
        return trainingDAO.createTraining(trainee, trainer, trainingName, trainingType, trainingTime, trainingDuration);
    }

    public Optional<Training> selectTraining(String trainingId) {
        return trainingDAO.selectTraining(trainingId);
    }

    public boolean deleteTraining(String userId) {
        return trainingDAO.deleteTraining(userId);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingDAO.getAllTrainings();
    }

}
