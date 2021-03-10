package com.training.weatherservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.weatherservice.DummyWeatherInformation;
import com.training.weatherservice.business.dto.Weather;
import com.training.weatherservice.entities.WeatherInformation;
import com.training.weatherservice.repository.WeatherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherRepository repository;

    @Test
    @DisplayName("GET /widgets success with information ordered by id desc")
    public void shouldReturnWeatherInformationWhenCallGet() throws Exception {

        String firstRow = "{\"id\":37891,\"location\":{\"lat\":32.7767,\"lon\":96.797,\"city\":\"Dallas\",\"state\":\"Texas\"},\"date\":\"2020-09-15\",\"lowest\":84.3,\"highest\":93.1,\"temperature\":[89.7,84.3,91.2,93.1]}]";
        String secondRow = "[{\"id\":37892,\"location\":{\"lat\":32.7767,\"lon\":96.797,\"city\":\"Austin\",\"state\":\"Texas\"},\"date\":\"2020-09-15\",\"lowest\":84.3,\"highest\":93.1,\"temperature\":[89.7,84.3,91.2,93.1]}";
        String expected = secondRow + "," + firstRow;

        List<WeatherInformation> responseRepository = Arrays.asList(DummyWeatherInformation.build(37891, "Dallas"), DummyWeatherInformation.build(37892, "Austin"));
        given(repository.findAll()).willReturn(responseRepository);

        mvc.perform(MockMvcRequestBuilders.get("/weathers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @DisplayName("GET by Id /widgets/1")
    public void shouldReturnWeatherInformationWhenCallById() throws Exception {

        String expected = "{\"id\":1,\"location\":{\"lat\":32.7767,\"lon\":96.797,\"city\":\"Dallas\",\"state\":\"Texas\"},\"date\":\"2020-09-15\",\"lowest\":84.3,\"highest\":93.1,\"temperature\":[89.7,84.3,91.2,93.1]}";

        WeatherInformation responseRepository = DummyWeatherInformation.build(1L, "Dallas");
        doReturn(Optional.of(responseRepository)).when(repository).findById(anyLong());

        mvc.perform(MockMvcRequestBuilders.get("/weathers/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @DisplayName("GET by Id /widgets/n")
    public void shouldReturnHttpCode400WhenCallWithBarArgument() throws Exception {

        String exceptionParam = "Bad arguments";

        mvc.perform(MockMvcRequestBuilders.get("/weathers/{exception_id}", exceptionParam)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET by date /weathers?date=2020-09-15")
    public void shouldReturnWeatherInformationWhenCallByDate() throws Exception {

        String firstRow = "{\"id\":37891,\"location\":{\"lat\":32.7767,\"lon\":96.797,\"city\":\"Dallas\",\"state\":\"Texas\"},\"date\":\"2020-09-15\",\"lowest\":84.3,\"highest\":93.1,\"temperature\":[89.7,84.3,91.2,93.1]}]";
        String secondRow = "[{\"id\":37892,\"location\":{\"lat\":32.7767,\"lon\":96.797,\"city\":\"Austin\",\"state\":\"Texas\"},\"date\":\"2020-09-15\",\"lowest\":84.3,\"highest\":93.1,\"temperature\":[89.7,84.3,91.2,93.1]}";
        String expected = secondRow + "," + firstRow;

        List<WeatherInformation> responseRepository = Arrays.asList(DummyWeatherInformation.build(37891, "Dallas"), DummyWeatherInformation.build(37892, "Austin"));
        doReturn(responseRepository).when(repository).findByDate(any(LocalDate.class));

        mvc.perform(MockMvcRequestBuilders.get("/weathers?date=2020-09-15").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @DisplayName("GET between dates /weathers/report?startDate=2020-01-12&endDate=2020-09-30")
    public void shouldReturnWeatherInformationWhenCallBetweenDate() throws Exception {

        String firstRow = "{\"id\":37891,\"location\":{\"lat\":32.7767,\"lon\":96.797,\"city\":\"Dallas\",\"state\":\"Texas\"},\"date\":\"2020-09-15\",\"lowest\":84.3,\"highest\":93.1,\"temperature\":[89.7,84.3,91.2,93.1]}]";
        String secondRow = "[{\"id\":37892,\"location\":{\"lat\":32.7767,\"lon\":96.797,\"city\":\"Austin\",\"state\":\"Texas\"},\"date\":\"2020-09-15\",\"lowest\":84.3,\"highest\":93.1,\"temperature\":[89.7,84.3,91.2,93.1]}";
        String expected = secondRow + "," + firstRow;

        List<WeatherInformation> responseRepository = Arrays.asList(DummyWeatherInformation.build(37891, "Dallas"), DummyWeatherInformation.build(37892, "Austin"));
        doReturn(responseRepository).when(repository).findByDateBetween(any(LocalDate.class), any(LocalDate.class));

        mvc.perform(MockMvcRequestBuilders.get("/weathers/report?startDate=2020-01-12&endDate=2020-09-30").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @DisplayName("POST /rest/widget")
    public void shouldReturnWeatherInformationWhenCallCreate() throws Exception {

        Weather weatherInformation = DummyWeatherInformation.weatherDtoBuild(37892, "Austin");
        doReturn(weatherInformation).when(repository).save(any());

        mvc.perform( MockMvcRequestBuilders
                .post("/weathers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(weatherInformation)));
    }

    @Test
    @DisplayName("PUT /rest/widget")
    public void shouldReturnWeatherInformationWhenCallUpdate() throws Exception {

        Weather weatherInformation = DummyWeatherInformation.weatherDtoBuild(37892, "Austin");
        doReturn(weatherInformation).when(repository).save(any());

        mvc.perform( MockMvcRequestBuilders
                .put("/weathers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(weatherInformation)));
    }

    @Test
    @DisplayName("DELETE /widgets success")
    public void shouldReturnOkWhenCallDelete() throws Exception {

        long id = 2L;
        WeatherInformation weatherInformation = DummyWeatherInformation.build(id, "Austin");
        when(repository.findById(anyLong())).thenReturn(Optional.of(weatherInformation));

        doNothing().when(repository).deleteById(1L);

        mvc.perform(MockMvcRequestBuilders.delete("/weathers/37892")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
