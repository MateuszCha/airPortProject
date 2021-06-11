package com.example.airport.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@MappedSuperclass
public abstract class AbstractEntity {

    @Version
    public int version;

    @Column(name = "modification_by")
    private String user;
    // toDo : pass LocalUser to user

    @Column
    private boolean isRemove;

    @Column(name = "create_time",nullable = true)
    private LocalDateTime addTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @PrePersist
    public void createTime(){
        this.addTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.version = 0;
    }

    @PreUpdate
    public void setUpdateTime(){
        this.updateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    protected AbstractEntity() {
        this.isRemove = false;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

}
