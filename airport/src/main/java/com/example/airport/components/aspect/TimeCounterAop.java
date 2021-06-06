package com.example.airport.components.aspect;

import org.apache.juli.logging.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Aspect
@Component
public class TimeCounterAop {

    private Logger log = LoggerFactory.getLogger(TimeCounterAop.class);

    @Pointcut("within( com.example.airport.persistance.service.crud.impl.*)")
    public void performance(){}



    @Around(value = "performance()")
    public void countTime(ProceedingJoinPoint pjp) {
        StringBuilder stringBuilder = new StringBuilder();
        LocalTime timeStart = LocalTime.now();
        try {
            Object obj = pjp.proceed();
            if(Objects.nonNull(obj)){
                stringBuilder.append(obj.getClass().getSimpleName());
                stringBuilder.append(". Total execute time: ").append(ChronoUnit.MILLIS.between(timeStart,LocalTime.now()));
                log.info(stringBuilder.toString());
            }
        }catch (Throwable err){
            log.error(err.getMessage());
        }

    }

}
