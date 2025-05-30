package com.rmartinic.flightabyss;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class FlightService {
    @Autowired
    private AmadeusAuth authSerivce;
    public FlightOfferSearch[] searchFlights(String originAirport, String destinationAirport, String departureDate, int numberOfPassengers, String currency ) throws ResponseException {
        Amadeus amadeus = authSerivce.getAmadeusClient();
        return amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", originAirport)
                        .and("destinationLocationCode",destinationAirport)
                        .and("departureDate", departureDate)
                        .and("adults", numberOfPassengers)
                        .and("currencyCode",currency)
        );
    }
    public FlightOfferSearch[] searchReturnFlights(String originAirport, String destinationAirport, String returnDate, int numberOfPassengers, String currency ) throws ResponseException {
        Amadeus amadeus = authSerivce.getAmadeusClient();
        return amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", destinationAirport)
                        .and("destinationLocationCode", originAirport)
                        .and("departureDate", returnDate)
                        .and("adults", numberOfPassengers)
                        .and("currencyCode", currency)
        );
    }
}
