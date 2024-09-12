package com.urilvv.GymApplicationEpam.facade;

import com.urilvv.GymApplicationEpam.services.TraineeService;
import com.urilvv.GymApplicationEpam.services.TrainerService;
import com.urilvv.GymApplicationEpam.services.TrainingService;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ServicesFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public void facadeMethod() {
        // Method which will combine other services methods into one, that will hide complexity of these methods' implementation.
    }

}
