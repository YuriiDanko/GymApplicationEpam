package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Data
public class TrainingDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Training createTraining(Trainee trainee, Trainer trainer, String trainingName,
                                   TrainingType trainingType, LocalDate trainingTime, LocalTime trainingDuration) {
        Training training = new Training(trainee, trainer, trainingName,
                trainingType, trainingTime, trainingDuration);

        trainee.addTraining(training);
        trainer.addTraining(training);

        entityManager.persist(training);

        log.info("Training session with training_id - " + training.getTrainingId() + " was created.");

        return training;
    }

    public Optional<Training> selectTraining(String trainingId) {
        List<Training> resultList = entityManager.createQuery("select tr from Training tr where tr.trainingId = :trainingId"
                        , Training.class).setParameter("trainingId", trainingId).getResultList();

        if (resultList.isEmpty()) {
            log.warn("No Training exists with given training_id - " + trainingId);
            return Optional.empty();
        }

        log.info("Training with training_id - " + resultList.get(0).getTrainingId() + " was returned.");
        return Optional.ofNullable(resultList.get(0));
    }

    @Transactional
    public boolean deleteTraining(String trainingId) {
        int updatedEntities = entityManager.createQuery("delete from Training tr where tr.trainingId = :trainingId")
                .setParameter("trainingId", trainingId)
                .executeUpdate();

        return updatedEntities == 1;
    }

    public List<Training> getAllTrainings() {
        log.info("List of Trainings was returned.");
        return entityManager.createQuery("from Training", Training.class)
                .getResultList();
    }

}
