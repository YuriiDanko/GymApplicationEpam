package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.enums.Specialization;
import com.urilvv.GymApplicationEpam.exceptions.PasswordMatchersException;
import com.urilvv.GymApplicationEpam.exceptions.UserNotFoundException;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;
import com.urilvv.GymApplicationEpam.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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

@Component
@Slf4j
@Data
public class TrainerDAO implements Validation {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordValidator passwordValidator;

    public TrainerDAO(PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
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

        entityManager.persist(trainer);

        log.info("Trainer with user_id - " + trainer.getUserId() + " was created.");

        return trainer;
    }

    @Transactional
    public Trainer editTrainer(String userId, String firstName, String lastName,
                               String spec) {
        Trainer trainer = entityManager.find(Trainer.class, userId);

        if(trainer == null) {
            log.warn("User with given user_id is not found!");
            throw new UserNotFoundException();
        }

        entityManager.detach(trainer);
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setUsername(firstName + "." + lastName);
        trainer.setTrainerSpec(Specialization.getSpec(spec + "_spec"));

        while (validate(trainer)) {
            trainer.setUsername(User.generateUsername(trainer.getFirstName() + "." + trainer.getLastName()));
        }

        log.info("Trainer with user_id - " + trainer.getUserId() + " was edited.");

        entityManager.merge(trainer);

        return trainer;
    }

    public Optional<Trainer> selectTrainer(String username) {
        List<Trainer> resultList = entityManager.createQuery("select t from Trainer t left join fetch t.trainerTrainings " +
                        "where t.username = :username", Trainer.class)
                .setParameter("username", username)
                .getResultList();

        if (resultList.isEmpty()) {
            log.warn("No Trainer found with given username - " + username);
            return Optional.empty();
        }

        log.info("Trainer profile with user_id - " + resultList.get(0).getUserId() + " was returned.");
        return Optional.ofNullable(resultList.get(0));
    }

    public List<Trainer> getAllTrainers() {
        log.info("List of Trainers was returned.");
        return entityManager.createQuery("select t from Trainer t left outer join Training tr on t.userId = tr.trainee.userId", Trainer.class)
                .getResultList();
    }

    @Transactional
    public void changePassword(String userId, String oldPassword, String newPassword) {
        Trainer trainer = entityManager.find(Trainer.class, userId);

        if(trainer == null) {
            log.warn("User with given user_id is not found!");
            throw new UserNotFoundException();
        }

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
            entityManager.detach(trainer);
            trainer.setPassword(newPassword);
            entityManager.merge(trainer);
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
        Trainer trainer = entityManager.find(Trainer.class, userId);

        if(trainer == null) {
            log.warn("User with given user_id is not found!");
            throw new UserNotFoundException();
        }

        entityManager.detach(trainer);
        trainer.setActive(!trainer.isActive());
        entityManager.merge(trainer);
        log.info("Active Status for Trainer with user_id - " + userId + " was changed to " + trainer.isActive() + ".");
    }

    public List<Training> getTrainerTrainingList(String trainerUsername, LocalDate from, LocalDate to, String traineeUsername) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);

        Root<Training> trainingRoot = criteriaQuery.from(Training.class);

        Predicate trainerUsernamePredicate = criteriaBuilder.like(trainingRoot.get("trainer").get("username"), trainerUsername);
        Predicate fromToDatePredicate = criteriaBuilder.between(trainingRoot.get("trainingTime"), from, to);
        Predicate traineeUsernamePredicate = criteriaBuilder.like(trainingRoot.get("trainee").get("username"), traineeUsername);

        criteriaQuery.select(trainingRoot).where(criteriaBuilder.and(traineeUsernamePredicate, trainerUsernamePredicate,
                fromToDatePredicate));

        log.info("Trainer related trainings were returned.");

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public <T extends User> boolean validate(T trainer) {
        return selectTrainer(trainer.getUsername()).isPresent();
    }

}
