package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.exceptions.PasswordMatchersException;
import com.urilvv.GymApplicationEpam.exceptions.UserNotFoundException;
import com.urilvv.GymApplicationEpam.models.Trainee;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@Data
public class TraineeDAO implements Validation {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordValidator passwordValidator;

    public TraineeDAO(PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
    }

    @Transactional
    public Trainee createTrainee(String firstName, String lastName, String username,
                                 boolean isActive, LocalDate dateOfBirth, String address) {
        Trainee trainee = new Trainee(firstName, lastName, username,
                isActive, dateOfBirth, address);

        while (validate(trainee)) {
            trainee.setUsername(User.generateUsername(username));
        }

        entityManager.persist(trainee);

        log.info("Trainee with user_id - " + trainee.getUserId() + " was created.");

        return trainee;
    }

    @Transactional
    public boolean deleteTrainee(String userId) {
        Trainee trainee = entityManager.find(Trainee.class, userId);

        if(trainee != null) {
            entityManager.remove(trainee);

            log.info("Trainee with user_id - " + userId + " was deleted.");
            return true;
        }

        log.warn("Trainee with user_id - " + userId + " was not deleted. No record found.");
        throw new UserNotFoundException();
    }

    @Transactional
    public Trainee editTrainee(String userId, String firstName, String lastName,
                               LocalDate dateOfBirth, String address) {
        Trainee trainee = entityManager.find(Trainee.class, userId);

        if(trainee == null) {
            log.warn("User with given user_id is not found!");
            throw new UserNotFoundException();
        }

        entityManager.detach(trainee);
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setUsername(firstName + "." + lastName);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setAddress(address);

        while (validate(trainee)) {
            trainee.setUsername(User.generateUsername(trainee.getFirstName() + "." + trainee.getLastName()));
        }

        entityManager.merge(trainee);

        return trainee;
    }

    @Transactional
    public void changePassword(String userId, String oldPassword, String newPassword) {
        Trainee trainee = entityManager.find(Trainee.class, userId);

        if(trainee == null){
            log.warn("User with given user_id is not found!");
            throw new UserNotFoundException();
        }

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
            entityManager.detach(trainee);
            trainee.setPassword(newPassword);
            entityManager.merge(trainee);
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
        Trainee trainee = entityManager.find(Trainee.class, userId);

        if(trainee == null) {
            log.warn("User with given user_id is not found!");
            throw new UserNotFoundException();
        }

        entityManager.detach(trainee);
        trainee.setActive(!trainee.isActive());
        entityManager.merge(trainee);
        log.info("Active Status for Trainee with user_id - " + userId + " was changed to " + trainee.isActive() + ".");
    }

    public Optional<Trainee> selectTrainee(String username) {
        List<Trainee> resultList = entityManager.createQuery("select t from Trainee t left join fetch t.traineeTrainings " +
                        "where t.username = :username", Trainee.class)
                .setParameter("username", username)
                .getResultList();

        if (resultList.isEmpty()) {
            log.warn("No Trainee found with given username - " + username);
            return Optional.empty();
        }

        log.info("Trainee profile with username - " + resultList.get(0).getUserId() + " was returned.");
        return Optional.ofNullable(resultList.get(0));
    }

    public List<Trainee> getAllTrainees() {
        log.info("List of Trainees was returned.");
        return entityManager.createQuery("select t from Trainee t left outer join Training tr on t.userId = tr.trainee.userId", Trainee.class)
                .getResultList();
    }

    public List<Training> getTraineeTrainingList(String traineeUsername, LocalDate from,
                                                 LocalDate to, String trainerUsername, TrainingType type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);

        Root<Training> trainingRoot = criteriaQuery.from(Training.class);

        Predicate traineeUsernamePredicate = criteriaBuilder.like(trainingRoot.get("trainee").get("username"), traineeUsername);
        Predicate fromToDatePredicate = criteriaBuilder.between(trainingRoot.get("trainingTime"), from, to);
        Predicate trainerUsernamePredicate = criteriaBuilder.like(trainingRoot.get("trainer").get("username"), trainerUsername);
        Predicate trainingTypePredicate = criteriaBuilder.like(trainingRoot.get("trainingType"), type.name());

        criteriaQuery.select(trainingRoot).where(criteriaBuilder.and(traineeUsernamePredicate, trainerUsernamePredicate,
                fromToDatePredicate, trainingTypePredicate));

        log.info("Trainee related trainings were returned.");

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public <T extends User> boolean validate(T trainee) {
        return selectTrainee(trainee.getUsername()).isPresent();
    }

}
