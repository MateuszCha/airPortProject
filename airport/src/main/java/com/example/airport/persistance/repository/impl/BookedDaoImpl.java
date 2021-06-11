package com.example.airport.persistance.repository.impl;

import com.example.airport.domain.entity.Booked;
import com.example.airport.domain.entity.query.QueryName;
import com.example.airport.persistance.repository.BookedDao;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookedDaoImpl extends AbstractDao implements BookedDao {

    @Override
    public List<Booked> getListOfBookedToRemove(){
        List<Booked> list;
        LocalDateTime date = super.getDataTimeMinusDays();
        TypedQuery<Booked> query = em.createNamedQuery(QueryName.GET_BOOKED_TO_REMOVE, Booked.class);
        query.setParameter("earlieDays",date);
        list = query.getResultList();
        return list;
    }
}
