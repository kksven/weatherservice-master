package com.training.weatherservice.util;

import com.training.weatherservice.exception.InvalidDateRequestException;
import com.training.weatherservice.util.imp.RangeDateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RangeDateValidatorTest {

    @InjectMocks
    RangeDateValidator rangeDateValidator;

    @Test
    public void shouldThrowExceptionWhenInvalidFormatFromDate(){

        Assertions.assertThrows(InvalidDateRequestException.class, () -> {
            rangeDateValidator.validate("2021-24-22", "2020-01-01");
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidFormatToDate(){

        Assertions.assertThrows(InvalidDateRequestException.class, () -> {
            rangeDateValidator.validate("2021-01-01", "2020-01-88");
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidRange(){

        Assertions.assertThrows(InvalidDateRequestException.class, () -> {
            rangeDateValidator.validate("2021-01-01", "2020-12-31");
        });

    }

    @Test
    public void shouldValidWhenRangeDate(){
        rangeDateValidator.validate("2020-12-31", "2020-12-31");
    }
}
