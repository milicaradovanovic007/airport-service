package com.milicaradovanovic.daon.service;

import com.milicaradovanovic.daon.entity.FlightEntity;
import com.milicaradovanovic.daon.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlightService {
    private FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Optional<FlightEntity> getFlightByFlightNumber(long flightNumber) {
        return this.flightRepository
                .findByFlightNumber((int) flightNumber);
    }

}
