package com.urilvv.GymApplicationEpam.enums;

public enum TrainingType {

    GROUP("group_training"), SOLO("solo_training");

    private String name;

    TrainingType(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
