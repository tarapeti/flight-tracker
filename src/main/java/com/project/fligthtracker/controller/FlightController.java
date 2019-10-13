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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime departureTimeDate = LocalDateTime.parse(departureTimeString, formatter);
        LocalDateTime landingTimeDate = LocalDateTime.parse(landingTimeString, formatter);
        long departureTimeInMilis = departureTimeDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long landingTimeInMilis = landingTimeDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        Planes newPlane = new Planes(companyName,departurePlace, landingPlace, departureTimeInMilis, landingTimeInMilis, lateByMins);

        planeService.savePlane(newPlane);
    }

    @RequestMapping(path = "/remove", method = RequestMethod.POST)
    public @ResponseBody
    void removePlaneById(@RequestParam(name = "planeId", required = false) Integer id) {
        planeService.deletePlane(planeService.findById(id));
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
