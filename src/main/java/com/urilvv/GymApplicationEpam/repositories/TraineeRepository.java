package com.urilvv.GymApplicationEpam.repositories;

import com.urilvv.GymApplicationEpam.models.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, String> {

    Optional<Trainee> findByUsername(String username);

}
