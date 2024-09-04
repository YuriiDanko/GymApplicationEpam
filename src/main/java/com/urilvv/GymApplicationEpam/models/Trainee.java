package com.urilvv.GymApplicationEpam.models;

import java.time.LocalDate;

public class Trainee extends User{

    private LocalDate dateOfBirth;
    private String address;

    public Trainee(String firstName, String lastName, String password, String username, boolean isActive) {
        super(firstName, lastName, password, username, isActive);
    }

}
