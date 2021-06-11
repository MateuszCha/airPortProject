package com.example.airport.persistance.query;

import com.example.airport.domain.entity.Client;
import com.example.airport.domain.enumeration.DocumentType;
import com.example.airport.domain.to.ClientDto;
import com.example.airport.persistance.repository.ClientRepository;
import com.example.airport.persistance.service.crud.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("hsql")
@TestPropertySource(properties = "custom.var.earlie.days=0")
public class ClientDaoImplTest {

    @Autowired
    private ClientService service;
    @Autowired
    private ClientRepository repository;

    @Test
    public void getClientsToRemoveShouldReturnElements() {
        //given
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        ClientDto clientDto2 = this.createClientDto(1L,"name2","surnam2","222","email2","xR2#",DocumentType.ID_CARD);
        ClientDto returnedDto1  = service.add(clientDto1);
        ClientDto returnedDto2 = service.add(clientDto2);
        //when
        service.setToRemove(2L);
        service.getAll();
        List<Client> seat = repository.getClientsToRemove();
        //then
        assertEquals(1,seat.size());
    }
    @Test
    public void getClientsToRemoveShouldReturnEmptyListWhenNotFoundElement() {
        //given
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        ClientDto clientDto2 = this.createClientDto(1L,"name2","surnam2","222","email2","xR2#",DocumentType.ID_CARD);
        service.add(clientDto1);
        service.add(clientDto1);
        //when
        service.getAll();
        List<Client> seat = repository.getClientsToRemove();
        //then
        assertEquals(0,seat.size());
    }

    @Test
    public void getClientsToRemoveShouldReturnEmptyListWhenDbIsEmpty() {
        //given
        //when
        List<Client> seat = repository.getClientsToRemove();
        //then
        assertEquals(0,seat.size());
        //TestPropertySource
    }

    private ClientDto createClientDto(Long id, String firstName, String surname, String phoneNumber, String email, String idNumber, DocumentType documentType) {
        return ClientDto.builder().withID(id).withFirsName(firstName).withSurname(surname).withPhoneNumber(phoneNumber).withEmail(email).witIdNumber(idNumber).withDocumentType(documentType).build();
    }

}
