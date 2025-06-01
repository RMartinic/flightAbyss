package com.rmartinic.flightabyss;
import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.rmartinic.flightabyss.*;
import com.rmartinic.flightabyss.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    private AmadeusAuth authSerivce;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightResultRepository flightResultRepository;

    public List<FlightResult> searchWithCache(String originAirport, String destinationAirport, String departureDate,
                                              String returnDate, int numberOfPassengers, String currency) throws ResponseException {

        Flight existingSearch = flightRepository.findByParams(
                originAirport, destinationAirport, departureDate, returnDate, numberOfPassengers, currency
        );

        if (existingSearch != null) {
            return flightResultRepository.findByFlight(existingSearch);
        }

        Amadeus amadeus = authSerivce.getAmadeusClient();

        FlightOfferSearch[] offers = amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", originAirport)
                        .and("destinationLocationCode", destinationAirport)
                        .and("departureDate", departureDate)
                        .and("adults", numberOfPassengers)
                        .and("currencyCode", currency)
        );


        FlightOfferSearch[] combinedOffers;
        if (returnDate != null && !returnDate.isEmpty()) {
            FlightOfferSearch[] returnOffers = amadeus.shopping.flightOffersSearch.get(
                    Params.with("originLocationCode", destinationAirport)
                            .and("destinationLocationCode", originAirport)
                            .and("departureDate", returnDate)
                            .and("adults", numberOfPassengers)
                            .and("currencyCode", currency)
            );
            combinedOffers = new FlightOfferSearch[offers.length + returnOffers.length];
            System.arraycopy(offers, 0, combinedOffers, 0, offers.length);
            System.arraycopy(returnOffers, 0, combinedOffers, offers.length, returnOffers.length);
        } else {
            combinedOffers = offers;
        }


        Flight newSearch = new Flight(originAirport, destinationAirport, departureDate, returnDate,
                numberOfPassengers, currency);

        Flight savedSearch = flightRepository.save(newSearch);

        List<FlightResult> results = new ArrayList<>();
        for (FlightOfferSearch offer : combinedOffers) {
            FlightResult result = new FlightResult();
            result.setFlight(savedSearch);
            result.setOfferJson(JsonUtil.serializeToJson(offer));
            results.add(flightResultRepository.save(result));
        }

        return results;
    }
}
