package com.urilvv.GymApplicationEpam.requestResponseModels.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterResponse {

    private String userId;
    private String username;
    private String password;

}
