package com.urilvv.GymApplicationEpam.requestResponseModels.register;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class RegisterRequest {

    @NotBlank(message = "First Name cannot be blank or null!")
    private String firstName;
    @NotBlank(message = "Last Name cannot be blank or null!")
    private String lastName;

}
