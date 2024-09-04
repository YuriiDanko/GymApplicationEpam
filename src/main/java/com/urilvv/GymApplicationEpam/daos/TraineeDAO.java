package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.models.Trainee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class TraineeDAO {

    private Map<String, Trainee> traineeStorage;

    public TraineeDAO(@Qualifier("traineeStorage") Map<String, Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    public Trainee createTrainee(String firstName, String lastName, String password, String username,
                                 boolean isActive, LocalDate dateOfBirth, String address) {
        Trainee trainee = Trainee.TraineeBuilder.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .username(username)
                .isActive(isActive)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

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

    public Trainee editTrainee(String userId, String firstName, String lastName, String password, String username,
                               boolean isActive, LocalDate dateOfBirth, String address) {

        Trainee edited = Trainee.TraineeBuilder.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .username(username)
                .isActive(isActive)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        traineeStorage.put(userId, edited);

        return edited;
    }

    public Trainee selectTrainee(String userId) {
        return traineeStorage.get(userId);
    }

}
