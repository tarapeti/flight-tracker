package com.project.fligthtracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "planes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Planes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String companyName;

    private String departurePlace;

    private String landingPlace;

    private int departureTimeInMillis;

    private int landingTimeInMillis;

    private int lateByMinsInMillis;

    public Planes(String companyName, String departurePlace, String landingPlace, int departureTimeInMillis, int landingTimeInMillis, int lateByMinsInMillis) {
        this.companyName = companyName;
        this.departurePlace = departurePlace;
        this.landingPlace = landingPlace;
        this.departureTimeInMillis = departureTimeInMillis;
        this.landingTimeInMillis = landingTimeInMillis;
        this.lateByMinsInMillis = lateByMinsInMillis;
    }
}