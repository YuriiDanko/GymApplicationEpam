package com.urilvv.GymApplicationEpam.service_tests;

import com.urilvv.GymApplicationEpam.daos.TrainingDAO;
import com.urilvv.GymApplicationEpam.enums.TrainingType;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;
import com.urilvv.GymApplicationEpam.services.servicesImpl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTests {

    @Mock
    private Map<String, Training> trainingStorage;
    @InjectMocks
    private TrainingDAO trainingDAO;
    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    public void init() {
        trainingDAO = new TrainingDAO(trainingStorage);
        trainingService = new TrainingServiceImpl(trainingDAO);
    }

    @Test
    public void createTraining() {
        Trainee trainee = new Trainee("Yurii", "Danko", "Yurii.Danko",
                true, LocalDate.of(2003, 8, 7), "Lviv, Avraama Linkolna");
        Trainer trainer = new Trainer("Yurii", "Danko", "Yurii.Danko",
                true, "cardio");

        when(trainingStorage.put(any(String.class), any(Training.class))).thenReturn(null);

        Training training = trainingService.createTraining(trainee.getUserId(), trainer.getUserId(), "Training Name", TrainingType.GROUP,
                LocalDate.now(), LocalTime.of(0, 45));

        assertNotNull(training);
        assertEquals(LocalTime.of(0, 45), training.getTrainingDuration());
        verify(trainingStorage, times(1)).put(any(String.class), any(Training.class));
    }

    @Test
    public void selectTraining() {
        Trainee trainee = new Trainee("Yurii", "Danko", "Yurii.Danko",
                true, LocalDate.of(2003, 8, 7), "Lviv, Avraama Linkolna");
        Trainer trainer = new Trainer("Yurii", "Danko", "Yurii.Danko",
                true, "cardio");

        Training training = trainingService.createTraining(trainee.getUserId(), trainer.getUserId(), "Training Name", TrainingType.GROUP,
                LocalDate.now(), LocalTime.of(0, 45));

        when(trainingStorage.get(any(String.class))).thenReturn(training);

        Training selected = trainingService.selectTraining(training.getTrainingId());


        assertNotNull(selected);
        assertEquals(training, selected);
        verify(trainingStorage, times(1)).get(any(String.class));
    }

}
