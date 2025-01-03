package com.urilvv.GymApplicationEpam.repositories;

import com.urilvv.GymApplicationEpam.models.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, String>, JpaSpecificationExecutor<Training> {
}
