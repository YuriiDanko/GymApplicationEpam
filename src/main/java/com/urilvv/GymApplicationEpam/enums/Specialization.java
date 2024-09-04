package com.urilvv.GymApplicationEpam.enums;

public enum Specialization {

    CARDIO("cardio_spec"), STRENGTH("strength_spec"), BODYBUILDING("bodybuilding_spec");

    private String name;

    Specialization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
