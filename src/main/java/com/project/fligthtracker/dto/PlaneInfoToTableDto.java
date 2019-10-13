package com.project.fligthtracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaneInfoToTableDto {

        private int id;

        private String companyName;

        private String departurePlace;

        private String landingPlace;

        //even tho dep and landing time wont be in milis i need to name them this way because the html listenes to fieldnames
        //and i can use only one table
        private String departureTimeInMillis;

        private String landingTimeInMillis;

        private int lateByMins;

    public PlaneInfoToTableDto(int id, String companyName, String departurePlace, String landingPlace, String departureTimeInMillis, String landingTimeInMillis, int lateByMins) {
        this.id = id;
        this.companyName = companyName;
        this.departurePlace = departurePlace;
        this.landingPlace = landingPlace;
        this.departureTimeInMillis = departureTimeInMillis;
        this.landingTimeInMillis = landingTimeInMillis;
        this.lateByMins = lateByMins;
    }
}
