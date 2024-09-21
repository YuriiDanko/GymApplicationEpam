package com.urilvv.GymApplicationEpam.models;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "trainings")
@RequiredArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "training_id", nullable = false, unique = true)
    private String trainingId;
    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
    @Column(name = "training_name")
    private String trainingName;
    @Enumerated(EnumType.STRING)
    @Column(name = "training_type")
    private TrainingType trainingType;
    @Column(name = "training_date")
    private LocalDate trainingTime;
    @Column(name = "training_duration")
    private LocalTime trainingDuration;

    public Training(Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType, LocalDate trainingTime, LocalTime trainingDuration) {
        this.trainingId = UUID.randomUUID().toString();
        this.trainee = trainee;
        this.trainer = trainer;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingTime = trainingTime;
        this.trainingDuration = trainingDuration;
    }

}
