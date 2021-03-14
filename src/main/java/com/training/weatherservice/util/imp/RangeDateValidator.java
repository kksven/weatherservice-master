package com.training.weatherservice.util.imp;

import com.training.weatherservice.exception.InvalidDateRequestException;
import com.training.weatherservice.util.RequestDateValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class RangeDateValidator implements RequestDateValidator {

    @Override
    public void validate(LocalDate from, LocalDate to) {

        if (Period.between(from, to).isNegative()) {
            throw new InvalidDateRequestException("Invalid date range ");
        }
    }
}
