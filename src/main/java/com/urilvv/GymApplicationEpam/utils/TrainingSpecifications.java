package com.urilvv.GymApplicationEpam.utils;

import com.urilvv.GymApplicationEpam.models.Training;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TrainingSpecifications {

    public static Specification<Training> hasTraineeUsername(String traineeUsername) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("trainee").get("username"), traineeUsername);
    }

    public static Specification<Training> hasTrainerUsername(String trainerUsername) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("trainer").get("username"), trainerUsername);
    }

    public static Specification<Training> betweenTrainingTime(LocalDate from, LocalDate to) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("trainingTime"), from, to);
    }

    public static Specification<Training> hasTrainingType(String trainingType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("trainingType"), trainingType);
    }
}
