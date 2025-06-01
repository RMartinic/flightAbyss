package com.rmartinic.flightabyss;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import java.time.LocalDateTime;
@Entity
public class Flight {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;




    private String originAirport;
    private String destinationAirport;
    private String timeOfDeparture;
    private String timeOfReturn;
    private int numberOfPassengers;
    private String currency;
    private boolean isReturn;

    public long getId() {return id;}
    public String getOriginAirport() {
        return originAirport;
    }
    public String getDestinationAirport() {
        return destinationAirport;
    }
    public String getTimeOfDeparture() {
        return timeOfDeparture;
    }
    public String getTimeOfReturn() {
        return timeOfReturn;
    }
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }
    public String getCurrency() {
        return currency;
    }
    public boolean isReturn() {return isReturn;}


    public void setReturn(boolean  b) {isReturn=b;}

    public Flight(){}

    public Flight(String originAirport, String destinationAirport, String timeOfDeparture, String timeOfReturn, int numberOfPassengers, String currency) {
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.timeOfDeparture = timeOfDeparture;
        this.timeOfReturn = timeOfReturn;
        this.numberOfPassengers = numberOfPassengers;
        this.currency = currency;
        this.isReturn = false;
    }

}
