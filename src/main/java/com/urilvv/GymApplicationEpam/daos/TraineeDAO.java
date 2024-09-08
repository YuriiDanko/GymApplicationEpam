package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.models.Trainee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TraineeDAO implements Validation {

    private Map<String, Trainee> traineeStorage;

    public TraineeDAO(@Qualifier("traineeStorage") Map<String, Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    public Trainee createTrainee(String firstName, String lastName, String username,
                                 boolean isActive, LocalDate dateOfBirth, String address) {
        if (validate(username)) {
            username += "#" + ThreadLocalRandom.current().nextInt(1, 999);
        }

        Trainee trainee = new Trainee(firstName, lastName, username,
                isActive, dateOfBirth, address);

        traineeStorage.put(trainee.getUserId(), trainee);

        return trainee;
    }

    public boolean deleteTrainee(String userId) {
        if (!traineeStorage.containsKey(userId)) {
            return false;
        }

        traineeStorage.remove(userId);

        return true;
    }

    public Trainee editTrainee(String userId, String firstName, String lastName, String username,
                               boolean isActive, LocalDate dateOfBirth, String address) {

        Trainee edited = new Trainee(firstName, lastName, username,
                isActive, dateOfBirth, address);

        traineeStorage.put(userId, edited);

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
