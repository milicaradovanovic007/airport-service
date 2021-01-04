package com.milicaradovanovic.daon.repository;

import com.milicaradovanovic.daon.entity.FlightEntity;
import com.milicaradovanovic.daon.entity.GateEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface FlightRepository extends CrudRepository<FlightEntity, Integer> {
    Optional<FlightEntity> findByFlightNumber(int flightNumber);

    Collection<FlightEntity> findAllByGateByGateId(GateEntity gateEntity);
}
