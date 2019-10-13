package com.project.fligthtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.fligthtracker.model.Planes;
import com.project.fligthtracker.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
public class FlightController {

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


    @Autowired
    private PlaneService planeService;

    @RequestMapping(value = "/getAll")
    String getAllFlights(Model model
    ){
        model.addAttribute("allPlanes", planeService.findAllPlanes());
        return "planes";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    @ResponseBody
    void addNewPlane(@RequestParam( required = false) String companyName,
                     @RequestParam( required = false) String departurePlace,
                     @RequestParam( required = false) String landingPlace,
                     @RequestParam( required = false, name = "departureTime") String departureTimeString,
                     @RequestParam( required = false, name = "landingTime") String landingTimeString,
                     @RequestParam( required = false, name = "lateByMins") Integer lateByMins){

        LocalDateTime departureTimeDate = LocalDateTime.parse(departureTimeString, FORMATTER);
        LocalDateTime landingTimeDate = LocalDateTime.parse(landingTimeString, FORMATTER);
        long departureTimeInMilis = departureTimeDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long landingTimeInMilis = landingTimeDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        Planes newPlane = new Planes(companyName,departurePlace, landingPlace, departureTimeInMilis, landingTimeInMilis, lateByMins);

        planeService.savePlane(newPlane);
    }

    //couldn't seem to work with deletemapping or .DELETE because html forms doesn't seem to like them
    @RequestMapping(path = "/remove", method = RequestMethod.POST)
    public @ResponseBody
    void removePlaneById(@RequestParam(name = "planeId", required = false) Integer id) {
        planeService.deletePlane(planeService.findById(id));
    }

    //html doesn't seem to like update as well
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    @ResponseBody
    void updatePlane(@RequestParam( required = false) String id,
                     @RequestParam( required = false) String companyName,
                     @RequestParam( required = false) String departurePlace,
                     @RequestParam( required = false) String landingPlace,
                     @RequestParam( required = false, name = "departureTime") String departureTimeString,
                     @RequestParam( required = false, name = "landingTime") String landingTimeString,
                     @RequestParam( required = false, name = "lateByMins") Integer lateByMins) {

        LocalDateTime departureTimeDate = LocalDateTime.parse(departureTimeString, FORMATTER);
        LocalDateTime landingTimeDate = LocalDateTime.parse(landingTimeString, FORMATTER);
        long departureTimeInMilis = departureTimeDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long landingTimeInMilis = landingTimeDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        Planes plane = planeService.findById(Integer.valueOf(id));
        plane.setCompanyName(companyName);
        plane.setDeparturePlace(departurePlace);
        plane.setLandingPlace(landingPlace);
        plane.setDepartureTimeInMillis(departureTimeInMilis);
        plane.setLandingTimeInMillis(landingTimeInMilis);
        plane.setLateByMins(lateByMins);

        planeService.updatePlane(plane);
    }

    //object to string in json format converter
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
