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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTests {

    @Mock
    public TrainerDAO trainerDAO;
    @InjectMocks
    public TrainerServiceImpl trainerService;

    Trainer initTrainer;
    Trainer editedTrainer;

    @BeforeEach
    public void init() {
        initTrainer = new Trainer("Sergiy", "Duhota",
                "Sergiy.Duhota", true, "cardio");
        editedTrainer = new Trainer("Yurii", "Danko",
                "Yurii.Danko", false, "strength");
    }

    @Test
    public void createTrainerTest() {
        when(trainerDAO.createTrainer(any(String.class), any(String.class), any(String.class),
                any(Boolean.class), any(String.class))).thenReturn(initTrainer);

        Trainer trainer = trainerService.createTrainer("Sergiy", "Duhota",
                true, "cardio");

        assertNotNull(trainer);
        assertEquals(trainer.getTrainerSpec(), Specialization.CARDIO);
    }

    @Test
    public void editTrainerTest() {
        when(trainerDAO.editTrainer(any(String.class), any(String.class), any(String.class),
                any(String.class))).thenReturn(editedTrainer);

        Trainer edited = trainerService.editTrainer("user_id", "Yurii", "Danko", "strength");

        assertNotNull(edited);
        assertEquals(edited.getTrainerSpec(), editedTrainer.getTrainerSpec());
    }

    @Test
    public void selectTrainerTest() {
        when(trainerDAO.selectTrainer(any(String.class))).thenReturn(Optional.of(initTrainer));

        Trainer trainer =  trainerDAO.selectTrainer("Sergiy.Duhota").orElseThrow();

        assertNotNull(trainer);
        assertEquals(trainer.getUsername(), initTrainer.getUsername());
    }

}
