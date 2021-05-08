package com.example.airport.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "plane")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;

    private String nameDeliver;

    public Plane() {
    }

    public Plane(Long id, String serialNumber, String nameOfDeliver) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.nameDeliver = nameOfDeliver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getNameDeliver() {
        return nameDeliver;
    }

    public void setNameDeliver(String nameDeliver) {
        this.nameDeliver = nameDeliver;
    }
}
