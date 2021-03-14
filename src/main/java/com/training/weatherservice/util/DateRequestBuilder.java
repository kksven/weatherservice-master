package com.training.weatherservice.util;

import org.assertj.core.util.Strings;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

@Component
public class DateRequestBuilder {

    private final Clock clock;

    public DateRequestBuilder(Clock clock) {
        this.clock = clock;
    }

    public LocalDate startDate(String from){
        LocalDate date = LocalDate.now(clock).minusDays(30);

        if (Strings.isNullOrEmpty(from)){
            return date;
        }
        return LocalDate.parse(from);
    }

    public LocalDate endDate(String to){
        LocalDate date = LocalDate.now(clock);

        if (Strings.isNullOrEmpty(to)){
            return date;
        }
        return LocalDate.parse(to);
    }


}
