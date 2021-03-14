package com.training.weatherservice.business.imp;

import com.training.weatherservice.business.WeatherService;
import com.training.weatherservice.business.dto.Weather;
import com.training.weatherservice.entities.WeatherInformation;
import com.training.weatherservice.exception.CustomNotFoundException;
import com.training.weatherservice.repository.WeatherRepository;
import com.training.weatherservice.util.DateRequestBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class WeatherServiceImp implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final DateRequestBuilder dateRequestBuilder;

    public WeatherServiceImp(WeatherRepository weatherRepository, DateRequestBuilder dateRequestBuilder) {
        this.weatherRepository = weatherRepository;
        this.dateRequestBuilder = dateRequestBuilder;
    }

    @Override
    public List<Weather> getAll() {

        List<Weather> weatherInformationList = weatherRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(WeatherInformation::getWeatherId).reversed())
                .map(WeatherBuilder::build)
                .collect(Collectors.toList());

        throwDataNotFoundExceptionWhenEmptyList(weatherInformationList);

        return  weatherInformationList;
    }

    @Override
    public Weather getById(Long id) {
        return weatherRepository
                .findById(id)
                .map(WeatherBuilder::build)
                .orElseThrow(() -> new CustomNotFoundException("Weather Information Not Found"));
    }

    @Override
    public List<Weather> getByDate(LocalDate date) {

        var weatherInformationList = weatherRepository.findByDate(date).stream()
                .sorted(Comparator.comparing(WeatherInformation::getWeatherId).reversed())
                .map(WeatherBuilder::build)
                .collect(Collectors.toList());

        throwDataNotFoundExceptionWhenEmptyList(weatherInformationList);

        return weatherInformationList;
    }

    @Override
    public List<Weather> getBetweenDate(LocalDate startDate, LocalDate endDate) {

        List<Weather> weatherInformationList = weatherRepository.findByDateBetween(startDate, endDate)
                .stream()
                .sorted(Comparator.comparing(WeatherInformation::getCityFromLocation))
                .map(WeatherBuilder::build)
                .collect(Collectors.toList());

        throwDataNotFoundExceptionWhenEmptyList(weatherInformationList);

        return weatherInformationList;
    }

    @Override
    public Weather create(WeatherInformation weatherInformation) {
        WeatherInformation weather = weatherRepository.save(weatherInformation);
        return WeatherBuilder.build(weather);
    }

    @Override
    public Weather update(Long id, WeatherInformation weatherInformation) {

        throwDataNotFoundExceptionWhenNotExistId(id);

        weatherInformation.setWeatherId(id);
        WeatherInformation weather = weatherRepository.save(weatherInformation);

        return WeatherBuilder.build(weather);
    }

    @Override
    public void delete(Long id) {
        throwDataNotFoundExceptionWhenNotExistId(id);

        weatherRepository.deleteById(id);
    }

    private void throwDataNotFoundExceptionWhenEmptyList(List<Weather> list){

        if (list.isEmpty()) {
            throw new CustomNotFoundException("Weather Information Not Found to dates range");
        }
    }

    private void throwDataNotFoundExceptionWhenNotExistId(Long id){
        weatherRepository
                .findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Weather Information Not Found"));
    }

}
