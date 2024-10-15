package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.exceptions.TrainingIsNotExist;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;
import com.urilvv.GymApplicationEpam.repositories.TrainingRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Data
public class TrainingDAO {

    private final TrainingRepository trainingRepository;

    @Transactional
    public Training createTraining(Trainee trainee, Trainer trainer, String trainingName,
                                   TrainingType trainingType, LocalDate trainingTime, LocalTime trainingDuration) {
        Training training = new Training(trainee, trainer, trainingName,
                trainingType, trainingTime, trainingDuration);

        trainee.addTraining(training);
        trainer.addTraining(training);

        trainingRepository.save(training);

        log.info("Training session with training_id - " + training.getTrainingId() + " was created.");

        return training;
    }

    public Optional<Training> selectTraining(String trainingId) {
        Optional<Training> trainingOpt = trainingRepository.findById(trainingId);

        if (trainingOpt.isEmpty()) {
            log.warn("No Training exists with given training_id - " + trainingId);
            return Optional.empty();
        }

        log.info("Training with training_id - " + trainingOpt.get().getTrainingId() + " was returned.");
        return trainingOpt;
    }

    @Transactional
    public boolean deleteTraining(String trainingId) {
        Optional<Training> trainingOpt = trainingRepository.findById(trainingId);

        if (trainingOpt.isEmpty()) {
            log.warn("No Training exists with given training_id - " + trainingId);
            throw new TrainingIsNotExist();
        }

        trainingRepository.delete(trainingOpt.get());

        return true;
    }

    public List<Training> getAllTrainings() {
        log.info("List of Trainings was returned.");
        return trainingRepository.findAll();
    }

}
