package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.models.Trainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TrainerDAO implements Validation {

    private Map<String, Trainer> trainerStorage;

    public TrainerDAO(@Qualifier("trainerStorage") Map<String, Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    public Trainer createTrainer(String firstName, String lastName,
                                 String username, boolean isActive, String spec) {
        if (validate(username)) {
            username += "#" + ThreadLocalRandom.current().nextInt(1, 999);
        }

        Trainer trainer = new Trainer(firstName,
                lastName,
                username,
                isActive,
                spec);

        trainerStorage.put(trainer.getUserId(), trainer);

        return trainer;
    }

    public Trainer editTrainer(String userId, String firstName, String lastName,
                               String username, boolean isActive, String spec) {
        Trainer edited = new Trainer(firstName,
                lastName,
                username,
                isActive,
                spec);

        trainerStorage.put(userId, edited);

        return edited;
    }

    public Trainer selectTrainer(String userId) {
        return trainerStorage.get(userId);
    }

    @Override
    public boolean validate(String username) {
        for (Trainer entry : trainerStorage.values()) {
            if (entry.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

}
