package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.models.Trainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TrainerDAO {

    private Map<String, Trainer> trainerStorage;

    public TrainerDAO(@Qualifier("trainerStorage") Map<String, Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    public Trainer createTrainer(String firstName, String lastName, String password,
                                 String username, boolean isActive, String spec){
        Trainer trainer = new Trainer(firstName,
                lastName,
                password,
                username,
                isActive,
                spec);

        return trainerStorage.put(trainer.getUserId(), trainer);
    }

    public Trainer editTrainer(String userId, String firstName, String lastName, String password,
                               String username, boolean isActive, String spec){
        Trainer edited = new Trainer(firstName,
                lastName,
                password,
                username,
                isActive,
                spec);

        return trainerStorage.put(userId, edited);
    }

    public Trainer selectTrainer(String userId){
        return trainerStorage.get(userId);
    }

}
