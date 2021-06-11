package com.example.airport.domain.to;

import com.example.airport.domain.enumeration.DocumentType;
import java.util.Objects;

public class ClientDto extends AbstractTo{
    private Long id;
    private String firstName;
    private String surname;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private DocumentType documentType;

    public ClientDto() {
    }

    public ClientDto(Long id, String firstName, String surname, String phoneNumber, String email, String idNumber, DocumentType documentType,int version) {
        super(version);
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.documentType = documentType;
    }

    public static ClientDtoBuilder builder(){
        return new ClientDtoBuilder();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDto clientDto = (ClientDto) o;
        return Objects.equals(id, clientDto.id) &&
                Objects.equals(firstName, clientDto.firstName) &&
                Objects.equals(surname, clientDto.surname) &&
                Objects.equals(phoneNumber, clientDto.phoneNumber) &&
                Objects.equals(email, clientDto.email) &&
                Objects.equals(idNumber, clientDto.idNumber) &&
                documentType == clientDto.documentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, surname, phoneNumber, email, idNumber, documentType);
    }

    public static class ClientDtoBuilder{
        private Long id;
        private String firstName;
        private String surname;
        private String phoneNumber;
        private String email;
        private String idNumber;
        private DocumentType documentType;
        private int version;

        public ClientDtoBuilder() {
            //default const...
        }

        public ClientDtoBuilder withID(Long id){
            this.id = id;
            return this;
        }
        public ClientDtoBuilder withFirsName(String firstName){
            this.firstName = firstName;
            return this;
        }
        public ClientDtoBuilder withSurname(String surname){
            this.surname = surname;
            return this;
        }
        public ClientDtoBuilder withPhoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }
        public ClientDtoBuilder withEmail(String email){
            this.email = email;
            return this;
        }
        public ClientDtoBuilder witIdNumber(String idNumber){
            this.idNumber = idNumber;
            return this;
        }
        public ClientDtoBuilder withDocumentType(DocumentType documentType){
            this.documentType = documentType;
            return this;
        }

        public ClientDtoBuilder withVersion(int version){
            this.version = version;
            return this;
        }
        public ClientDto build(){
            return new ClientDto(id,firstName,surname,phoneNumber,email,idNumber,documentType,version);
        }
    }

}
