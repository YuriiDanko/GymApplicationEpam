package com.urilvv.GymApplicationEpam.controller_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urilvv.GymApplicationEpam.controllers.TraineeController;
import com.urilvv.GymApplicationEpam.dtos.TraineeDTO;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.requestResponseModels.login.LoginChangeRequest;
import com.urilvv.GymApplicationEpam.requestResponseModels.register.RegisterTraineeRequest;
import com.urilvv.GymApplicationEpam.services.TraineeService;
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

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TraineeController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TraineeControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TraineeService traineeService;
    @MockBean
    private Mapper mapper;

    Trainee trainee;
    TraineeDTO traineeDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        trainee = new Trainee("Yurii", "Danko", "Yurii.Danko",
                true, LocalDate.now(), "Lviv");
        traineeDTO = new TraineeDTO("1", "Yurii", "Danko", "Yurii.Danko",
                true, LocalDate.now(), "Lviv");
    }

    @Test
    public void registerTraineeTest() throws Exception {
        when(traineeService.createTrainee(any(String.class), any(String.class), any(Boolean.class),
                any(LocalDate.class), any(String.class))).thenReturn(trainee);

        RegisterTraineeRequest registerTraineeRequest = new RegisterTraineeRequest(
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getDateOfBirth(),
                trainee.getAddress()
        );

        mockMvc.perform(post("/trainees/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerTraineeRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getTraineeProfileTest() throws Exception {
        when(traineeService.selectTrainee(any(String.class))).thenReturn(Optional.of(trainee));
        when(mapper.mapTrainee(any(Trainee.class))).thenReturn(traineeDTO);

        mockMvc.perform(get("/trainees/profile/{username}", trainee.getUsername())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(trainee.getFirstName())));

    }

    @Test
    public void changeTraineePasswordTest() throws Exception {
        LoginChangeRequest loginChangeRequest = new LoginChangeRequest(
                trainee.getPassword(),
                trainee.getPassword() + "d"
        );

        mockMvc.perform(put("/trainees/{user_id}/change-password", "user_id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginChangeRequest)))
                .andExpect(status().isAccepted())
                .andExpect(result -> result.getResponse()
                        .getContentAsString().equals("Password was changed successfully!"));
    }

    @Test
    public void deleteTraineeTest() throws Exception {
        when(traineeService.deleteTrainee(any(String.class))).thenReturn(true);

        mockMvc.perform(delete("/trainees/delete/{user_id}", "user_id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().equals("true"));
    }

    @Test
    public void changeTraineeActiveStatusTest() throws Exception {
        mockMvc.perform(patch("/trainees/{user_id}/change-active-status", "user_id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse()
                        .getContentAsString().equals("Active status was changed successfully!"));
    }

}
