package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.enums.Specialization;
import com.urilvv.GymApplicationEpam.exceptions.PasswordMatchersException;
import com.urilvv.GymApplicationEpam.exceptions.UserNotFoundException;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;
import com.urilvv.GymApplicationEpam.models.User;
import com.urilvv.GymApplicationEpam.repositories.TrainerRepository;
import com.urilvv.GymApplicationEpam.repositories.TrainingRepository;
import com.urilvv.GymApplicationEpam.utils.TrainingSpecifications;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
@Slf4j
@Data
public class TrainerDAO implements Validation {

    private final PasswordValidator passwordValidator;
    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;

    public TrainerDAO(PasswordValidator passwordValidator, TrainerRepository trainerRepository, TrainingRepository trainingRepository) {
        this.passwordValidator = passwordValidator;
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
    }

    @Transactional
    public Trainer createTrainer(String firstName, String lastName,
                                 String username, boolean isActive, String spec) {
        Trainer trainer = new Trainer(firstName,
                lastName,
                username,
                isActive,
                spec);

        while(validate(trainer)) {
            trainer.setUsername(User.generateUsername(trainer.getFirstName() + "." + trainer.getLastName()));
        }

        trainerRepository.save(trainer);

        log.info("Trainer with user_id - " + trainer.getUserId() + " was created.");

        return trainer;
    }

    @Transactional
    public Trainer editTrainer(String userId, String firstName, String lastName,
                               String spec) {
        Optional<Trainer> trainerOpt = trainerRepository.findById(userId);

        if(trainerOpt.isEmpty()) {
            log.warn("User with given user_id is not found!");
            throw new UserNotFoundException();
        }

        Trainer trainer = trainerOpt.get();

        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setUsername(firstName + "." + lastName);
        trainer.setTrainerSpec(Specialization.getSpec(spec + "_spec"));

        while (validate(trainer)) {
            trainer.setUsername(User.generateUsername(trainer.getFirstName() + "." + trainer.getLastName()));
        }

        log.info("Trainer with user_id - " + trainer.getUserId() + " was edited.");

        trainerRepository.save(trainer);

        return trainer;
    }

    public Optional<Trainer> selectTrainer(String username) {
        Optional<Trainer> trainerOpt = trainerRepository.findByUsername(username);

        if (trainerOpt.isEmpty()) {
            log.warn("No Trainer found with given username - " + username);
            return Optional.empty();
        }

        log.info("Trainer profile with user_id - " + trainerOpt.get().getUserId() + " was returned.");
        return trainerOpt;
    }

    public List<Trainer> getAllTrainers() {
        log.info("List of Trainers was returned.");
        return trainerRepository.findAll();
    }

    @Transactional
    public void changePassword(String userId, String oldPassword, String newPassword) {
        Optional<Trainer> trainerOpt = trainerRepository.findById(userId);

        if(trainerOpt.isEmpty()) {
            log.warn("User with given user_id is not found!");
            throw new UserNotFoundException();
        }

        Trainer trainer = trainerOpt.get();

        if(!trainer.getPassword().equals(oldPassword)) {
            log.error("Password is not correct. Try again.");
            throw new PasswordMatchersException("Password is not correct. Try again.");
        }
        else if(newPassword.equals(oldPassword)) {
            log.error("Enter new password instead of old one.");
            throw new PasswordMatchersException("Enter new password instead of old one.");
        }

        RuleResult passwordValidation = passwordValidator.validate(new PasswordData(newPassword));

        if(passwordValidation.isValid()) {
            log.info("Password for Trainer with user_id - " + userId + " was changed.");
            trainer.setPassword(newPassword);
            trainerRepository.save(trainer);
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
        Optional<Trainer> trainerOpt = trainerRepository.findById(userId);

        if(trainerOpt.isEmpty()) {
            log.warn("User with given user_id is not found!");
            throw new UserNotFoundException();
        }

        Trainer trainer = trainerOpt.get();

        trainer.setActive(!trainer.isActive());
        trainerRepository.save(trainer);

        log.info("Active Status for Trainer with user_id - " + userId + " was changed to " + trainer.isActive() + ".");
    }

    public List<Training> getTrainerTrainingList(String trainerUsername, LocalDate from, LocalDate to, String traineeUsername) {
        return trainingRepository.findAll(
                where(TrainingSpecifications.hasTrainerUsername(trainerUsername))
                        .and(TrainingSpecifications.betweenTrainingTime(from, to))
                        .and(TrainingSpecifications.hasTraineeUsername(traineeUsername))
        );
    }

    @Override
    public <T extends User> boolean validate(T trainer) {
        return selectTrainer(trainer.getUsername()).isPresent();
    }

}
