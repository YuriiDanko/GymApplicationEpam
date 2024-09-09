package com.urilvv.GymApplicationEpam.configurations;

import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Trainer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class FilePopulation {

    @Value("${trainee.population}")
    private String traineeFilePath;
    @Value("${trainer.population}")
    private String trainerFilePath;

    private final Map<String, Trainee> traineeStorage;
    private final Map<String, Trainer> trainerStorage;

    public FilePopulation(@Qualifier("traineeStorage") Map<String, Trainee> traineeStorage,
                          @Qualifier("trainerStorage") Map<String, Trainer> trainerStorage) {
        this.traineeStorage = traineeStorage;
        this.trainerStorage = trainerStorage;
    }

    @PostConstruct
    private void populate() {
        populateTrainee();
        populateTrainer();
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

}
