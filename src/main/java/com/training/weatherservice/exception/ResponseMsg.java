package com.training.weatherservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ResponseMsg {
    private Date timestamp;
    private String message;
    private String detail;
}
