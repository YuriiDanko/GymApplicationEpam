package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.models.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class TrainerDAO implements Validation {

    private final Map<String, Trainer> trainerStorage;

    public TrainerDAO(@Qualifier("trainerStorage") Map<String, Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    public Trainer createTrainer(String firstName, String lastName,
                                 String username, boolean isActive, String spec) {
        if (validate(username)) {
            String plainUsername = username;
            while(validate(plainUsername)) {
                plainUsername = username + "#" + ThreadLocalRandom.current().nextInt(1, 999);
            }

            username = plainUsername;
        }

        Trainer trainer = new Trainer(firstName,
                lastName,
                username,
                isActive,
                spec);

        trainerStorage.put(trainer.getUserId(), trainer);

        log.info("Trainer with user_id - " + trainer.getUserId() + " was created.");

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

        log.info("Trainer with user_id - " + userId + " was edited.");

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
