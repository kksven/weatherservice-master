package com.training.weatherservice.controller;

import com.training.weatherservice.aspect.annotation.Monitor;
import com.training.weatherservice.business.WeatherService;
import com.training.weatherservice.business.dto.Weather;
import com.training.weatherservice.entities.WeatherInformation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Monitor
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    private static final String PATH = "/weathers";
    private static final String PATH_BY_ID = "/weathers/{weatherId}";

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            value = PATH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Weather> getWeather(){
        return weatherService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            value = PATH_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Weather getWeatherById(@PathVariable("weatherId") long weatherId){
        return weatherService.getById(weatherId);
    }

    @GetMapping(
            value = PATH,
            params = {"date"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Weather> getWeatherByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return weatherService.getByDate(date);
    }

    @GetMapping(
            value = PATH + "/report",
            params = {"startDate", "endDate"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Weather> getWeatherBetweenDates(
            @RequestParam(value ="startDate", required = false, defaultValue = "#{T(java.time.LocalDate).now().minusDays(30)}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value ="endDate", required = false, defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        return weatherService.getBetweenDate(startDate, endDate);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            value = PATH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Weather create(@RequestBody WeatherInformation request) {
        return weatherService.create(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(
            value = PATH_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void delete(@PathVariable("weatherId") long weatherId) {
        weatherService.delete(weatherId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(
            value = PATH_BY_ID,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Weather update(@PathVariable("weatherId") long weatherId,
                          @RequestBody WeatherInformation request) {
        return weatherService.update(weatherId, request);
    }
}
