package com.example.airport.domain.to;

import java.util.Objects;

public class PlaneDto extends AbstractTo{
    private Long id;
    private String serialNumber;
    private String nameCarrier;

    public PlaneDto() {
    }

    public PlaneDto(Long id, String serialNumber, String nameCarrier,int version) {
        super.setVersion(version);
        this.id = id;
        this.serialNumber = serialNumber;
        this.nameCarrier = nameCarrier;
    }

    public static PlaneDtoBuilder builder(){
        return new PlaneDtoBuilder();
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

    public String getNameCarrier() {
        return nameCarrier;
    }

    public void setNameCarrier(String nameCarrier) {
        this.nameCarrier = nameCarrier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaneDto planeDto = (PlaneDto) o;
        return Objects.equals(id, planeDto.id) &&
                Objects.equals(serialNumber, planeDto.serialNumber) &&
                Objects.equals(nameCarrier, planeDto.nameCarrier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, nameCarrier);
    }
    public static class PlaneDtoBuilder{
        private Long id;
        private String serialNumber;
        private String nameCarrier;
        private int version;

        public PlaneDtoBuilder() {

            //default const...
        }

        public  PlaneDtoBuilder withId(Long id){
            this.id = id;
            return this;
        }
        public PlaneDtoBuilder withSerialNumber(String serialNumber ){
            this.serialNumber = serialNumber;
            return this;
        }
        public PlaneDtoBuilder withNameCarrier(String nameCarrier ){
            this.nameCarrier = nameCarrier;
            return this;
        }
        public PlaneDtoBuilder withVersion(int version){
            this.version = version;
            return this;
        }
        public PlaneDto build(){
            return new PlaneDto(id,serialNumber,nameCarrier,version);
        }

    }
}
