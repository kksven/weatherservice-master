package com.training.weatherservice.aspect;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LogMessageBuilder {
    private String className;
    private String method;
    private String start;
    private String durationMillis;
    private String exceptionMessage;
    private String stackTrace;
    private String description;

}
