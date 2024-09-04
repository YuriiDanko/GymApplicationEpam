package com.urilvv.GymApplicationEpam.models;

import java.util.UUID;

public abstract class User {

    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private boolean isActive;

    /*
    * TODO: - Username going to be calculated from Trainer/Trainee first name and last name
    *       by concatenation by using dot as a separator (eg. John.Smith)
    *       - In the case that already exists Trainer or Trainee with the same pair of first and
    *       last name as a suffix to the username should be added a serial number.
    *       - Password should be generated as a random 10 chars length string.
    * */

    public User(String firstName, String lastName, String password, String username, boolean isActive) {
        this.userId = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
        this.isActive = isActive;
    }

    public User(){
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
