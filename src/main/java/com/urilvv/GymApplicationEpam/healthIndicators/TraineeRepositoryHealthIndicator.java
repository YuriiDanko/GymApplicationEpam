package com.urilvv.GymApplicationEpam.healthIndicators;

import com.urilvv.GymApplicationEpam.repositories.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraineeRepositoryHealthIndicator implements HealthIndicator {

    private final ApplicationContext applicationContext;

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        TraineeRepository repository = applicationContext.getBean(TraineeRepository.class);

        return repository != null ? builder.up().withDetail("repository", "Trainee Repository is working.").build()
                : builder.up().withDetail("repository", "Trainee Repository was not created.").build();
    }

}
