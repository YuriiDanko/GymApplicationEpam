package com.urilvv.GymApplicationEpam.healthIndicators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class DatabaseConnectivityHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        try(Connection connection = dataSource.getConnection()) {
            if(!connection.isClosed()) {
                return builder.up().withDetail("database", "Connection is alive, database can be used.").build();
            } else {
                return builder.down().withDetail("database", "Connection is closed, database cannot be used").build();
            }
        } catch (SQLException e) {
            return builder.down(e).build();
        }
    }

}
