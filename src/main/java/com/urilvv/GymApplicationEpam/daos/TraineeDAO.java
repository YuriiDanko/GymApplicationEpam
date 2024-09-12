package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.models.Trainee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class TraineeDAO implements Validation {

    private final Map<String, Trainee> traineeStorage;

    public TraineeDAO(@Qualifier("traineeStorage") Map<String, Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    public Trainee createTrainee(String firstName, String lastName, String username,
                                 boolean isActive, LocalDate dateOfBirth, String address) {
        if (validate(username)) {
            String plainUsername = username;
            while(validate(plainUsername)) {
                plainUsername = username + "#" + ThreadLocalRandom.current().nextInt(1, 999);
            }

            username = plainUsername;
        }

        Trainee trainee = new Trainee(firstName, lastName, username,
                isActive, dateOfBirth, address);

        traineeStorage.put(trainee.getUserId(), trainee);

        log.info("Trainee with user_id - " + trainee.getUserId() + " was created.");

        return trainee;
    }

    public boolean deleteTrainee(String userId) {
        if (!traineeStorage.containsKey(userId)) {
            log.error("Trainee with given user_id does not exist!");
            return false;
        }

        traineeStorage.remove(userId);

        log.info("Trainee with user_id - " + userId + " was deleted.");

        return true;
    }

    public Trainee editTrainee(String userId, String firstName, String lastName, String username,
                               boolean isActive, LocalDate dateOfBirth, String address) {

        Trainee edited = new Trainee(firstName, lastName, username,
                isActive, dateOfBirth, address);

        traineeStorage.put(userId, edited);

        log.info("Trainee with user_id - " + userId + " was edited.");

        return edited;
    }

    public Trainee selectTrainee(String userId) {
        return traineeStorage.get(userId);
    }

    @Override
    public boolean validate(String username) {
        for (Trainee entry : traineeStorage.values()) {
            if (entry.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }
}
