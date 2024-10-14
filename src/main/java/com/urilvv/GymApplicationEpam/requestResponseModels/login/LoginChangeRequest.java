package com.urilvv.GymApplicationEpam.requestResponseModels.login;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginChangeRequest {

    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;

}
