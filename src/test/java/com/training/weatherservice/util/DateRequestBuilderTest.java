package com.training.weatherservice.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class DateRequestBuilderTest {
    private final static LocalDate LOCAL_DATE = LocalDate.of(2020, 7, 05);

    @Mock
    private Clock clock;
    private Clock fixedClock;

    @InjectMocks
    DateRequestBuilder dateRequestBuilder;

    @BeforeEach
    void init(){
        fixedClock = Clock.fixed(LOCAL_DATE.atTime(9, 5).toInstant(ZoneOffset.UTC), ZoneId.of("CET"));
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
    }

    @Test
    @DisplayName("Return Date now when date from is null")
    public void shouldReturnDateNowWhenDateFromIsnull(){

        LocalDate expected = LOCAL_DATE.minusDays(30);
        LocalDate result = dateRequestBuilder.startDate(null);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Return LocalDate when string startDate is valid")
    public void shouldReturnDateWhenStringDateFrom(){

        LocalDate expected = LocalDate.of(2020, 01, 01);
        LocalDate result = dateRequestBuilder.startDate("2020-01-01");

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Return LocalDate when string endDate is valid")
    public void shouldReturnDateWhenStringDateTo(){

        LocalDate expected = LocalDate.of(2020, 01, 01);
        LocalDate result = dateRequestBuilder.endDate("2020-01-01");

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Return Date now when endDate to is null")
    public void shouldReturnDateNowWhenDateToIsnull(){

        LocalDate expected = LOCAL_DATE;
        LocalDate result = dateRequestBuilder.endDate(null);

        Assertions.assertEquals(expected, result);
    }
}
