package com.example.airport.domain.to.account;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class UserTo {

    private Integer id;
    private String name;
    @JsonIgnore
    private String password;

    public UserTo() {
    }

    public UserTo(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTo userTo = (UserTo) o;
        return Objects.equals(id, userTo.id) &&
                Objects.equals(name, userTo.name) &&
                Objects.equals(password, userTo.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }
}
