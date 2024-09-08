package com.urilvv.GymApplicationEpam.models;

import java.time.LocalDate;
import java.time.Period;

public class Trainee extends User {

    private LocalDate dateOfBirth;
    private String address;

    public Trainee(String firstName, String lastName, String username,
                   boolean isActive, LocalDate dateOfBirth, String address) {
        super(firstName, lastName, username, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("Trainee - %s; Age - %sy.o.", super.getUsername(),
                Period.between(this.dateOfBirth, LocalDate.now()).getYears());
    }

}