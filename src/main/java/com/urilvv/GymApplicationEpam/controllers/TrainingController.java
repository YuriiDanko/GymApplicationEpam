package com.urilvv.GymApplicationEpam.controllers;

import com.urilvv.GymApplicationEpam.dtos.TrainingDTO;
import com.urilvv.GymApplicationEpam.requestResponseModels.trainingRequests.CreateTrainingRequest;
import com.urilvv.GymApplicationEpam.utils.Mapper;
import com.urilvv.GymApplicationEpam.utils.TrainingFacade;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/trainings", produces = {"application/JSON"})
@AllArgsConstructor
public class TrainingController {

    private final TrainingFacade trainingFacade;
    private final Mapper mapper;

    @PostMapping("/create-training")
    public ResponseEntity<TrainingDTO> createTraining(@Valid @RequestBody CreateTrainingRequest createRequest) {
        TrainingDTO training = mapper.mapTraining(trainingFacade.createTraining(createRequest));
        return new ResponseEntity<>(training, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TrainingDTO>> getAllTrainings() {
        List<TrainingDTO> trainingDTOS = trainingFacade.getAllTrainings()
                .stream()
                .map(mapper::mapTraining)
                .toList();

        return new ResponseEntity<>(trainingDTOS, HttpStatus.OK);
    }

}
