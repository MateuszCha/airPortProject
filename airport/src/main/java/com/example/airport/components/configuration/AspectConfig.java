package com.example.airport.components.configuration;

import com.example.airport.components.aspect.TimeCounterAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public TimeCounterAspect getTimeCounterAspect(){
        return new TimeCounterAspect();
    }
}
