package com.project.fligthtracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PlaneInfoToTableDto {

        private int id;

        private String companyName;

        private String departurePlace;

        private String landingPlace;

        //even tho dep and landing time wont be in milis i need to name them this way because the html listenes to fieldnames
        //and i can use only one table
        private String departureTimeInMillis;

        private String landingTimeInMillis;

        private int delay;

    public PlaneInfoToTableDto(int id, String companyName, String departurePlace, String landingPlace, String departureTimeInMillis, String landingTimeInMillis, int delay) {
        this.id = id;
        this.companyName = companyName;
        this.departurePlace = departurePlace;
        this.landingPlace = landingPlace;
        this.departureTimeInMillis = departureTimeInMillis;
        this.landingTimeInMillis = landingTimeInMillis;
        this.delay = delay;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public String getLandingPlace() {
        return landingPlace;
    }

    public void setLandingPlace(String landingPlace) {
        this.landingPlace = landingPlace;
    }

    public String getDepartureTimeInMillis() {
        return departureTimeInMillis;
    }

    public void setDepartureTimeInMillis(String departureTimeInMillis) {
        this.departureTimeInMillis = departureTimeInMillis;
    }

    public String getLandingTimeInMillis() {
        return landingTimeInMillis;
    }

    public void setLandingTimeInMillis(String landingTimeInMillis) {
        this.landingTimeInMillis = landingTimeInMillis;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
