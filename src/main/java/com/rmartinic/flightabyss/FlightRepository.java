package com.rmartinic.flightabyss;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlightRepository extends JpaRepository<Flight, Long>{
    @Query("SELECT f FROM Flight f WHERE " +
            "f.originAirport = :origin AND " +
            "f.destinationAirport = :destination AND " +
            "f.timeOfDeparture = :departure AND " +
            "(:returnDate IS NULL OR f.timeOfReturn = :returnDate) AND " +
            "f.numberOfPassengers = :passengers AND " +
            "f.currency = :currency")
    Flight findByParams(
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("departure") String departure,
            @Param("returnDate") String returnDate,
            @Param("passengers") int passengers,
            @Param("currency") String currency
    );

}

