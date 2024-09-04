package com.urilvv.GymApplicationEpam.models;

import java.time.LocalDate;

public class Trainee extends User{

    private LocalDate dateOfBirth;
    private String address;

    public Trainee(String firstName, String lastName, String password, String username,
                   boolean isActive, LocalDate dateOfBirth, String address) {
        super(firstName, lastName, password, username, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static class TraineeBuilder{

        private String firstName;
        private String lastName;
        private String password;
        private String username;
        private boolean isActive;
        private LocalDate dateOfBirth;
        private String address;

        public static TraineeBuilder builder(){
            return new TraineeBuilder();
        }

        public Trainee build(){
            return new Trainee(firstName, lastName, password,
                    username, isActive, dateOfBirth, address);
        }

        public TraineeBuilder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public TraineeBuilder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public TraineeBuilder password(String password){
            this.password = password;
            return this;
        }

        public TraineeBuilder username(String username){
            this.username = username;
            return this;
        }

        public TraineeBuilder isActive(boolean isActive){
            this.isActive = isActive;
            return this;
        }

        public TraineeBuilder dateOfBirth(LocalDate dateOfBirth){
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public TraineeBuilder address(String address){
            this.address = address;
            return this;
        }

    }

}