package com.project.fligthtracker.service;

import com.project.fligthtracker.model.Planes;

import java.util.List;

public interface PlaneService {

    void savePlane(Planes plane);

    void deletePlane(Planes plane);

    List<Planes> findAllPlanes();

    Planes findPlaneByCompanyName(String companyName);

    void updatePlane(Planes planes);

    Planes findById(int id);
}
