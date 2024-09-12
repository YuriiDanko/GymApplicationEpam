package com.urilvv.GymApplicationEpam.configurations;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class FilePopulation {

    @Value("${trainee.population}")
    private String traineeFilePath;
    @Value("${trainer.population}")
    private String trainerFilePath;

    private final Map<String, Trainee> traineeStorage;
    private final Map<String, Trainer> trainerStorage;
    private final Map<String, Training> trainingStorage;

    public FilePopulation(@Qualifier("traineeStorage") Map<String, Trainee> traineeStorage,
                          @Qualifier("trainerStorage") Map<String, Trainer> trainerStorage,
                          @Qualifier("trainingStorage") Map<String, Training> trainingStorage) {
        this.traineeStorage = traineeStorage;
        this.trainerStorage = trainerStorage;
        this.trainingStorage = trainingStorage;
    }

    @PostConstruct
    private void populate() {
        log.info("Populating storages with data...");
        populateTrainee();
        populateTrainer();
        populateTrainings();
        log.info("Storages are now filled with data.");
    }

    private void populateTrainee() {
        try(BufferedReader reader = new BufferedReader(new FileReader(traineeFilePath))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                Trainee trainee = new Trainee(data[0], data[1], data[2], Boolean.parseBoolean(data[3]),
                        LocalDate.parse(data[4], DateTimeFormatter.ofPattern("yyyy/MM/dd")), data[5]);
                traineeStorage.put(trainee.getUserId(), trainee);
            }
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
    }

    private void populateTrainer() {
        try(BufferedReader reader = new BufferedReader(new FileReader(trainerFilePath))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                Trainer trainer = new Trainer(data[0], data[1], data[2], Boolean.parseBoolean(data[3]), data[4]);
                trainerStorage.put(trainer.getUserId(), trainer);
            }
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
    }

    private void populateTrainings() {
        String trainingId = UUID.randomUUID().toString();

        String trainerId = trainerStorage.values()
                .stream()
                .findAny()
                .orElseThrow()
                .getUserId();

        String traineeId = traineeStorage.values()
                .stream()
                .findAny()
                .orElseThrow()
                .getUserId();

        Training training = new Training(
                traineeId,
                trainerId,
                "TrainingTest",
                TrainingType.SOLO,
                LocalDate.now().plusDays(14),
                LocalTime.of(1, 30)
        );

        trainingStorage.put(trainingId, training);
    }

}
