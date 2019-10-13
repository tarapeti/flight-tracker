package com.project.fligthtracker.service.simple;

import com.project.fligthtracker.dto.PlaneInfoToTableDto;
import com.project.fligthtracker.model.Planes;
import com.project.fligthtracker.repositroy.PlaneRepository;
import com.project.fligthtracker.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.ejb.Stateless;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Stateless
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

    @Override
    public List<PlaneInfoToTableDto> parsePlaneList(List<Planes> planes) {
        List<PlaneInfoToTableDto> visualFormat = new ArrayList<>();
        for (Planes p : planes) {
            LocalDateTime departureDate =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(p.getDepartureTimeInMillis()), ZoneId.systemDefault());
            LocalDateTime landingDate =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(p.getLandingTimeInMillis()), ZoneId.systemDefault());

            String formattedDepartureDate = departureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            String formattedLandingDate = landingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

            PlaneInfoToTableDto dto = new PlaneInfoToTableDto(p.getId(), p.getCompanyName(), p.getDeparturePlace(), p.getLandingPlace(), formattedDepartureDate, formattedLandingDate, p.getDelay());
            visualFormat.add(dto);

        }
        return visualFormat;
    }

    //service method for gettting max delay
    @Override
    public Planes getMostDelayedPlane(List<Planes> allPlanes) {
        return allPlanes
                .stream()
                .max(Comparator.comparing(Planes::getDelay))
                .orElseThrow(NoSuchElementException::new);
    }

    //service method to get avg delay
    @Override
    public OptionalDouble getAvgDelay(List<Planes> allPlanes) {
        return allPlanes.stream()
                .mapToDouble(Planes::getDelay)
                .average();
    }
}
