package com.example.airport.domain.to;

public abstract class AbstractTo {

    private int version;

    private boolean isRemove;

    protected AbstractTo() {
    }

    protected AbstractTo(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }
}
