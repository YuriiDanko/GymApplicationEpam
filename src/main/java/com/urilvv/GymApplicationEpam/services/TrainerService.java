package com.urilvv.GymApplicationEpam.services;

import com.urilvv.GymApplicationEpam.daos.TrainerDAO;
import com.urilvv.GymApplicationEpam.models.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {

    private final TrainerDAO trainerDAO;

    @Autowired
    public TrainerService(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    public Trainer createTrainer(String firstName, String lastName,
                                 boolean isActive, String spec) {
        return trainerDAO.createTrainer(firstName, lastName, firstName + "." + lastName,
                isActive, spec);
    }

    public Trainer selectTrainer(String userId) {
        return trainerDAO.selectTrainer(userId);
    }

    public Trainer editTrainer(String userId, String firstName, String lastName,
                               boolean isActive, String spec) {
        return trainerDAO.editTrainer(userId, firstName, lastName,
                firstName + "." + lastName, isActive, spec);
    }

}
