package com.training.weatherservice.aspect;

import java.util.HashMap;
import java.util.Map;

public enum LoggerEnum {
	STATUS("status"),
	PENDING("pending"),
	SUCCESS("success"),
	ERROR("error"),
	EXCEPTION_STACKTRACE("stackTrace"),
	EXCEPTION_CAUSE("errorCause"),
	LOGGER_BEFORE("Class: {}, Method: {}, Status: {}, Argument: {}"),
	LOGGER_AFTER("Class: {}, Method: {}, Status: {}, Duration: {}, Argument: {}"),
	LOGGER_EXCEPTION("Class: {}, Method: {}, Status: {}, Duration: {}, errorCause: {}, stackTrace: {}");
	
	private static final Map<LoggerEnum, String> VALUES = new HashMap<>();
	
	static {
		for (LoggerEnum e: values()) {
			VALUES.put(e, e.value);
	    }
	}

	public final String value;

	LoggerEnum(String value) {
        this.value = value;
    }

    public static String getValue(LoggerEnum label) {
        return VALUES.get(label);
    }
}
