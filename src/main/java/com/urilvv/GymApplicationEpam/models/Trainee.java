package com.urilvv.GymApplicationEpam.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "traineeTrainings", callSuper = false)
@ToString(callSuper = true)
@Entity
@Table(name = "trainees")
@NoArgsConstructor
public class Trainee extends User {

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "trainee_address")
    private String address;
    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<Training> traineeTrainings;

    public Trainee(String firstName, String lastName, String username,
                   boolean isActive, LocalDate dateOfBirth, String address) {
        super(firstName, lastName, username, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.traineeTrainings = new HashSet<>();
    }

    public void addTraining(Training training) {
        getTraineeTrainings().add(training);
        training.setTrainee(this);
    }

}