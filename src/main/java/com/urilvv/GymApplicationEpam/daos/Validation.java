package com.urilvv.GymApplicationEpam.daos;

import com.urilvv.GymApplicationEpam.models.User;

public interface Validation {

    <T extends User> boolean validate(T entity);

}
