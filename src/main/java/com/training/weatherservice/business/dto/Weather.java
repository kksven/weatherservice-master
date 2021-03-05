package com.training.weatherservice.business.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Weather implements Serializable {
    private long id;
    private Location location;
    private LocalDate date;
    private Double lowest;
    private Double highest;
    private List<Double> temperature;
}
