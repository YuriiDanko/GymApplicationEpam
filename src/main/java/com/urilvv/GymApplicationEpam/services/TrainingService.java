package com.urilvv.GymApplicationEpam.services;

import com.urilvv.GymApplicationEpam.daos.TrainingDAO;
import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TrainingService {

    private TrainingDAO trainingDAO;

    @Autowired
    public TrainingService(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    public Training createTraining(String traineeId, String trainerId, String trainingName,
                                   TrainingType trainingType, LocalDate trainingTime, LocalTime trainingDuration) {
        return trainingDAO.createTraining(traineeId, trainerId, trainingName, trainingType, trainingTime, trainingDuration);
    }

    public Training selectTraining(String trainingId) {
        return trainingDAO.selectTraining(trainingId);
    }

}
