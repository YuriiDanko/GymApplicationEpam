package com.urilvv.GymApplicationEpam.configurations;

import org.passay.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("com.urilvv.*")
public class BaseConfiguration {

    @Bean
    public PasswordValidator getPasswordValidator() {
        return new PasswordValidator(
                new LengthRule(10, 18),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new WhitespaceRule()
        );
    }

}
