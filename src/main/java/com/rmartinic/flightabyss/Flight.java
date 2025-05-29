package com.rmartinic.flightabyss;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Date;
@Entity
public class Flight {

    @Id
    @GeneratedValue
    private long id;




    private String originAirport;
    private String destinationAirport;
    private LocalDate timeOfDeparture;
    private LocalDate timeOfReturn;
    private int numberOfPassengers;
    private int price;

    public String getOriginAirport() {
        return originAirport;
    }
    public String getDestinationAirport() {
        return destinationAirport;
    }
    public LocalDate getTimeOfDeparture() {
        return timeOfDeparture;
    }
    public LocalDate getTimeOfReturn() {
        return timeOfReturn;
    }
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }
    public int getPrice() {
        return price;
    }

    public Flight(){}

    public Flight(String originAirport, String destinationAirport, LocalDate timeOfDeparture, LocalDate timeOfReturn, int numberOfPassengers, int price) {
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.timeOfDeparture = timeOfDeparture;
        this.timeOfReturn = timeOfReturn;
        this.numberOfPassengers = numberOfPassengers;
        this.price = price;
    }
}
