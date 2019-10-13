package com.project.fligthtracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "planes")
public class Planes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String companyName;

    private String departurePlace;

    private String landingPlace;

    private long departureTimeInMillis;

    private long landingTimeInMillis;

    private int delay;

    public Planes(String companyName, String departurePlace, String landingPlace, long departureTimeInMillis, long landingTimeInMillis, int delay) {
        this.companyName = companyName;
        this.departurePlace = departurePlace;
        this.landingPlace = landingPlace;
        this.departureTimeInMillis = departureTimeInMillis;
        this.landingTimeInMillis = landingTimeInMillis;
        this.delay = delay;
    }

    public Planes() {
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

    public long getDepartureTimeInMillis() {
        return departureTimeInMillis;
    }

    public void setDepartureTimeInMillis(long departureTimeInMillis) {
        this.departureTimeInMillis = departureTimeInMillis;
    }

    public long getLandingTimeInMillis() {
        return landingTimeInMillis;
    }

    public void setLandingTimeInMillis(long landingTimeInMillis) {
        this.landingTimeInMillis = landingTimeInMillis;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
