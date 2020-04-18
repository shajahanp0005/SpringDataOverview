package com.sha.springdataoverview.service;

import com.sha.springdataoverview.entity.Flight;
import com.sha.springdataoverview.repository.FlightRepository;
import org.springframework.stereotype.Component;

@Component
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void saveFlight(Flight flight){
        flightRepository.save(flight);
        throw new RuntimeException("I failed");
    }
}
