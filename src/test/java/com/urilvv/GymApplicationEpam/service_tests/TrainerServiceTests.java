package com.urilvv.GymApplicationEpam.service_tests;

import com.urilvv.GymApplicationEpam.daos.TrainerDAO;
import com.urilvv.GymApplicationEpam.enums.Specialization;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.services.servicesImpl.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTests {

    @Mock
    private Map<String, Trainer> trainerStorage;

    @InjectMocks
    private TrainerDAO trainerDAO;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    public void init() {
        trainerDAO = new TrainerDAO(trainerStorage);
        trainerService = new TrainerServiceImpl(trainerDAO);
    }

    @Test
    public void createTrainerTest() {
        when(trainerStorage.put(any(String.class), any(Trainer.class))).thenReturn(null);

        Trainer trainer = trainerService.createTrainer("Yurii", "Danko",
                true, "cardio");

        assertEquals(trainer.getTrainerSpec(), Specialization.CARDIO);
        verify(trainerStorage, times(1)).put(eq(trainer.getUserId()), any(Trainer.class));
    }

    @Test
    public void editTrainerTest() {
        when(trainerStorage.put(any(String.class), any(Trainer.class))).thenReturn(null);

        Trainer trainer = trainerService.createTrainer("Yurii", "Danko",
                true, "cardio");

        Trainer edited = trainerService.editTrainer(trainer.getUserId(), "Marko", "Yanovych", true, "strength");

        assertNotEquals(trainer.getUsername(), edited.getUsername());
        assertEquals(edited.getUsername(), "Marko.Yanovych");
        verify(trainerStorage, times(2)).put(any(String.class), any(Trainer.class));
    }

    @Test
    public void selectTrainerTest() {
        Trainer trainer = trainerService.createTrainer("Yurii", "Danko",
                true, "cardio");

        when(trainerStorage.get(eq(trainer.getUserId()))).thenReturn(trainer);

        Trainer selected = trainerService.selectTrainer(trainer.getUserId());

        assertEquals(trainer, selected);
        verify(trainerStorage, times(1)).get(eq(trainer.getUserId()));
    }

    @Test
    public void similarNamesTest() {
        Trainer trainer1 = trainerService.createTrainer("Yurii", "Danko",
                true, "cardio");

        when(trainerStorage.values()).thenReturn(List.of(trainer1));

        Trainer trainer2 = trainerService.createTrainer("Yurii", "Danko",
                true, "cardio");

        assertNotEquals(trainer1.getUsername(), trainer2.getUsername());
    }

}
