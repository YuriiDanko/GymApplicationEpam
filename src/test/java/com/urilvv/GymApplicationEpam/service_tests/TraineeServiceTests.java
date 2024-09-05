package com.urilvv.GymApplicationEpam.service_tests;

import com.urilvv.GymApplicationEpam.daos.TraineeDAO;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.services.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TraineeServiceTests {

    @Mock
    private Map<String, Trainee> traineeStorage;

    @InjectMocks
    private TraineeDAO traineeDAO;

    @InjectMocks
    private TraineeService traineeService;

    @BeforeEach
    public void init() {
        traineeDAO = new TraineeDAO(traineeStorage);
        traineeService = new TraineeService(traineeDAO);
    }

    @Test
    public void createTraineeTest() {
        when(traineeStorage.put(any(String.class), any(Trainee.class))).thenReturn(null);

        Trainee trainee = traineeService.createTrainee("Yurii", "Danko", "password", "urilvv",
                true, LocalDate.of(2003, 8, 7), "Lviv, Avraama Linkolna");

        assertEquals(trainee.getUsername(), "urilvv");
        verify(traineeStorage, times(1)).put(any(String.class), any(Trainee.class));
    }

    @Test
    public void editTraineeTest() {
        when(traineeStorage.put(any(String.class), any(Trainee.class))).thenReturn(null);

        Trainee trainee = traineeService.createTrainee("Yurii", "Danko", "password", "urilvv",
                true, LocalDate.of(2003, 8, 7), "Lviv, Avraama Linkolna");

        Trainee edited = traineeService.editTrainee(trainee.getUserId(), "Yurii", "Danko", "password", "urilvv55",
                true, LocalDate.of(2003, 8, 7), "Lviv, Avraama Linkolna");

        assertNotEquals(trainee.getUsername(), edited.getUsername());
        assertEquals(edited.getUsername(), "urilvv55");
        verify(traineeStorage, times(2)).put(any(String.class), any(Trainee.class));
    }

    @Test
    public void selectTraineeTest() {
        Trainee trainee = traineeService.createTrainee("Yurii", "Danko", "password", "urilvv",
                true, LocalDate.of(2003, 8, 7), "Lviv, Avraama Linkolna");

        when(traineeStorage.get(eq(trainee.getUserId()))).thenReturn(trainee);

        Trainee selected = traineeService.selectTrainee(trainee.getUserId());

        assertEquals(trainee, selected);
        verify(traineeStorage, times(1)).get(eq(trainee.getUserId()));
    }

    @Test
    public void deleteTraineeTest() {
        Trainee trainee = traineeService.createTrainee("Yurii", "Danko", "password", "urilvv",
                true, LocalDate.of(2003, 8, 7), "Lviv, Avraama Linkolna");

        when(traineeStorage.remove(eq(trainee.getUserId()))).thenReturn(trainee);
        when(traineeStorage.containsKey(eq(trainee.getUserId()))).thenReturn(true);

        assertTrue(traineeService.deleteTrainee(trainee.getUserId()));
        verify(traineeStorage, times(1)).remove(eq(trainee.getUserId()));
    }

}
