package com.training.weatherservice.business.imp;


import com.training.weatherservice.business.dto.Location;
import com.training.weatherservice.business.dto.Weather;
import com.training.weatherservice.entities.WeatherInformation;

import java.util.List;
import java.util.stream.Collectors;

public class WeatherBuilder {
    protected static Weather build(WeatherInformation weatherInformation){

        var locationDTO = createLocationDTO(weatherInformation.getLocation());
        var tempSorted = getTemperatureListOrdered(weatherInformation);

        return Weather.builder()
                .id(weatherInformation.getWeatherId())
                .date(weatherInformation.getDate())
                .highest(getHighest(tempSorted))
                .lowest(getLowest(tempSorted))
                .location(locationDTO)
                .temperature(weatherInformation.getTemperature())
                .build();
    }

    private static Location createLocationDTO(com.training.weatherservice.entities.Location location) {
        return Location.builder()
                .lon(location.getLon())
                .lat(location.getLat())
                .city(location.getCity())
                .state(location.getState())
                .build();
    }

    private static List<Double> getTemperatureListOrdered(WeatherInformation weatherInformation){
        return weatherInformation
                .getTemperature()
                .stream()
                .sorted()
                .collect(Collectors.toList());

    }

    private static Double getHighest(List<Double> temp){
        return temp.get(temp.size()-1);
    }

    private static Double getLowest(List<Double> temp){
        return temp.get(0);
    }
}
