package com.project.fligthtracker.controller;

import com.project.fligthtracker.model.Planes;
import com.project.fligthtracker.repositroy.PlaneRepository;
import com.project.fligthtracker.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.ejb.EJB;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class FlightController {

    @Autowired
    PlaneRepository planeRepository;

    //setting date format
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    //injecting planceservice
    @EJB
    private PlaneService planeService;

    //extending html model with all the planes
    @RequestMapping(value = "/getAll")
    String getAllFlights(Model model){
        List<Planes> allPlanes = planeService.findAllPlanes();

        //finding the plane which was delayed the most
        Planes maxDelay = planeService.getMostDelayedPlane(allPlanes);
        //getting avg delay
        OptionalDouble average = planeService.getAvgDelay(allPlanes);

        model.addAttribute("maxDelay", maxDelay);
        model.addAttribute("avgDelay", average.getAsDouble());
        model.addAttribute("allPlanes", planeService.parsePlaneList(allPlanes));
        return "planes";
    }

    //getting necessary data to insert a new plane into db
    @RequestMapping(path = "/save", method = RequestMethod.POST)
    @ResponseBody
    RedirectView addNewPlane(@RequestParam( required = false) String companyName,
                     @RequestParam( required = false) String departurePlace,
                     @RequestParam( required = false) String landingPlace,
                     @RequestParam( required = false, name = "departureTime") String departureTimeString,
                     @RequestParam( required = false, name = "landingTime") String landingTimeString,
                     @RequestParam( required = false, name = "delay") Integer delay){

        //converting data from html form into object to later save it in acceptable form in db
        long departureTimeInMillis = LocalDateTime.parse(departureTimeString, FORMATTER).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long landingTimeInMillis = LocalDateTime.parse(landingTimeString, FORMATTER).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        Planes newPlane = new Planes(companyName,departurePlace, landingPlace, departureTimeInMillis, landingTimeInMillis, delay);

        planeService.savePlane(newPlane);

        //simple redirect to visually inform user about the insertion
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/getAll");
        return redirectView;

    }

    //couldn't seem to work with deletemapping or .DELETE because html forms doesn't seem to like them
    @RequestMapping(path = "/remove", method = RequestMethod.POST)
    public @ResponseBody
    RedirectView removePlaneById(@RequestParam(name = "planeId", required = false) Integer id) {
        //deleting plane with {id}
        planeService.deletePlane(planeService.findById(id));
        //redirect for visual info
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/getAll");
        return redirectView;
    }

    //html doesn't seem to like update as well
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    @ResponseBody
    RedirectView updatePlane(@RequestParam( required = false) String id,
                     @RequestParam( required = false) String companyName,
                     @RequestParam( required = false) String departurePlace,
                     @RequestParam( required = false) String landingPlace,
                     @RequestParam( required = false, name = "departureTime") String departureTimeString,
                     @RequestParam( required = false, name = "landingTime") String landingTimeString,
                     @RequestParam( required = false, name = "delay") Integer delay) {

        //retrieving and converting data from html form
        long departureTimeInMillis = LocalDateTime.parse(departureTimeString, FORMATTER).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long landingTimeInMillis = LocalDateTime.parse(landingTimeString, FORMATTER).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        //getting the plane that will be updated
        Planes plane = planeService.findById(Integer.valueOf(id));

        //changing parameters
        plane.setCompanyName(companyName);
        plane.setDeparturePlace(departurePlace);
        plane.setLandingPlace(landingPlace);
        plane.setDepartureTimeInMillis(departureTimeInMillis);
        plane.setLandingTimeInMillis(landingTimeInMillis);
        plane.setDelay(delay);

        planeService.updatePlane(plane);

        //redirect for visual info
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/getAll");
        return redirectView;
    }
    @RequestMapping(path = "filter", method = RequestMethod.POST)
    @ResponseBody
    RedirectView getAllFilteredFlights(Model model,
                                 @RequestParam( required = false, name = "departureTime") String departureTimeFrom){
        long departureTimeFromInMillis =  LocalDateTime.parse(departureTimeFrom, FORMATTER)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        List<Planes> allPlanes = planeService.findAllPlanes();
        List<Planes> filteredPlanes = planeService.getFilteredPlanes(allPlanes, departureTimeFromInMillis);
        model.addAttribute("allPlanes", planeService.parsePlaneList(filteredPlanes));

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/getAll");
        return redirectView;
    }
}
