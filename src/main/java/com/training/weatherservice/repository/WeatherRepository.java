package com.training.weatherservice.repository;

import com.training.weatherservice.entities.WeatherInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherInformation, Long> {
    List<WeatherInformation> findByDate(LocalDate date);
    List<WeatherInformation> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
