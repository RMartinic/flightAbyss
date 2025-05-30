package com.rmartinic.flightabyss;

import com.amadeus.resources.FlightOfferSearch;
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
            List<FlightOfferSearch> matchingOutgoing = new ArrayList<>();
            List<FlightOfferSearch> alternativesOutgoing = new ArrayList<>();
            var results = flightService.searchFlights(originAirport, destinationAirport, departureDate,numberOfPassengers,currency);
            for (var flight : results){
                if(flight.getItineraries()[0].getSegments()[0].getArrival().getIataCode().equalsIgnoreCase(destinationAirport)){
                    matchingOutgoing.add(flight);
                }
                else{
                    alternativesOutgoing.add(flight);
                }
            }
            model.addAttribute("matching", matchingOutgoing);
            model.addAttribute("alternatives", alternativesOutgoing);
            model.addAttribute("numberOfPassengers", numberOfPassengers);
        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }
    }
