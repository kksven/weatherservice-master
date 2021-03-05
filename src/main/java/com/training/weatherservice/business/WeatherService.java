package com.training.weatherservice.business;

import com.training.weatherservice.business.dto.Weather;
import com.training.weatherservice.entities.WeatherInformation;

import java.time.LocalDate;
import java.util.List;

public interface WeatherService {

    /**
     * all weather information
     *
     * @return all entities weather information
     */
    List<Weather> getAll();

    /**
     * Retrieves a Weather Information by its id.
     *
     * @param id to search for
     * @return the entity with the given id or exception if nothing found.
     */
    Weather getById(Long id);

    /**
     *
     * @return
     */
    List<Weather> getByDate(LocalDate date);

    /**
     *
     * @return
     */
    List<Weather> getBetweenDate(LocalDate startDate, LocalDate endDate);

    /**
     *
     * Creates a new Weather Information and saves it on db.
     *
     * @param weatherInformation
     * @return the saved Weather Information
     */
    Weather create(WeatherInformation weatherInformation);

    /**
     * Update a Weather Information
     *
     * @param weatherInformation
     * @return the updated Weather Information
     */
    Weather update(Long id, WeatherInformation weatherInformation);

    /**
     *
     * Deletes the Weather Information with the given id.
     * @param id of the Weather Information to be deleted
     */
    void delete(Long id);
}
