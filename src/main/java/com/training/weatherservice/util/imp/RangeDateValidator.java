package com.training.weatherservice.util.imp;

import com.training.weatherservice.exception.InvalidDateRequestException;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;

@Component
public class RangeDateValidator implements com.training.weatherservice.util.RequestDateValidator {

    @Override
    public void validate(String dateFrom, String dateTo) {
        try{
            if (Period.between(LocalDate.parse(dateFrom), LocalDate.parse(dateTo)).isNegative()) {
                throw new InvalidDateRequestException("Invalid date request ");
            }

        }  catch (DateTimeException de) {
            throw new InvalidDateRequestException("Invalid date request");
    }
    }
}
