package com.project.fligthtracker.service.simple;

import com.project.fligthtracker.model.Planes;
import com.project.fligthtracker.repositroy.PlaneRepository;
import com.project.fligthtracker.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SimplePlaneService implements PlaneService {
    @Autowired
    private PlaneRepository planeRepository;

    @Override
    public void savePlane(Planes plane) {
        planeRepository.save(plane);
    }

    @Override
    public void deletePlane(Planes plane) {
        planeRepository.delete(plane);
    }

    @Override
    public List<Planes> findAllPlanes() {
        return planeRepository.findAll();
    }

    @Override
    public Planes findPlaneByCompanyName(String companyName) {
        return planeRepository.findByCompanyName(companyName);
    }

    @Override
    public void updatePlane(Planes plane) {
        //jpa repositroy's save works as update if the database already contains the object's id in it
        planeRepository.save(plane);
    }

    @Override
    public Planes findById(int id) {
        return planeRepository.findById(id);
    }
}
