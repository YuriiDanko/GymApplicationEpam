package com.urilvv.GymApplicationEpam.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public abstract class User {

    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private boolean isActive;

    public User(String firstName, String lastName, String username, boolean isActive) {
        this.userId = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = generatePassword();
        this.username = username;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public static String generatePassword() {
        List<CharacterRule> rules = Arrays.asList(
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1)
        );

        PasswordGenerator passwordGenerator = new PasswordGenerator();

        return passwordGenerator.generatePassword(10, rules);
    }

}
