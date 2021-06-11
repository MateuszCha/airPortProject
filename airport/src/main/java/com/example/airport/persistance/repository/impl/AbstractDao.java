package com.example.airport.persistance.repository.impl;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class AbstractDao {
   @PersistenceContext
    protected EntityManager em;

    @Value("${custom.var.earlie.days}")
    private Integer earlieDay;

    protected static final String NO_FOUND_ENTITY = "no found entities : ";

    private void checkDaysIsSet(){
        if(Objects.isNull(this.earlieDay)){
            throw new IllegalArgumentException("not set days to remove : " + this.earlieDay);
        }
        if(this.earlieDay < 0 ){
            this.earlieDay *= -1;
        }
    }
    public LocalDateTime getDataTimeMinusDays(){
        LocalDateTime date = LocalDateTime.now().minusDays(this.getEarlieDay());
        date = date.truncatedTo(ChronoUnit.DAYS);
        date = date.plusDays(1L);
        return date;
    }

    public Integer getEarlieDay() {
        checkDaysIsSet();
        return earlieDay;
    }
}
