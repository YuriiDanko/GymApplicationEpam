package com.urilvv.GymApplicationEpam.models;

import com.urilvv.GymApplicationEpam.enums.Specialization;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Trainer extends User {

    private Specialization trainerSpec;

    public Trainer(String firstName, String lastName, String username, boolean isActive, String spec) {
        super(firstName, lastName, username, isActive);
        this.trainerSpec = Specialization.getSpec(spec + "_spec");
    }

    @Override
    public String toString() {
        return String.format("Trainer - %s; Training Specialization - %s", super.getUsername(),
                this.trainerSpec.getName());
    }
}
