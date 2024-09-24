package com.urilvv.GymApplicationEpam.models;

import com.urilvv.GymApplicationEpam.enums.TrainingType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "trainings")
@NoArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "training_id", nullable = false, unique = true)
    private String trainingId;
    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private Trainee trainee;
    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;
    @Column(name = "training_name", nullable = false)
    private String trainingName;
    @Enumerated(EnumType.STRING)
    @Column(name = "training_type", nullable = false)
    private TrainingType trainingType;
    @Column(name = "training_date", nullable = false)
    private LocalDate trainingTime;
    @Column(name = "training_duration", nullable = false)
    private LocalTime trainingDuration;

    public Training(Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType, LocalDate trainingTime, LocalTime trainingDuration) {
        this.trainee = trainee;
        this.trainer = trainer;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingTime = trainingTime;
        this.trainingDuration = trainingDuration;
    }

}
