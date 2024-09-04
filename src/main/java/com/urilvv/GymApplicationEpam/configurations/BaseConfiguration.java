package com.urilvv.GymApplicationEpam.configurations;

import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.models.Training;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan("com.urilvv.*")
public class BaseConfiguration {

    @Bean("traineeStorage")
    public Map<String, Trainee> getTraineeStorage(){
        return new HashMap<>();
    }

    @Bean("trainerStorage")
    public Map<String, Trainer> getTrainerStorage(){
        return new HashMap<>();
    }

    @Bean("trainingStorage")
    public Map<String, Training> getTrainingStorage(){
        return new HashMap<>();
    }

}
