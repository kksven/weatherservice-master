package com.training.weatherservice.aspect;


import com.training.weatherservice.aspect.annotation.LogMessageBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Aspect
@Component
public class LoggerAspect {

    @Around("@annotation(com.training.weatherservice.aspect.annotation.Monitor) || @within(com.training.weatherservice.aspect.annotation.Monitor)")
    public Object logRelevantInformation(ProceedingJoinPoint pjp) throws Throwable {

        var className = pjp.getTarget().getClass().getSimpleName();
        var methodName = pjp.getSignature().getName();
        Instant start = Instant.now();
        String startTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now().truncatedTo(ChronoUnit.SECONDS));

        try {
            LogMessageBuilder beforeLog = LogMessageBuilder.builder()
                    .className(className)
                    .method(methodName)
                    .start(startTime)
                    .description(getArgument(pjp))
                    .build();

            LoggerEvent.before(beforeLog);

            Object proceed = pjp.proceed(pjp.getArgs());

            var afterLog = LogMessageBuilder.builder()
                    .className(className)
                    .method(methodName)
                    .durationMillis(duration(start))
                    .description(getDescription(proceed))
                    .build();

            LoggerEvent.after(afterLog);

            return proceed;
        } catch (Throwable thr) {

            LogMessageBuilder exceptionLog = LogMessageBuilder.builder()
                    .className(className)
                    .method(methodName)
                    .durationMillis(duration(start))
                    .exceptionMessage(getCause(thr))
                    .stackTrace(getStacktraceWithoutNewLines(thr))
                    .build();

            LoggerEvent.exception(exceptionLog);

            throw thr;
        }
    }

    private String getArgument(ProceedingJoinPoint pjp){
        var attributes = "";
        var argNames = ((MethodSignature) pjp.getSignature()).getParameterNames();
        var arguments = pjp.getArgs();
        if (argNames.length != 0) {
            attributes = IntStream.range(0, argNames.length)
                    .mapToObj(i -> argNames[i] + ": " + arguments[i] + " ")
                    .collect(Collectors.joining());
        }
        return attributes;
    }

    private String getStacktraceWithoutNewLines(Throwable thr) {
        return ExceptionUtils.getStackTrace(thr).replace("\n", " |").substring(0, 500);
    }

    private String getCause(Throwable thr) {
        return thr.getCause() != null ? thr.getCause().getLocalizedMessage() : Strings.EMPTY;
    }

    private String duration(Instant start ){
        Instant end = Instant.now();
        String diff = Duration.between(start, end).toString();
        return Duration.parse(diff).toMillis() + " ms";
    }

    private String getDescription(Object proceed){
        return proceed != null ? proceed.toString() : Strings.EMPTY;
    }
}
