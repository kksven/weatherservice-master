package com.training.weatherservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.weatherservice.DummyWeatherInformation;
import com.training.weatherservice.business.dto.Weather;
import com.training.weatherservice.entities.WeatherInformation;
import com.training.weatherservice.repository.WeatherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
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

    @Mock
    private Clock clock;
    private Clock fixedClock;

    @MockBean
    private WeatherRepository repository;

    @Captor
    ArgumentCaptor<LocalDate> argumentsStartDate;
    @Captor
    ArgumentCaptor<LocalDate> argumentsEndDate;

    private final static LocalDate LOCAL_DATE = LocalDate.of(2021, 2, 28);

    @BeforeEach
    void init(){
        fixedClock = Clock.fixed(LOCAL_DATE.atTime(9, 5).toInstant(ZoneOffset.UTC), ZoneId.of("CET"));
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for GET /weathers
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for /weathers/{weatherId}
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for GET /weathers?date={date}
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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
    @DisplayName("return Bad Request when start date is invalid")
    public void shouldBadRequestExceptionWhenCallByInvalidDateFormat() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/weathers?date=2020-13-15").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for GET /weather/report?startDate={startDate}&endDate={endDate}
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("return Bad Request when start date is invalid")
    public void shouldReturnBadRequestWhenStartDateIsInvalid() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/weathers/report?startDate=2020-25-12&endDate=2020-09-30").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("return Bad Request when end date is invalid")
    public void shouldReturnBadRequestWhenEndDateIsInvalid() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/weathers/report?startDate=2020-01-12&endDate=2020-09-38").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET between dates /weathers/report?startDate=2020-01-01")
    public void shouldReturnWeatherInformationWhenCallStartDateOnly() throws Exception {

        List<WeatherInformation> responseRepository = Arrays.asList(DummyWeatherInformation.build(37891, "Dallas"), DummyWeatherInformation.build(37892, "Austin"));
        doReturn(responseRepository).when(repository).findByDateBetween(any(LocalDate.class), any(LocalDate.class));

        mvc.perform(MockMvcRequestBuilders.get("/weathers/report?startDate=2021-01-01")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(repository, times(1)).findByDateBetween(argumentsStartDate.capture(), argumentsEndDate.capture());
        Assertions.assertEquals(LocalDate.of(2021, 01, 01), argumentsStartDate.getValue());
        //Assertions.assertEquals(LOCAL_DATE, argumentsEndDate.getValue());
    }

    @Test
    @DisplayName("GET between dates /weathers/report?endDate=2020-09-30")
    public void shouldReturnWeatherInformationWhenCallEndDateOnly() throws Exception {

        LocalDate expectedDate = LOCAL_DATE.minusDays(30);

        List<WeatherInformation> responseRepository = Arrays.asList(DummyWeatherInformation.build(37891, "Dallas"), DummyWeatherInformation.build(37892, "Austin"));
        doReturn(responseRepository).when(repository).findByDateBetween(any(LocalDate.class), any(LocalDate.class));

        mvc.perform(MockMvcRequestBuilders.get("/weathers/report?endDate=2021-03-04")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(repository, times(1)).findByDateBetween(argumentsStartDate.capture(), argumentsEndDate.capture());
        //Assertions.assertEquals(expectedDate, argumentsStartDate.getValue());
        Assertions.assertEquals(LocalDate.of(2021, 03, 04), argumentsEndDate.getValue());
    }

    @Test
    @DisplayName("GET between dates /weathers/report?startDate=2020-01-01&endDate=2020-01-31")
    public void shouldReturnWeatherInformationWhenCallWithRangeDateValid() throws Exception {

        String firstRow = "{\"id\":37891,\"location\":{\"lat\":32.7767,\"lon\":96.797,\"city\":\"Dallas\",\"state\":\"Texas\"},\"date\":\"2020-09-15\",\"lowest\":84.3,\"highest\":93.1,\"temperature\":[89.7,84.3,91.2,93.1]}]";
        String secondRow = "[{\"id\":37892,\"location\":{\"lat\":32.7767,\"lon\":96.797,\"city\":\"Austin\",\"state\":\"Texas\"},\"date\":\"2020-09-15\",\"lowest\":84.3,\"highest\":93.1,\"temperature\":[89.7,84.3,91.2,93.1]}";
        String expected = secondRow + "," + firstRow;

        List<WeatherInformation> responseRepository = Arrays.asList(DummyWeatherInformation.build(37891, "Dallas"), DummyWeatherInformation.build(37892, "Austin"));
        doReturn(responseRepository).when(repository).findByDateBetween(any(LocalDate.class), any(LocalDate.class));

        mvc.perform(MockMvcRequestBuilders.get("/weathers/report?startDate=2021-01-01&endDate=2021-03-10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for POST /weathers
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Test
    @DisplayName("POST /rest/weathers")
    public void shouldReturnWeatherInformationWhenCallCreate() throws Exception {

        Weather weatherInformation = DummyWeatherInformation.weatherDtoBuild(37892, "Austin");
        doReturn(weatherInformation).when(repository).save(any());

        mvc.perform( MockMvcRequestBuilders
                .post("/weathers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(weatherInformation)));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for PUT /weathers
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Tests for DELETE /weathers
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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
