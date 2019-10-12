package com.project.fligthtracker.controller;

import com.project.fligthtracker.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class FlightController {

    @Autowired
    private PlaneService planeService;

    @RequestMapping("/getAll")
    String getAllFlights(Model model){
        model.addAttribute("allPlanes", planeService.findAllPlanes());
        return "planes";
    }
}
