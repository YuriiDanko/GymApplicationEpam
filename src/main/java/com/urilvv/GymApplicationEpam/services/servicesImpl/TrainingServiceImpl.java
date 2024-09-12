package com.urilvv.GymApplicationEpam.services.servicesImpl;

import com.urilvv.GymApplicationEpam.daos.TrainingDAO;
import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Training;
import com.urilvv.GymApplicationEpam.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDAO trainingDAO;

    public Training createTraining(String traineeId, String trainerId, String trainingName,
                                   TrainingType trainingType, LocalDate trainingTime, LocalTime trainingDuration) {
        return trainingDAO.createTraining(traineeId, trainerId, trainingName, trainingType, trainingTime, trainingDuration);
    }

    public Training selectTraining(String trainingId) {
        return trainingDAO.selectTraining(trainingId);
    }

}
