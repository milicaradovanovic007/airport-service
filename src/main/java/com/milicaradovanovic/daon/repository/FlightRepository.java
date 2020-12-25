package com.milicaradovanovic.daon.repository;

import com.milicaradovanovic.daon.entity.FlightEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FlightRepository extends CrudRepository<FlightEntity, Integer> {
    Optional<FlightEntity> findByFlightNumber(int flightNumber);
}
