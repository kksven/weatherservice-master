package com.training.weatherservice.util;

import java.time.LocalDate;

public interface RequestDateValidator {

    void validate(LocalDate from, LocalDate to);
}
