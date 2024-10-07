package com.urilvv.GymApplicationEpam.requestResponseModels.trainingRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class CreateTrainingRequest {

    @NotBlank(message = "TraineeId cannot be blank or empty.")
    private String traineeId;
    @NotBlank(message = "TrainerId cannot be blank or empty.")
    private String trainerId;
    @NotBlank(message = "TrainingName cannot be blank or empty.")
    private String trainingName;
    @NotBlank(message = "TrainingType cannot be blank or empty.")
    private String trainingType;
    @NotNull(message = "TrainingTime cannot be blank or empty.")
    private LocalDate trainingTime;
    @NotNull(message = "TrainingDuration cannot be blank or empty.")
    private LocalTime trainingDuration;

}
