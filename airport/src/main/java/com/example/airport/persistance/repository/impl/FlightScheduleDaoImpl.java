package com.example.airport.persistance.repository.impl;

import com.example.airport.domain.entity.FlightSchedule;
import com.example.airport.domain.entity.query.QueryName;
import com.example.airport.persistance.repository.FlightScheduleDao;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightScheduleDaoImpl extends AbstractDao implements FlightScheduleDao {

    @Override
    public List<FlightSchedule> getSchedulesToRemove() {
        List<FlightSchedule> list;
        LocalDateTime date = super.getDataTimeMinusDays();
        TypedQuery<FlightSchedule> query = em.createNamedQuery(QueryName.GET_SCHEDULE_TO_REMOVE, FlightSchedule.class);
        query.setParameter("earlieDays",date);
        list = query.getResultList();
        return list;
    }
}
