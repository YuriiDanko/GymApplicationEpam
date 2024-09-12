package com.urilvv.GymApplicationEpam.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.Period;

@Data
@EqualsAndHashCode(callSuper = true)
public class Trainee extends User {

    private LocalDate dateOfBirth;
    private String address;

    public Trainee(String firstName, String lastName, String username,
                   boolean isActive, LocalDate dateOfBirth, String address) {
        super(firstName, lastName, username, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("Trainee - %s; Age - %sy.o.", super.getUsername(),
                Period.between(this.dateOfBirth, LocalDate.now()).getYears());
    }

}