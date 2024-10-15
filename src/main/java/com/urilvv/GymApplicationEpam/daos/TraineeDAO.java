package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.exceptions.PasswordMatchersException;
import com.urilvv.GymApplicationEpam.exceptions.UserNotFoundException;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Training;
import com.urilvv.GymApplicationEpam.models.User;
import com.urilvv.GymApplicationEpam.repositories.TraineeRepository;
import com.urilvv.GymApplicationEpam.repositories.TrainingRepository;
import com.urilvv.GymApplicationEpam.utils.TrainingSpecifications;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

@Repository
@Slf4j
@Data
public class TraineeDAO implements Validation {

    private final PasswordValidator passwordValidator;
    private final TraineeRepository traineeRepository;
    private final TrainingRepository trainingRepository;

    public TraineeDAO(PasswordValidator passwordValidator, TraineeRepository traineeRepository, TrainingRepository trainingRepository) {
        this.passwordValidator = passwordValidator;
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;
    }

    @Transactional
    public Trainee createTrainee(String firstName, String lastName, String username,
                                 boolean isActive, LocalDate dateOfBirth, String address) {
        Trainee trainee = new Trainee(firstName, lastName, username,
                isActive, dateOfBirth, address);

        while (validate(trainee)) {
            trainee.setUsername(User.generateUsername(username));
        }

        traineeRepository.save(trainee);

        log.info("Trainee with user_id - " + trainee.getUserId() + " was created.");

        return trainee;
    }

    @Transactional
    public boolean deleteTrainee(String userId) {
        Optional<Trainee> trainee = traineeRepository.findById(userId);

        if (trainee.isPresent()) {
            traineeRepository.delete(trainee.get());

            log.info("Trainee with user_id - " + userId + " was deleted.");
            return true;
        }

        log.warn("Trainee with user_id - " + userId + " was not deleted. No record found.");
        throw new UserNotFoundException();
    }

    @Transactional
    public Trainee editTrainee(String userId, String firstName, String lastName,
                               LocalDate dateOfBirth, String address) {
        Optional<Trainee> trainee = traineeRepository.findById(userId);

        if (trainee.isEmpty()) {
            log.warn("User with given user_id is not found!");
            throw new UserNotFoundException();
        }

        Trainee editTrainee = trainee.get();

        editTrainee.setFirstName(firstName);
        editTrainee.setLastName(lastName);
        editTrainee.setUsername(firstName + "." + lastName);
        editTrainee.setDateOfBirth(dateOfBirth);
        editTrainee.setAddress(address);

        while (validate(editTrainee)) {
            editTrainee.setUsername(User.generateUsername(editTrainee.getFirstName() + "." + editTrainee.getLastName()));
        }

        traineeRepository.save(editTrainee);

        return editTrainee;
    }

    @Transactional
    public void changePassword(String userId, String oldPassword, String newPassword) {
        Optional<Trainee> traineeOpt = traineeRepository.findById(userId);

        if (traineeOpt.isEmpty()) {
            log.warn("User with given user_id is not found!");
            throw new UserNotFoundException();
        }

        Trainee trainee = traineeOpt.get();


        if (!trainee.getPassword().equals(oldPassword)) {
            log.error("Password is not correct. Try again.");
            throw new PasswordMatchersException("Password is not correct. Try again.");
        } else if (newPassword.equals(oldPassword)) {
            log.error("Enter new password instead of old one.");
            throw new PasswordMatchersException("Enter new password instead of old one.");
        }

        RuleResult passwordValidation = passwordValidator.validate(new PasswordData(newPassword));

        if (passwordValidation.isValid()) {
            log.info("Password for Trainee with user_id - " + userId + " was changed.");
            trainee.setPassword(newPassword);
            traineeRepository.save(trainee);
            return;
        }

        log.error("""
                \nPassword need to follow next rules:
                \tShould contain between 10 to 18 characters;
                \tShould contain 1 UpperCase symbol;
                \tShould contain 1 LowerCase symbol;
                \tShould contain 1 Digit symbol;
                \tShould not contain any whitespaces.
                """);

        throw new PasswordMatchersException("Password was not validated.");
    }

    @Transactional
    public void changeActiveStatus(String userId) {
        Optional<Trainee> traineeOpt = traineeRepository.findById(userId);

        if (traineeOpt.isEmpty()) {
            log.warn("User with given user_id is not found!");
            throw new UserNotFoundException();
        }

        Trainee trainee = traineeOpt.get();

        trainee.setActive(!trainee.isActive());
        traineeRepository.save(trainee);

        log.info("Active Status for Trainee with user_id - " + userId + " was changed to " + trainee.isActive() + ".");
    }

    public Optional<Trainee> selectTrainee(String username) {
        Optional<Trainee> traineeOpt = traineeRepository.findByUsername(username);

        if (traineeOpt.isEmpty()) {
            log.warn("No Trainee found with given username - " + username);
            return Optional.empty();
        }

        log.info("Trainee profile with username - " + traineeOpt.get().getUserId() + " was returned.");
        return traineeOpt;
    }

    public List<Trainee> getAllTrainees() {
        log.info("List of Trainees was returned.");
        return traineeRepository.findAll();
    }

    public List<Training> getTraineeTrainingList(String traineeUsername, LocalDate from,
                                                 LocalDate to, String trainerUsername, TrainingType type) {
        return trainingRepository.findAll(
                where(TrainingSpecifications.hasTraineeUsername(traineeUsername))
                        .and(TrainingSpecifications.hasTrainerUsername(trainerUsername))
                        .and(TrainingSpecifications.betweenTrainingTime(from, to))
                        .and(TrainingSpecifications.hasTrainingType(type.name()))
        );
    }

    @Override
    public <T extends User> boolean validate(T trainee) {
        return selectTrainee(trainee.getUsername()).isPresent();
    }

}
