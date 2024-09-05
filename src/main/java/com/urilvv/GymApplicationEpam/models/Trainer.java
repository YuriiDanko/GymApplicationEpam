package com.urilvv.GymApplicationEpam.models;

import com.urilvv.GymApplicationEpam.enums.Specialization;

public class Trainer extends User{

    private Specialization trainerSpec;

    public Trainer(String firstName, String lastName, String password, String username, boolean isActive, String spec) {
        super(firstName, lastName, password, username, isActive);
        this.trainerSpec = Specialization.getSpec(spec + "_spec");
    }

    public Trainer(){
        super();
    }

    public Specialization getTrainerSpec() {
        return trainerSpec;
    }

    public void setTrainerSpec(Specialization traineeSpec) {
        this.trainerSpec = traineeSpec;
    }

    @Override
    public String toString() {
        return String.format("Trainer - %s; Training Specialization - %s", super.getFirstName() + super.getLastName(),
                this.trainerSpec.getName());
    }
}
