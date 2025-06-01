package com.rmartinic.flightabyss;

import com.amadeus.resources.FlightOfferSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.rmartinic.flightabyss.util.JsonUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import com.rmartinic.flightabyss.FlightService;


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
            List<FlightOfferSearch> outgoingFlights = new ArrayList<>();
            List<FlightOfferSearch> returnFlights = new ArrayList<>();
            List<FlightOfferSearch> outgoingAlternatives = new ArrayList<>();
            List<FlightOfferSearch> returningAlternatives = new ArrayList<>();


            var results = flightService.searchWithCache(originAirport, destinationAirport, departureDate,returnDate, numberOfPassengers,currency);
            for (FlightResult flightResult : results){
                FlightOfferSearch offer = JsonUtil.deserializeFromJson(flightResult.getOfferJson());
                String arrivalCode = offer.getItineraries()[0].getSegments()[0].getArrival().getIataCode();
                String flightDate = offer.getItineraries()[0].getSegments()[0].getDeparture().getAt().substring(0,10);
                if (arrivalCode.equalsIgnoreCase(destinationAirport)){
                    outgoingFlights.add(offer);
                }
                else if(arrivalCode.equalsIgnoreCase(originAirport)){
                    returnFlights.add(offer);
                }
                else if(flightDate.equalsIgnoreCase(departureDate)){
                    outgoingAlternatives.add(offer);
                }
                else{
                    returningAlternatives.add(offer);
                }

            }
            if (outgoingFlights.isEmpty()){
                model.addAttribute("errorNoFlights","Sorry, we did not find any outgoing flights you searched on that date.");
            }
            if (returnFlights.isEmpty() && returnDate!=null && !returnDate.isEmpty()){
                model.addAttribute("errorNoReturnFlights","Sorry, we did not find any returning flights you searched on that date.");
            }

            model.addAttribute("outgoing", outgoingFlights);
            model.addAttribute("returning", returnFlights);
            model.addAttribute("outgoingAlternatives", outgoingAlternatives);
            model.addAttribute("returningAlternatives", returningAlternatives);
            model.addAttribute("numberOfPassengers", numberOfPassengers);
        } catch (Exception e){
            model.addAttribute("error", "An error occurred while searching for flights: " + e.getMessage());
        }
        return "index";
    }
    }
