package com.rmartinic.flightabyss.util;

import com.amadeus.resources.FlightOfferSearch;
import com.google.gson.Gson;

public class JsonUtil {

    private static final Gson gson = new Gson();

    public static String serializeToJson(FlightOfferSearch offer) {
        try {
            return gson.toJson(offer);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing FlightOfferSearch to JSON", e);
        }
    }

    public static FlightOfferSearch deserializeFromJson(String json) {
        try {
            return gson.fromJson(json, FlightOfferSearch.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing FlightOfferSearch from JSON", e);
        }
    }
}
