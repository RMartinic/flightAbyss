package com.rmartinic.flightabyss;

import jakarta.persistence.*;

@Entity
public class FlightResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Flight flight;

    @Lob
    private String offerJson;

    public FlightResult(){}
    public FlightResult(Flight flight, String offerJson) {
        this.flight = flight;
        this.offerJson = offerJson;
    }
    public long getId() {
        return id;
    }
    public Flight getFlight() {
        return flight;
    }

    public String getOfferJson() {
        return offerJson;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setOfferJson(String offerJson) {
        this.offerJson = offerJson;
    }
}
