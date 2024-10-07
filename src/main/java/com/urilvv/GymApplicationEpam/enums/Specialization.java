package com.urilvv.GymApplicationEpam.enums;

import lombok.Getter;

@Getter
public enum Specialization {

    CARDIO("cardio_spec"), STRENGTH("strength_spec"), BODYBUILDING("bodybuilding_spec");

    private final String name;

    Specialization(String name) {
        this.name = name;
    }

    public static Specialization getSpec(String name){
        for(Specialization spec : Specialization.values()){
            if(spec.getName().equals(name)){
                return spec;
            }
        }

        throw new IllegalArgumentException("Specialization is not found.");
    }
}
