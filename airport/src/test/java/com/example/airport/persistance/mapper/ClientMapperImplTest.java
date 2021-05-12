package com.example.airport.persistance.mapper;

import com.example.airport.domain.entity.Client;
import com.example.airport.domain.enumeration.DocumentType;
import com.example.airport.persistance.to.ClientDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClientMapperImplTest {

    @Autowired
    ClientMapper mapper;

    @Test
    public void map2EntityShouldReturnEntity() {
        //given
        ClientDto expect = this.createClientDto(1L,"name","surnam","66","email","xR#",DocumentType.VISA);
        //when
        Client result = mapper.map2Entity(expect);
        //then
        checkAllParametersInDtoAndEntityAreNotNull(result,expect);
        compareEntityWithDtoTest(result, expect);
    }
    @Test
    public void map2EntityShouldReturnNullWhenDtoIsNull() {
        //given
        ClientDto expect = null;
        //when
        Client result = mapper.map2Entity(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EntityShouldReturnEntityWhenAllDtoParametersAreNull() {
        //given
        ClientDto expect = this.createClientDto(null,null,null,null,null,null,null);
        //when
        Client result = mapper.map2Entity(expect);
        //then
        checkAllParametersInDtoAndEntityAreNull(result,expect);
    }


    @Test
    public void map2DtoShouldReturnDto() {
        //given
        Client expect = this.createClient(1L,"name","surnam","66","email","xR#",DocumentType.VISA);
        //when
         ClientDto result = mapper.map2To(expect);
        //then
        checkAllParametersInDtoAndEntityAreNotNull(expect,result);
        compareEntityWithDtoTest(expect, result);
    }
    @Test
    public void map2DtoShouldReturnNullWhenEntityIsNull() {
        //given
        Client expect  = null;
        //when
        ClientDto result = mapper.map2To(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2DtoShouldReturnEntityWhenAllEntityParametersAreNull() {
        //given
        Client expect = this.createClient(null,null,null,null,null,null,null);
        //when
        ClientDto result = mapper.map2To(expect);
        //then
        checkAllParametersInDtoAndEntityAreNull(expect,result);
    }

    @Test
    public void map2EntitiesShouldReturnListOfEntity() {
        //given
        List<ClientDto> dtos = new ArrayList<ClientDto>();
        dtos.add(this.createClientDto(1L,"name","surnam","66","email","xR#",DocumentType.VISA));
        dtos.add(this.createClientDto(2L,"name2","surnam2","662","emai2l","x2#",DocumentType.PASSPORT));
        //when
        List<Client> result = mapper.map2Entities(dtos);
        //then
        assertNotNull(dtos);
        assertNotNull(result);
        assertEquals(dtos.size(),result.size());
        checkAllParametersInDtoAndEntityAreNotNull(result.get(0),dtos.get(0));
        checkAllParametersInDtoAndEntityAreNotNull(result.get(1),dtos.get(1));
        compareEntityWithDtoTest(result.get(0), dtos.get(0));
        compareEntityWithDtoTest(result.get(1), dtos.get(1));
    }
    @Test
    public void map2EntitiesShouldReturnNullWhenDtosIsNull() {
        //given
        List<ClientDto> expect = null;
        //when
        List<Client> result = mapper.map2Entities(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EntitiesShouldReturnNullWhenDtosIsEmpty() {
        //given
        List<ClientDto> expect = new ArrayList<>();
        //when
        List<Client> result = mapper.map2Entities(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EntitiesShouldReturnDtosWhenDtosIncludeElementWithAllNullParameters() {
        //given
        List<ClientDto> dtos = new ArrayList<ClientDto>();
        dtos.add(this.createClientDto(null,null,null,null,null,null,null));
        dtos.add(this.createClientDto(null,null,null,null,null,null,null));
        dtos.add(this.createClientDto(1L,"name","surnam","66","email","xR#",DocumentType.VISA));
       //when
        List<Client> result = mapper.map2Entities(dtos);
        //then
        assertNotNull(dtos);
        assertNotNull(result);
        assertEquals(dtos.size(),result.size());
        checkAllParametersInDtoAndEntityAreNull(result.get(0),dtos.get(0));
        checkAllParametersInDtoAndEntityAreNull(result.get(1),dtos.get(1));
        checkAllParametersInDtoAndEntityAreNotNull(result.get(2),dtos.get(2));
        compareEntityWithDtoTest(result.get(2), dtos.get(2));
    }
    @Test
    public void map2EntitiesShouldReturnDtoWhenDtoesIncludeNullElement() {
        //given
        List<ClientDto> dtos = new ArrayList<ClientDto>();
        dtos.add(null);
        dtos.add(null);
        //when
        List<Client> result = mapper.map2Entities(dtos);
        //then
        assertNotNull(dtos);
        assertNotNull(result);
        assertEquals(dtos.size(),result.size());
        assertNull(dtos.get(0));
        assertNull(dtos.get(1));
    }

    @Test
    public void map2ToesShouldReturnListOfDto() {
        //given
        List<Client> entities = new ArrayList<Client>();
        entities.add(this.createClient(1L,"name","surnam","66","email","xR#",DocumentType.VISA));
        entities.add(this.createClient(2L,"name2","surnam2","662","emai2l","x2#",DocumentType.PASSPORT));
        //when
        List<ClientDto> result = mapper.map2Toes(entities);
        //then
        assertNotNull(entities);
        assertNotNull(result);
        assertEquals(entities.size(),result.size());
        checkAllParametersInDtoAndEntityAreNotNull(entities.get(0),result.get(0));
        checkAllParametersInDtoAndEntityAreNotNull(entities.get(1),result.get(1));
        compareEntityWithDtoTest(entities.get(0), result.get(0));
        compareEntityWithDtoTest(entities.get(1), result.get(1));
    }
    @Test
    public void map2EToesShouldReturnNullWhenEntitiesIsNull() {
        //given
        List<Client> expect = null;
        //when
        List<ClientDto> result = mapper.map2Toes(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EToesShouldReturnNullWhenEntitiesIsEmpty() {
        //given
        List<Client> expect = new ArrayList<>();
        //when
        List<ClientDto> result = mapper.map2Toes(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2ToesShouldReturnDtoWhenEntitiesIncludeElementWithAllNullParameters() {
        //given
        List<Client> entities = new ArrayList<Client>();
        entities.add(this.createClient(null,null,null,null,null,null,null));
        entities.add(this.createClient(null,null,null,null,null,null,null));
        entities.add(this.createClient(1L,"name","surnam","66","email","xR#",DocumentType.VISA));
        //when
        List<ClientDto> result = mapper.map2Toes(entities);
        //then
        assertNotNull(entities);
        assertNotNull(result);
        assertEquals(entities.size(),result.size());
        checkAllParametersInDtoAndEntityAreNull(entities.get(0),result.get(0));
        checkAllParametersInDtoAndEntityAreNull(entities.get(1),result.get(1));
        checkAllParametersInDtoAndEntityAreNotNull(entities.get(2),result.get(2));
        compareEntityWithDtoTest(entities.get(2), result.get(2));
    }
    @Test
    public void map2ToesShouldReturnDToesWhenEntitiesIncludeNullElement() {
        //given
        List<Client> entities = new ArrayList<Client>();
        entities.add(null);
        entities.add(null);
        //when
        List<ClientDto> result = mapper.map2Toes(entities);
        //then
        assertNotNull(entities);
        assertNotNull(result);
        assertEquals(entities.size(),result.size());
        assertNull(entities.get(0));
        assertNull(entities.get(1));
    }


    private void checkAllParametersInDtoAndEntityAreNotNull(Client entity,ClientDto dto){
        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertNotNull(entity.getFirstName());
        assertNotNull(entity.getSurname());
        assertNotNull(entity.getPhoneNumber());
        assertNotNull(entity.getEmail());
        assertNotNull(entity.getIdNumber());
        assertNotNull(entity.getDocumentType());
        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertNotNull(dto.getFirstName());
        assertNotNull(dto.getSurname());
        assertNotNull(dto.getPhoneNumber());
        assertNotNull(dto.getEmail());
        assertNotNull(dto.getIdNumber());
        assertNotNull(dto.getDocumentType());
    }
    private void checkAllParametersInDtoAndEntityAreNull(Client entity,ClientDto dto){
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getFirstName());
        assertNull(entity.getSurname());
        assertNull(entity.getPhoneNumber());
        assertNull(entity.getEmail());
        assertNull(entity.getIdNumber());
        assertNull(entity.getDocumentType());
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getFirstName());
        assertNull(dto.getSurname());
        assertNull(dto.getPhoneNumber());
        assertNull(dto.getEmail());
        assertNull(dto.getIdNumber());
        assertNull(dto.getDocumentType());
    }
    private void compareEntityWithDtoTest(Client client, ClientDto clientDto){
        assertEquals(client.getId(), clientDto.getId());
        assertEquals(client.getFirstName(), clientDto.getFirstName());
        assertEquals(client.getSurname(), clientDto.getSurname());
        assertEquals(client.getPhoneNumber(), clientDto.getPhoneNumber());
        assertEquals(client.getEmail(), clientDto.getEmail());
        assertEquals(client.getIdNumber(), clientDto.getIdNumber());
        assertEquals(client.getDocumentType(), clientDto.getDocumentType());
    }

    private ClientDto createClientDto(Long id, String firstName, String surname, String phoneNumber, String email, String idNumber, DocumentType documentType) {
       return ClientDto.builder().withID(id).withFirsName(firstName).withSurname(surname).withPhoneNumber(phoneNumber).withEmail(email).witIdNumber(idNumber).withDocumentType(documentType).build();
    }
    private Client createClient(Long id, String firstName, String surname, String phoneNumber, String email, String idNumber, DocumentType documentType) {
        return new Client(id,firstName,surname,phoneNumber,email,idNumber,documentType,null);
    }

}
