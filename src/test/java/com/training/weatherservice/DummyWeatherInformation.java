package com.training.weatherservice;

import com.training.weatherservice.business.dto.Location;
import com.training.weatherservice.business.dto.Weather;
import com.training.weatherservice.entities.WeatherInformation;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DummyWeatherInformation {

    public static WeatherInformation build(long id, String city){
        LocalDate dateTest = LocalDate.of(2020, 9, 15);
        List<Double> temperature = Arrays.asList(89.7,84.3,91.2,93.1);
        return WeatherInformation.builder()
                .weatherId(id)
                .date(dateTest)
                .location(locationBuild(id, city))
                .temperature(temperature)
                .build();
    }

    public static Weather weatherDtoBuild(long id, String city){
        LocalDate dateTest = LocalDate.of(2020, 9, 15);
        List<Double> temperature = Arrays.asList(89.7,84.3,91.2,93.1);
        Location location = locationDTOBuild(city);
        List<Double> temperatureSorted = temperature.stream().sorted().collect(Collectors.toList());


        return Weather.builder()
                .id(id)
                .date(dateTest)
                .lowest(temperatureSorted.get(0))
                .highest(temperatureSorted.get(temperatureSorted.size()-1))
                .location(location)
                .temperature(temperature)
                .build();
    }

    private static com.training.weatherservice.entities.Location locationBuild(long id, String city){
        return com.training.weatherservice.entities.Location.builder()
                .locationId(id)
                .lat(32.7767)
                .lon(96.7970)
                .city(city)
                .state("Texas")
                .build();
    }

    private static Location locationDTOBuild(String city){
        return Location.builder()
                .lat(32.7767)
                .lon(96.7970)
                .city(city)
                .state("Texas")
                .build();
    }
}
