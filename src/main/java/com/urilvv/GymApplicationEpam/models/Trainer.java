package com.urilvv.GymApplicationEpam.models;

import com.urilvv.GymApplicationEpam.enums.Specialization;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "trainers")
@NoArgsConstructor
public class Trainer extends User {

    @Enumerated(EnumType.STRING)
    @Column(name = "trainer_specialization")
    private Specialization trainerSpec;
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Training> trainerTrainings;

    public Trainer(String firstName, String lastName, String username, boolean isActive, String spec) {
        super(firstName, lastName, username, isActive);
        this.trainerSpec = Specialization.getSpec(spec + "_spec");
        this.trainerTrainings = new HashSet<>();
    }

    public void addTraining(Training training) {
        getTrainerTrainings().add(training);
        training.setTrainer(this);
    }

    public void removeTraining(Training training) {
        getTrainerTrainings().remove(training);
        training.setTrainer(null);
    }

}
