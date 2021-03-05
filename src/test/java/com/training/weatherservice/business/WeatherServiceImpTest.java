package com.training.weatherservice.business;

import com.training.weatherservice.DummyWeatherInformation;
import com.training.weatherservice.business.dto.Weather;
import com.training.weatherservice.business.imp.WeatherServiceImp;
import com.training.weatherservice.entities.WeatherInformation;
import com.training.weatherservice.exception.CustomNotFoundException;
import com.training.weatherservice.repository.WeatherRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceImpTest {

    @InjectMocks
    WeatherServiceImp service;

    @Mock
    private WeatherRepository repository;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for getAll()
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("return Weather Information list, order by reverse ID")
    public void shouldCallRepositoryGetAll() {
        List<WeatherInformation> responseRepository = Arrays.asList(DummyWeatherInformation.build(1, "Dallas"), DummyWeatherInformation.build(2, "Austin"));
        List<Weather> expected = Arrays.asList(DummyWeatherInformation.weatherDtoBuild(2, "Austin"), DummyWeatherInformation.weatherDtoBuild(1, "Dallas"));
        when(repository.findAll()).thenReturn(responseRepository);

        List<Weather> result = service.getAll();

        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
        assertEquals(expected, result);
        assertEquals(2, result.get(0).getId());
    }

    @Test
    @DisplayName("Throw DataNotFoundException when repository result is empty")
    public void shouldThrowExceptionWhenCallGetAll() {
        when(repository.findAll()).thenReturn(Lists.emptyList());

        Assertions.assertThrows(CustomNotFoundException.class, () -> service.getAll());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for getByID()
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("return Weather Information by ID")
    public void shouldCallRepositoryGetById() {

        WeatherInformation weatherInformation = DummyWeatherInformation.build(1, "Dallas");
        when(repository.findById(anyLong())).thenReturn(Optional.of(weatherInformation));
        Weather expected = DummyWeatherInformation.weatherDtoBuild(1, "Dallas");

        Weather result = service.getById(1L);

        verify(repository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(repository);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Throw DataNotFoundException when repository result is empty")
    public void shouldThrowExceptionCallRepositoryGetById() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomNotFoundException.class, () -> service.getById(1L));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for getByDate()
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    @Test
    @DisplayName("return Weather Information by date")
    public void shouldCallRepositoryGetByDate() {

        List<WeatherInformation> responseRepository = Arrays.asList(DummyWeatherInformation.build(1, "Dallas"), DummyWeatherInformation.build(2, "Austin"));
        List<Weather> expected = Arrays.asList(DummyWeatherInformation.weatherDtoBuild(2, "Austin"), DummyWeatherInformation.weatherDtoBuild(1, "Dallas"));
        when(repository.findByDate(any())).thenReturn(responseRepository);

        List<Weather> result = service.getByDate(LocalDate.now());

        verify(repository, times(1)).findByDate(any(LocalDate.class));
        verifyNoMoreInteractions(repository);
        assertEquals(expected, result);
        assertEquals(2, result.get(0).getId());
    }

    @Test
    @DisplayName("Throw DataNotFoundException when repository result is empty")
    public void shouldThrowExceptionCallRepositoryGetByDate() {

        Assertions.assertThrows(CustomNotFoundException.class, () -> service.getByDate(LocalDate.now()));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for getBetweenDates()
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("return Weather Information between dates ")
    public void shouldCallRepositoryGetBetweenDate() {

        List<WeatherInformation> responseRepository = Arrays.asList(DummyWeatherInformation.build(1, "Dallas"), DummyWeatherInformation.build(2, "Austin"));
        List<Weather> expected = Arrays.asList(DummyWeatherInformation.weatherDtoBuild(2, "Austin"), DummyWeatherInformation.weatherDtoBuild(1, "Dallas"));
        when(repository.findByDateBetween(any(),any())).thenReturn(responseRepository);

        List<Weather> result = service.getBetweenDate(LocalDate.now(), LocalDate.now());

        verify(repository, times(1)).findByDateBetween(any(LocalDate.class), any(LocalDate.class));
        verifyNoMoreInteractions(repository);
        assertEquals(expected, result);
        assertEquals(2, result.get(0).getId());
    }

    @Test
    @DisplayName("Throw DataNotFoundException when repository result is empty")
    public void shouldThrowExceptionCallRepositoryGetBetweenDate() {

        Assertions.assertThrows(CustomNotFoundException.class, () -> service.getBetweenDate(LocalDate.now(), LocalDate.now()));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for create()
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    @Test
    @DisplayName("return Weather information created")
    public void shouldCallRepositorySave() {
        WeatherInformation weatherInformation = DummyWeatherInformation.build(0L, "Dallas");
        when(repository.save(any(WeatherInformation.class))).thenReturn(DummyWeatherInformation.build(1L, "Dallas"));

        Weather result = service.create(weatherInformation);

        verify(repository, times(1)).save(eq(weatherInformation));
        assertEquals(1L, result.getId());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for update()
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    @Test
    @DisplayName("return Weather information updated")
    public void shouldPassWhenUpdateWeatherInformationWithoutErrors() {
        long id = 2L;
        WeatherInformation weatherInformation = DummyWeatherInformation.build(id, "Austin");
        when(repository.findById(anyLong())).thenReturn(Optional.of(weatherInformation));
        when(repository.save(any(WeatherInformation.class))).thenReturn(DummyWeatherInformation.build(2L, "Austin"));

        Weather result = service.update(id, weatherInformation);

        verify(repository, times(1)).save(eq(weatherInformation));
        verify(repository, times(1)).findById(eq(id));
        assertEquals(2L, result.getId());
        assertEquals("Austin", result.getLocation().getCity());
    }

    @Test()
    @DisplayName("Throw DataNotFoundException when Weather Information not exist")
    public void shouldThrowDataNotFoundExceptionWhenItemToUpdateIsNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomNotFoundException.class, () -> {
            service.update(1L, DummyWeatherInformation.build(3L, "El Paso"));
        });
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for delete()
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    @Test
    public void shouldCallRepositoryDelete() {
        long id = 3L;

        WeatherInformation weatherInformation = DummyWeatherInformation.build(id, "Dallas");
        when(repository.findById(anyLong())).thenReturn(Optional.of(weatherInformation));
        doNothing().when(repository).deleteById(anyLong());

        service.delete(id);

        verify(repository, times(1)).deleteById(eq(id));
    }
}
