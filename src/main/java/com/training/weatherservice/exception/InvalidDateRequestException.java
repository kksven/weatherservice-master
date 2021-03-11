package com.training.weatherservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDateRequestException extends RuntimeException {
    public InvalidDateRequestException(String message) {
        super(message);
    }
}
