package com.example.airport.persistance.repository.impl;

import com.example.airport.domain.entity.Client;
import com.example.airport.domain.entity.query.QueryName;
import com.example.airport.persistance.repository.ClientDao;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientDaoImpl extends AbstractDao implements ClientDao {

    @Override
    public List<Client> getClientsToRemove() {
        List<Client> clients;
        LocalDateTime dateTime = super.getDataTimeMinusDays();
        TypedQuery<Client> query = em.createNamedQuery(QueryName.GET_CLIENT_TO_REMOVE, Client.class);
        query.setParameter("earlieDays",dateTime);
        clients = query.getResultList();
        return clients;
    }
}
