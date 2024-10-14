package com.urilvv.GymApplicationEpam.controllers;

import com.urilvv.GymApplicationEpam.dtos.TrainingDTO;
import com.urilvv.GymApplicationEpam.requestResponseModels.trainingRequests.CreateTrainingRequest;
import com.urilvv.GymApplicationEpam.utils.Mapper;
import com.urilvv.GymApplicationEpam.utils.TrainingFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/trainings")
@AllArgsConstructor
public class TrainingController {

    private final TrainingFacade trainingFacade;
    private final Mapper mapper;

    @Operation(
            description = "POST endpoint to create a new training.",
            responses = {
                    @ApiResponse(
                            description = "Training created successfully.",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Error processing request.",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/create-training")
    public ResponseEntity<TrainingDTO> createTraining(@Valid @RequestBody CreateTrainingRequest createRequest) {
        TrainingDTO training = mapper.mapTraining(trainingFacade.createTraining(createRequest));
        return new ResponseEntity<>(training, HttpStatus.CREATED);
    }

    @Operation(
            description = "GET endpoint to receive all existing trainings.",
            responses = {
                    @ApiResponse(
                            description = "List of trainings was received successfully.",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<TrainingDTO>> getAllTrainings() {
        List<TrainingDTO> trainingDTOS = trainingFacade.getAllTrainings()
                .stream()
                .map(mapper::mapTraining)
                .toList();

        return new ResponseEntity<>(trainingDTOS, HttpStatus.OK);
    }

}
