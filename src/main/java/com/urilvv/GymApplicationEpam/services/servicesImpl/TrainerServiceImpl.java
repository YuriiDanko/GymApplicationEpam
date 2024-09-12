package com.urilvv.GymApplicationEpam.services.servicesImpl;

import com.urilvv.GymApplicationEpam.daos.TrainerDAO;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.services.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDAO trainerDAO;

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
