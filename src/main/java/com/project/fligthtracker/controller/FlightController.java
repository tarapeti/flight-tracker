package com.project.fligthtracker.controller;

import com.project.fligthtracker.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                     @RequestParam( required = false, name = "departureTimeInMillis") String departureTimeInDate,
                     @RequestParam( required = false, name = "landingTimeInMillis") String landingTimeInDate,
                     @RequestParam(name = "lateByMinsInMillis") int lateByMinsInDate,
                     Model model
    ){

    }
}
