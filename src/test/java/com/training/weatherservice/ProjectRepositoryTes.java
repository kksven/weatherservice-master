package com.training.weatherservice;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
public class ProjectRepositoryTes {
    private static final LocalDate LOCAL_DATE = LocalDate.of(2020, 07, 05);

    @TestConfiguration
    static class FakeClockConfig {

        @Bean
        public Clock clock() {
            return Clock.fixed(LOCAL_DATE.atTime(9, 5).toInstant(ZoneOffset.UTC), ZoneId.of("CET"));
        }
    }
}
