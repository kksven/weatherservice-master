package com.training.weatherservice.aspect;

import com.training.weatherservice.util.imp.RangeDateValidator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class DateRangeAspect {

    private final RangeDateValidator rangeDateValidator;

    public DateRangeAspect(RangeDateValidator rangeDateValidator) {
        this.rangeDateValidator = rangeDateValidator;
    }

    @Around("@annotation(com.training.weatherservice.aspect.annotation.DateRangeValidator) || @within(com.training.weatherservice.aspect.annotation.DateRangeValidator)")
    public Object validate(ProceedingJoinPoint pjp) throws Throwable {

        Map<String, LocalDate> mapArgument = getArgumentList(pjp);

        LocalDate from = mapArgument.get("startDate");
        LocalDate to = mapArgument.get("endDate");

        rangeDateValidator.validate(from, to);

        Object proceed = pjp.proceed(pjp.getArgs());

        return proceed;
    }

    private Map<String, LocalDate> getArgumentList(ProceedingJoinPoint pjp){
        Map<String, LocalDate> mapArgument = new HashMap<>();
        var argNames = ((MethodSignature) pjp.getSignature()).getParameterNames();
        var arguments = pjp.getArgs();


        if (argNames.length != 0) {
            for(int i = 0; i < argNames.length; i++) {
                mapArgument.put(argNames[i], (LocalDate) arguments[i]);
            }
        }
        return mapArgument;
    }
}
