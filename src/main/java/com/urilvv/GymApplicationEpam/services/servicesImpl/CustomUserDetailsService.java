package com.urilvv.GymApplicationEpam.services.servicesImpl;

import com.urilvv.GymApplicationEpam.exceptions.LoginBlockedException;
import com.urilvv.GymApplicationEpam.models.Trainee;
import com.urilvv.GymApplicationEpam.models.Trainer;
import com.urilvv.GymApplicationEpam.repositories.TraineeRepository;
import com.urilvv.GymApplicationEpam.repositories.TrainerRepository;
import com.urilvv.GymApplicationEpam.security.LoginAttemptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loginAttemptService.isBlocked()) {
            throw new LoginBlockedException();
        }

        Optional<Trainee> traineeOpt = traineeRepository.findByUsername(username);
        Optional<Trainer> trainerOpt = trainerRepository.findByUsername(username);

        if(traineeOpt.isPresent()) {
            Trainee trainee = traineeOpt.get();

            return User.withUsername(username)
                    .password(trainee.getPassword())
                    .roles("TRAINEE")
                    .build();
        }

        if (trainerOpt.isPresent()) {
            Trainer trainer = trainerOpt.get();

            return User.withUsername(username)
                    .password(trainer.getPassword())
                    .roles("TRAINER")
                    .build();
        }

        throw new UsernameNotFoundException("User was not found.");
    }

}
