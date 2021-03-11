package com.training.weatherservice.aspect;

import lombok.extern.slf4j.Slf4j;

import static com.training.weatherservice.aspect.LoggerEnum.*;

@Slf4j
public class LoggerEvent {

    public static void before(LogMessageBuilder logInformation){

        log.info(LoggerEnum.getValue(LOGGER_BEFORE),
                logInformation.getClassName(),
                logInformation.getMethod(),
                LoggerEnum.PENDING,
                logInformation.getDescription());
    }

    public static void after(LogMessageBuilder logInformation){

        log.info(LoggerEnum.getValue(LOGGER_AFTER),
                logInformation.getClassName(),
                logInformation.getMethod(),
                LoggerEnum.SUCCESS,
                logInformation.getDurationMillis(),
                logInformation.getDescription());
    }

    public static void exception(LogMessageBuilder logInformation){

        log.error(LoggerEnum.getValue(LOGGER_EXCEPTION),
                logInformation.getClassName(),
                logInformation.getMethod(),
                LoggerEnum.ERROR,
                logInformation.getDurationMillis(),
                logInformation.getExceptionMessage(),
                logInformation.getStackTrace());
    }
}
