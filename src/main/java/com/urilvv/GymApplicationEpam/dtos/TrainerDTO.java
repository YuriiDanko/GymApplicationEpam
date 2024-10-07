package com.urilvv.GymApplicationEpam.dtos;

import com.urilvv.GymApplicationEpam.enums.Specialization;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TrainerDTO {

    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private boolean isActive;
    private Specialization spec;

}
