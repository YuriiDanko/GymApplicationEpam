package com.urilvv.GymApplicationEpam.dtos;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class TrainingDTO {

    private String trainingId;
    private TraineeDTO traineeDTO;
    private TrainerDTO trainerDTO;
    private String trainingName;
    private TrainingType trainingType;
    private LocalDate trainingTime;
    private LocalTime trainingDuration;

}
