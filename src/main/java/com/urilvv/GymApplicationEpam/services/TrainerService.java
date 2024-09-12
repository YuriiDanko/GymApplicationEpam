package com.urilvv.GymApplicationEpam.services;

import com.urilvv.GymApplicationEpam.models.Trainer;

public interface TrainerService {

    Trainer createTrainer(String firstName, String lastName,
                                 boolean isActive, String spec);

    Trainer selectTrainer(String userId);

    Trainer editTrainer(String userId, String firstName, String lastName,
                               boolean isActive, String spec);

}
