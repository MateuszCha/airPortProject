package com.example.airport.persistance.validation;

import com.example.airport.domain.enumeration.DocumentType;
import com.example.airport.domain.to.ClientDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientValidatorTest {
    
    @Autowired
    private ClientValidator validator;

    @Test
    public void addValidationShouldReturnFalseWhenDtoIsNull() {
        //given
        ClientDto dto = null;
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }

    @Test
    public void addValidationShouldReturnTrue() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrueWhenIdIsNull() {
        //given
        ClientDto dto = this.createClientDto(null,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrueWhenIdIsLessThanOne() {
        //given
        ClientDto dto = this.createClientDto(0L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenFirstNameIsNull() {
        //given
        ClientDto dto = this.createClientDto(1L,null,"surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenFirstNameIsEmpty() {
        //given
        ClientDto dto = this.createClientDto(1L,"","surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenSurnameIsNull() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1",null,"111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenSurnameIsEmpty() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","","111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenPhoneNumberIsNull() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1",null,"email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenPhoneNumberIsEmpty() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenDescriptionEmailIsNull() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","111",null,"xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenDescriptionEmailIsEmptyl() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","111","","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenIdNumberIsNull() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","111","email1",null,DocumentType.VISA);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenIdNumberIsEmpty() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","111","email1","",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenDocumentTypeIsNull() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",null);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenDtoIsNull() {
        //given
        ClientDto dto = null;
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnTrue() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertTrue(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenIdIsNull() {
        //given
        ClientDto dto = this.createClientDto(null,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }

    @Test
    public void updateValidationShouldReturnTrueWhenIdIsLessThanOne() {
        //given
        ClientDto dto = this.createClientDto(0L,"name1","surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenFirstNameIsNull() {
        //given
        ClientDto dto = this.createClientDto(1L,null,"surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenFirstNameIsEmpty() {
        //given
        ClientDto dto = this.createClientDto(1L,"","surnam1","111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenSurnameIsNull() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1",null,"111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenSurnameIsEmpty() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","","111","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenPhoneNumberIsNull() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1",null,"email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenPhoneNumberIsEmpty() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","","email1","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenDescriptionEmailIsNull() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","111",null,"xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenDescriptionEmailIsEmptyl() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","111","","xR1#",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenIdNumberIsNull() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","111","email1",null,DocumentType.VISA);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenIdNumberIsEmpty() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","111","email1","",DocumentType.VISA);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenDocumentTypeIsNull() {
        //given
        ClientDto dto = this.createClientDto(1L,"name1","surnam1","111","email1","xR1#",null);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    private ClientDto createClientDto(Long id, String firstName, String surname, String phoneNumber, String email, String idNumber, DocumentType documentType) {
        return ClientDto.builder().withID(id).withFirsName(firstName).withSurname(surname).withPhoneNumber(phoneNumber).withEmail(email).witIdNumber(idNumber).withDocumentType(documentType).build();
    }
}
