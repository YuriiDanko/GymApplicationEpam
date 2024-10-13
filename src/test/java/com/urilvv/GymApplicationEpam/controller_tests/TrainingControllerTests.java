package com.urilvv.GymApplicationEpam.controller_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urilvv.GymApplicationEpam.controllers.TrainingController;
import com.urilvv.GymApplicationEpam.models.Training;
import com.urilvv.GymApplicationEpam.requestResponseModels.trainingRequests.CreateTrainingRequest;
import com.urilvv.GymApplicationEpam.utils.Mapper;
import com.urilvv.GymApplicationEpam.utils.TrainingFacade;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TrainingController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TrainingControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrainingFacade trainingFacade;
    @MockBean
    private Mapper mapper;

    Training training;

    @BeforeEach
    public void setup() {
        training = new Training();
        training.setTrainingName("Name");
    }

    @Test
    public void createTrainingTest() throws Exception {
        when(trainingFacade.createTraining(any(CreateTrainingRequest.class))).thenReturn(training);

        CreateTrainingRequest createTrainingRequest = new CreateTrainingRequest(
                "trainee_id",
                "trainer_id",
                "Name",
                "SOLO",
                LocalDate.now(),
                LocalTime.of(1, 30)
        );

        mockMvc.perform(post("/trainings/create-training")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTrainingRequest)))
                .andExpect(status().isCreated());
    }

}
