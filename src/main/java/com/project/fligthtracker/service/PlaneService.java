package com.project.fligthtracker.service;

import com.project.fligthtracker.dto.PlaneInfoToTableDto;
import com.project.fligthtracker.model.Planes;

import java.util.List;
import java.util.OptionalDouble;

public interface PlaneService {

    void savePlane(Planes plane);

    void deletePlane(Planes plane);

    List<Planes> findAllPlanes();

    Planes findPlaneByCompanyName(String companyName);

    void updatePlane(Planes planes);

    Planes findById(int id);

    List<PlaneInfoToTableDto> parsePlaneList(List<Planes> planes);

    Planes getMostDelayedPlane(List<Planes> allPlanes);

    OptionalDouble getAvgDelay(List<Planes> allPlanes);

    List<Planes> getFilteredPlanes(List<Planes> allPlanes, long departureTimeFromInMillis);
}
