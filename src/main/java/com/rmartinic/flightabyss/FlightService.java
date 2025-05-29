package com.rmartinic.flightabyss;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FlightService {
    @Autowired
    private AmadeusAuth authSerivce;
    public FlightOfferSearch[] searchFlights(String originAirport, String destinationAirport, String departureDate, String returnDate, int numberOfPassengers, String currency ) throws ResponseException {
        Amadeus amadeus = authSerivce.getAmadeusClient();
        return amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", originAirport)
                        .and("destinationLocationCode",destinationAirport)
                        .and("departureDate", departureDate)
                        .and("adults", numberOfPassengers)
                        .and("currencyCode",currency)
        );
    }
}
