package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.models.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TraineeDAO implements Validation {

    /*
    * TODO: improve name generation algorithm
    *  */


    private Map<String, Trainee> traineeStorage;
    private final Logger logger = LoggerFactory.getLogger(TraineeDAO.class);

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

        logger.info("Trainee with user_id - " + trainee.getUserId() + " was created.");

        return trainee;
    }

    public boolean deleteTrainee(String userId) {
        if (!traineeStorage.containsKey(userId)) {
            logger.error("Trainee with given user_id does not exist!");
            return false;
        }

        traineeStorage.remove(userId);

        logger.info("Trainee with user_id - " + userId + " was deleted.");

        return true;
    }

    public Trainee editTrainee(String userId, String firstName, String lastName, String username,
                               boolean isActive, LocalDate dateOfBirth, String address) {

        Trainee edited = new Trainee(firstName, lastName, username,
                isActive, dateOfBirth, address);

        traineeStorage.put(userId, edited);

        logger.info("Trainee with user_id - " + userId + " was edited.");

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
