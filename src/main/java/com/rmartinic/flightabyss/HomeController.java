package com.rmartinic.flightabyss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/")
    public String home(){
        return "index";
    }
    @PostMapping("/search")
    public String searchFlights(@RequestParam String originAirport,
                                @RequestParam String destinationAirport,
                                @RequestParam String departureDate,
                                @RequestParam(required = false) String returnDate,
                                @RequestParam(defaultValue = "1") int numberOfPassengers,
                                @RequestParam(defaultValue = "EUR") String currency,
                                Model model){
        try {
            var results = flightService.searchFlights(originAirport, destinationAirport, departureDate,returnDate,numberOfPassengers,currency);
            model.addAttribute("results", results);
        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }
    }
