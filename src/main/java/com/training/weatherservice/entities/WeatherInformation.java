package com.training.weatherservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "weather_information")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class WeatherInformation {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private long weatherId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="locationId")
    private Location location;

    private LocalDate date;

    @ElementCollection
    private List<Double> temperature;
}
