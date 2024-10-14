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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTests {

    @Mock
    private TrainingDAO trainingDAO;
    @InjectMocks
    private TrainingServiceImpl trainingService;

    Training initTraining;
    Trainee trainee;
    Trainer trainer;

    @BeforeEach
    public void init() {
        trainee = new Trainee("Yurii", "Danko", "Yurii.Danko", true,
                LocalDate.of(2003, 8, 7), "Lviv");
        trainer = new Trainer("Sergiy", "Duhota",
                "Sergiy.Duhota", true, "cardio");
        initTraining = new Training(trainee, trainer, "TestName", TrainingType.SOLO, LocalDate.now(), LocalTime.of(1, 30));
    }

    @Test
    public void createTraining() {
        when(trainingDAO.createTraining(any(Trainee.class), any(Trainer.class), any(String.class),
                any(TrainingType.class), any(LocalDate.class), any(LocalTime.class))).thenReturn(initTraining);

        Training training = trainingService.createTraining(trainee, trainer,"abcd", TrainingType.SOLO,
                LocalDate.now(), LocalTime.of(1, 30));

        assertNotNull(training);
        assertEquals(training.getTrainingName(), initTraining.getTrainingName());
    }

    @Test
    public void selectTraining() {
        when(trainingDAO.selectTraining(any(String.class))).thenReturn(Optional.of(initTraining));

        Training training = trainingService.selectTraining("random_id").orElseThrow();

        assertNotNull(training);
        assertEquals(training.getTrainee(), initTraining.getTrainee());
    }

}
