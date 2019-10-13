package com.project.fligthtracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "planes")
@Getter
@Setter
@NoArgsConstructor
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
}
