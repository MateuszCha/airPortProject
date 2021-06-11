package com.example.airport.persistance.repository.impl;

import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.entity.query.QueryName;
import com.example.airport.persistance.repository.SeatDao;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SeatDaoImpl extends AbstractDao implements SeatDao {


    @Override
    public List<Seat> getSeatToRemove(){
        List<Seat> list;
        LocalDateTime date = super.getDataTimeMinusDays();
        TypedQuery<Seat> query = em.createNamedQuery(QueryName.GET_SEAT_TO_REMOVE, Seat.class);
        query.setParameter("earlieDays",date);
        list = query.getResultList();
        return list;
    }

}
