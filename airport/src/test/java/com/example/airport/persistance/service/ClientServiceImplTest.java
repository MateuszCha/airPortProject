package com.example.airport.persistance.service;

import com.example.airport.domain.enumeration.DocumentType;
import com.example.airport.domain.to.ClientDto;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.service.crud.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("hsql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientServiceImplTest {

    @Autowired
    private ClientService service;

    @Test
    public void addShouldAddElement() {
    //given
    ClientDto expect = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
    //   ClientDto clientDto2 = this.createClientDto(1L,"name2","surnam2","222","email2","xR2#",DocumentType.ID_CARD);
    //when
    ClientDto result = service.add(expect);
    //then
    assertNotNull(result);
    this.compareExpectAndResultDtoTest(expect,result);
}
    @Test
    public void addShouldAddElementWhenElementOnIndexExistOnDb() {
        //given
        ClientDto dto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        ClientDto expect = this.createClientDto(1L,"name2","surnam2","222","email2","xR2#",DocumentType.ID_CARD);
        //when
        List<ClientDto> beforeAdd = service.getAll();
        service.add(dto1);
        ClientDto result = service.add(expect);
        List<ClientDto> afterAdd = service.getAll();
        //then
        assertEquals(0,beforeAdd.size());
        assertEquals(2,afterAdd.size());
        assertNotNull(result);
    }
    @Test(expected = IllegalArgumentException.class)
    public void addShouldThrowExceptionWhenElementIsNull() {
        //given
        ClientDto dto1 = null;
        //when
        service.add(dto1);
        //then
    }

    @Test
    public void getShouldReturnElementOnIndex() {
        //given
        Long index = 2L;
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        ClientDto expect = this.createClientDto(1L,"name2","surnam2","222","email2","xR2#",DocumentType.ID_CARD);
        //when
        service.add(clientDto1);
        service.add(expect);
        ClientDto result = service.get(index);
        //then
        this.checkAllParametersInDtoAndEntityAreNotNull(result);
        assertEquals(index, result.getId());
        this.compareExpectAndResultDtoTest(expect,result);
    }

    @Test(expected = NoFoundEntity.class)
    public void getShouldThrowExceptionWhenNotFindElement() {
        //given
        Long index = 14L;
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        service.add(clientDto1);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        Long index = 0L;
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        service.add(clientDto1);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        Long index = Long.MIN_VALUE;
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        service.add(clientDto1);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsNull() {
        //given
        Long index = null;
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        service.add(clientDto1);
        service.get(index);
        //then
    }
    @Test(expected = NoFoundEntity.class)
    public void getShouldThrowExceptionWhenDBisEmpty() {
        //given
        Long index = 1L;
        //when
        service.get(index);
        //then
    }

    @Test
    public void getAllShouldReturnAllElement() {
        //given
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        ClientDto clientDto2 = this.createClientDto(1L,"name2","surnam2","222","email2","xR2#",DocumentType.ID_CARD);
        //when
        service.add(clientDto1);
        service.add(clientDto2);
        List<ClientDto> resultList = service.getAll();
        //then
        assertNotNull(resultList);
        assertEquals(2,resultList.size());
        assertNotNull(resultList.get(0));
        assertNotNull(resultList.get(1));
        this.compareExpectAndResultDtoTest(clientDto1,resultList.get(0));
        this.compareExpectAndResultDtoTest(clientDto2,resultList.get(1));
    }
    @Test
    public void getAllShouldReturnZeroWhenDBisEmpty() {
        //given
        // when
        List<ClientDto> resultList = service.getAll();
        //then
        assertNotNull(resultList);
        assertEquals(0,resultList.size());
    }

    @Test
    public void removeShouldRemoveElement() {
        //given
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        ClientDto clientDto2 = this.createClientDto(1L,"name2","surnam2","222","email2","xR2#",DocumentType.ID_CARD);
        Long removeIndex = 2L;
        //when
        service.add(clientDto1);
        service.add(clientDto2);
        List<ClientDto> beforeRemove = service.getAll();
        ClientDto result = service.remove(removeIndex);
        List<ClientDto> afterRemove = service.getAll();
        //then
        assertNotNull(result);
        assertEquals(2,beforeRemove.size());
        assertEquals(1,afterRemove.size());
        this.compareExpectAndResultDtoTest(beforeRemove.get(0),afterRemove.get(0));
        this.compareExpectAndResultDtoTest(beforeRemove.get(1),result);
    }

    @Test (expected = NoFoundEntity.class)
    public void removeShouldThrowExceptionWhenIndexNotExist() {
        //given
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        Long removeIndex = 4L;
        //when
        service.add(clientDto1);
        ClientDto result = service.remove(removeIndex);
        //then
    }
    @Test (expected = NoFoundEntity.class)
    public void removeShouldThrowExceptionWhenDBisEmpty() {
        //given
        Long removeIndex = 4L;
        //when
        ClientDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        Long removeIndex = 0L;
        //when
        ClientDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        Long removeIndex = Long.MIN_VALUE;
        //when
        ClientDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsNull() {
        //given
        Long removeIndex = null;
        //when
        ClientDto result = service.remove(removeIndex);
        //then
    }
    @Test
    public void updateShouldUpdateElement() {
        //given
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        ClientDto clientDto2 = this.createClientDto(1L,"name2","surnam2","222","email2","xR2#",DocumentType.ID_CARD);
        //when
        service.add(clientDto1);
        ClientDto expect = service.add(clientDto2);
        expect.setEmail("coJaWidze@xxxxx.pl");
        expect.setFirstName("Opos");
        expect.setSurname("dupa");
        List<ClientDto> beforeUpdate = service.getAll();
        ClientDto result = service.update(expect);
        List<ClientDto> afterUpdate = service.getAll();
        //then
        assertNotNull(result);
        assertEquals(beforeUpdate.size(), afterUpdate.size());
        this.compareExpectAndResultDtoTest(beforeUpdate.get(0),afterUpdate.get(0));
        this.compareExpectAndResultDtoTest(afterUpdate.get(1),result);
    }
    @Test(expected = NoFoundEntity.class)
    public void updateShouldThrowExceptionWhenIdNoExist() {
        //given
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        ClientDto clientDto2 = this.createClientDto(1L,"name2","surnam2","222","email2","xR2#",DocumentType.ID_CARD);
        //when
        service.add(clientDto1);
        ClientDto expect = service.add(clientDto2);
        expect.setId(5L);
        expect.setEmail("coJaWidze@xxxxx.pl");
        expect.setFirstName("Opos");
        expect.setSurname("dupa");
        service.update(expect);
        //then
    }
    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowExceptionWhenElementIsNull() {
        //given
        ClientDto clientDto1 = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        ClientDto clientDto2 = this.createClientDto(1L,"name2","surnam2","222","email2","xR2#",DocumentType.ID_CARD);
        //when
        service.add(clientDto1);
        service.add(clientDto2);
        service.update(null);
        //then
    }

    private void checkAllParametersInDtoAndEntityAreNotNull(ClientDto dto){

        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertNotNull(dto.getFirstName());
        assertNotNull(dto.getSurname());
        assertNotNull(dto.getPhoneNumber());
        assertNotNull(dto.getEmail());
        assertNotNull(dto.getIdNumber());
        assertNotNull(dto.getDocumentType());
    }
    private void checkAllParametersInDtoAndEntityAreNull(ClientDto dto){
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getFirstName());
        assertNull(dto.getSurname());
        assertNull(dto.getPhoneNumber());
        assertNull(dto.getEmail());
        assertNull(dto.getIdNumber());
        assertNull(dto.getDocumentType());
    }
    private void compareExpectAndResultDtoTest(ClientDto expect, ClientDto result){
        assertEquals(expect.getFirstName(), result.getFirstName());
        assertEquals(expect.getSurname(), result.getSurname());
        assertEquals(expect.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(expect.getEmail(), result.getEmail());
        assertEquals(expect.getIdNumber(), result.getIdNumber());
        assertEquals(expect.getDocumentType(), result.getDocumentType());
    }
    private ClientDto createClientDto(Long id, String firstName, String surname, String phoneNumber, String email, String idNumber, DocumentType documentType) {
        return ClientDto.builder().withID(id).withFirsName(firstName).withSurname(surname).withPhoneNumber(phoneNumber).withEmail(email).witIdNumber(idNumber).withDocumentType(documentType).build();
    }
}
