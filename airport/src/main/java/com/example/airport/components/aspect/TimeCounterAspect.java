package com.example.airport.components.aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Aspect
public class TimeCounterAspect {

    private Logger log = LoggerFactory.getLogger(TimeCounterAspect.class);
    private LocalTime startTime;
    @Pointcut("within( com.example.airport.persistance.service.crud.impl.*)")
    public void performance(){
        //it is Pointcut
    }
    @Before(value = "performance()")
    public void testB(){
        this.startTime = LocalTime.now();
    }
    @After(value = "performance()")
    public void testA(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(". Total execute time: ").append(ChronoUnit.MILLIS.between(this.startTime,LocalTime.now()));
        String msg = stringBuilder.toString();
        log.info(msg);
    }


}
