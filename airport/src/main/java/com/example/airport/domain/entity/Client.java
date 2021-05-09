package com.example.airport.domain.entity;

import com.example.airport.domain.enumeration.DocumentType;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name ="surname",nullable = false )
    private String surname;

    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;

    @Column(name = "email" , nullable = true)
    private String email;

    @Column(name = "document_id", nullable = false)
    private String idNumber;

    @Column(name = "document_type",nullable = false, columnDefinition = "enum('PASSPORT','VISA','ID_CARD')")
    @Enumerated(value = EnumType.STRING)
    private DocumentType documentType;

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private Set<Booked> bookedSet;

    public Client() {

    }

    public Client(Long id, String firstName, String surname, String phoneNumber, String email, String idNumber, DocumentType documentType, Set<Booked> bookedSet) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.documentType = documentType;
        this.bookedSet = bookedSet;
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

    public Set<Booked> getBookedSet() {
        return bookedSet;
    }

    public void setBookedSet(Set<Booked> bookedSet) {
        this.bookedSet = bookedSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) &&
                Objects.equals(firstName, client.firstName) &&
                Objects.equals(surname, client.surname) &&
                Objects.equals(phoneNumber, client.phoneNumber) &&
                Objects.equals(email, client.email) &&
                Objects.equals(idNumber, client.idNumber) &&
                documentType == client.documentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, surname, phoneNumber, email, idNumber, documentType);
    }
}
