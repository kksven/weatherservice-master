package com.training.weatherservice.aspect;

import com.training.weatherservice.util.imp.RangeDateValidator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DateRangeAspect {

    private RangeDateValidator rangeDateValidator;

    public DateRangeAspect(RangeDateValidator rangeDateValidator) {
        this.rangeDateValidator = rangeDateValidator;
    }

    @Before("@annotation(com.training.weatherservice.aspect.annotation.DateRangeValidator) || @within(com.training.weatherservice.aspect.annotation.DateRangeValidator)")
    public Object validate(JoinPoint pjp) {

        Object[] args = pjp.getArgs();
        String dateFrom = getArgument(args,0);
        String dateTo = getArgument(args,1);

        rangeDateValidator.validate(dateFrom, dateTo);

        return null;
    }

    private String getArgument(Object[] args, int pos) {
        String value = "";
        if (args.length > pos) {
            value = (String) args[pos];
        }
        return value;
    }

}
