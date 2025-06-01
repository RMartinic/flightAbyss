package com.rmartinic.flightabyss;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightResultRepository extends JpaRepository<FlightResult, Long> {
    List<FlightResult> findByFlight(Flight flight);
}
