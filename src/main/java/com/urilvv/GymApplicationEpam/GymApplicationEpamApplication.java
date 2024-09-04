package com.urilvv.GymApplicationEpam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GymApplicationEpamApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymApplicationEpamApplication.class, args);
	}

	@Bean
	public CommandLineRunner keepAliveRunner() {
		return args -> {
			synchronized (GymApplicationEpamApplication.class) {
				GymApplicationEpamApplication.class.wait();
			}
		};
	}

}
