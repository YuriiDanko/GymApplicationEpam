package com.urilvv.GymApplicationEpam.models;

import com.urilvv.GymApplicationEpam.enums.Specialization;

public class Trainer extends User {

    private Specialization trainerSpec;

    public Trainer(String firstName, String lastName, String username, boolean isActive, String spec) {
        super(firstName, lastName, username, isActive);
        this.trainerSpec = Specialization.getSpec(spec + "_spec");
    }

    public Specialization getTrainerSpec() {
        return trainerSpec;
    }

    public void setTrainerSpec(Specialization traineeSpec) {
        this.trainerSpec = traineeSpec;
    }

    @Override
    public String toString() {
        return String.format("Trainer - %s; Training Specialization - %s", super.getUsername(),
                this.trainerSpec.getName());
    }
}
