package com.urilvv.GymApplicationEpam.service_tests;

import com.urilvv.GymApplicationEpam.daos.TraineeDAO;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.services.servicesImpl.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTests {

    @Mock
    public TraineeDAO traineeDAO;
    @InjectMocks
    public TraineeServiceImpl traineeService;

    Trainee initTrainee;
    Trainee editedTrainee;

    @BeforeEach
    public void init() {
        initTrainee = new Trainee("Yurii", "Danko", "Yurii.Danko", true,
                LocalDate.of(2003, 8, 7), "Lviv");
        editedTrainee = new Trainee("Roman", "Danko", "Roman.Danko", false,
                LocalDate.of(2006, 10, 22), "Lviv");
    }

    @Test
    public void createTraineeTest() {
        when(traineeDAO.createTrainee(any(String.class), any(String.class), any(String.class), any(Boolean.class),
                any(LocalDate.class), any(String.class))).thenReturn(initTrainee);

        Trainee trainee = traineeService.createTrainee("Yurii", "Danko",
                true, LocalDate.of(2003, 8, 7), "Lviv, Avraama Linkolna");

        assertNotNull(trainee);
        assertEquals(trainee.getUsername(), "Yurii.Danko");
    }

    @Test
    public void editTraineeTest() {
        when(traineeDAO.editTrainee(any(String.class), any(String.class), any(String.class),
                any(LocalDate.class), any(String.class))).thenReturn(editedTrainee);

        Trainee edited = traineeService.editTrainee("user_id", "Roman",
                "Danko", LocalDate.now(), "Lviv");

        assertNotNull(edited);
        assertNotEquals(edited.getUsername(), initTrainee.getUsername());
        assertEquals(edited.getUsername(), "Roman.Danko");
    }

    @Test
    public void selectTraineeTest() {
        when(traineeDAO.selectTrainee(any(String.class))).thenReturn(Optional.of(initTrainee));

        Trainee trainee = traineeService.selectTrainee("user_id").get();

        assertNotNull(trainee);
        assertEquals(trainee.getUsername(), "Yurii.Danko");
    }

    @Test
    public void deleteTraineeTest() {
        when(traineeDAO.deleteTrainee(any(String.class))).thenReturn(true);

        boolean deleted = traineeService.deleteTrainee("user_id");

        assertTrue(deleted);
        verify(traineeDAO, times(1)).deleteTrainee(any(String.class));
    }



}
