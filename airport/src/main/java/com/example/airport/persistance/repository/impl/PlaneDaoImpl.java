package com.example.airport.persistance.repository.impl;

import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.entity.query.QueryName;
import com.example.airport.persistance.repository.PlaneDao;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlaneDaoImpl extends AbstractDao implements PlaneDao {

    @Override
    public List<Plane> getPlanesToRemove() {
        List<Plane> list;
        LocalDateTime date = super.getDataTimeMinusDays();
        TypedQuery<Plane> query = em.createNamedQuery(QueryName.GET_PLANE_TO_REMOVE, Plane.class);
        query.setParameter("earlieDays",date);
        list = query.getResultList();
        return list;
    }
}
