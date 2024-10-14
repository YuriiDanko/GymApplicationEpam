package com.urilvv.GymApplicationEpam.controller_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urilvv.GymApplicationEpam.controllers.TrainerController;
import com.urilvv.GymApplicationEpam.dtos.TrainerDTO;
import com.urilvv.GymApplicationEpam.enums.Specialization;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.requestResponseModels.login.LoginChangeRequest;
import com.urilvv.GymApplicationEpam.requestResponseModels.register.RegisterTrainerRequest;
import com.urilvv.GymApplicationEpam.services.TrainerService;
import com.urilvv.GymApplicationEpam.utils.Mapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TrainerController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TrainerControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrainerService trainerService;
    @MockBean
    private Mapper mapper;

    Trainer trainer;
    TrainerDTO trainerDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        trainer = new Trainer("Sergiy", "Duhota",
                "Sergiy.Duhota", true, "cardio");
        trainerDTO = new TrainerDTO("user_id", "Sergiy", "Duhota",
                "Sergiy.Duhota", true, Specialization.getSpec("cardio_spec"));
    }

    @Test
    public void registerTrainerTest() throws Exception {
        when(trainerService.createTrainer(any(String.class), any(String.class), any(Boolean.class), any(String.class))).thenReturn(trainer);

        RegisterTrainerRequest registerTraineeRequest = new RegisterTrainerRequest(
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getTrainerSpec().getName()
        );

        mockMvc.perform(post("/trainers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerTraineeRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getTraineeProfileTest() throws Exception {
        when(trainerService.selectTrainer(any(String.class))).thenReturn(Optional.of(trainer));
        when(mapper.mapTrainer(any(Trainer.class))).thenReturn(trainerDTO);

        mockMvc.perform(get("/trainers/profile/{username}", trainer.getUsername())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(trainer.getFirstName())));

    }

    @Test
    public void changeTraineePasswordTest() throws Exception {
        LoginChangeRequest loginChangeRequest = new LoginChangeRequest(
                trainer.getPassword(),
                trainer.getPassword() + "d"
        );

        mockMvc.perform(put("/trainers/{user_id}/change-password", "user_id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginChangeRequest)))
                .andExpect(status().isAccepted())
                .andExpect(result -> result.getResponse()
                        .getContentAsString().equals("Password was changed successfully!"));
    }

    @Test
    public void changeTrainerActiveStatusTest() throws Exception {
        mockMvc.perform(patch("/trainers/{user_id}/change-active-status", "user_id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse()
                        .getContentAsString().equals("Active status was changed successfully!"));
    }

}
