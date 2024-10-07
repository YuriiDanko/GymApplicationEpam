package com.urilvv.GymApplicationEpam.enums;

import lombok.Getter;

@Getter
public enum TrainingType {

    GROUP("group_training"), SOLO("solo_training");

    private final String name;

    TrainingType(String name) {
        this.name = name;
    }

    public static TrainingType getType(String name){
        for(TrainingType type : TrainingType.values()){
            if(type.getName().equals(name)){
                return type;
            }
        }

        throw new IllegalArgumentException("Specialization is not found.");
    }

}
