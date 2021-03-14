package com.training.weatherservice.util;

import com.training.weatherservice.exception.InvalidDateRequestException;
import com.training.weatherservice.util.imp.RangeDateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class RangeDateValidatorTest {

    @InjectMocks
    RangeDateValidator rangeDateValidator;

    @Test
    public void shouldThrowExceptionWhenInvalidRange(){
        LocalDate from = LocalDate.of(2021, 01, 01);
        LocalDate to = LocalDate.of(2020, 12, 31);

        Assertions.assertThrows(InvalidDateRequestException.class, () -> {
            rangeDateValidator.validate(from, to);
        });

    }

    @Test
    public void shouldValidWhenRangeDate(){
        LocalDate from = LocalDate.of(2020, 12, 31);
        LocalDate to = LocalDate.of(2020, 12, 31);
        rangeDateValidator.validate(from, to);
    }
}
