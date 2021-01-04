package com.milicaradovanovic.daon.service;

import com.milicaradovanovic.daon.entity.FlightEntity;
import com.milicaradovanovic.daon.entity.GateEntity;
import com.milicaradovanovic.daon.repository.FlightRepository;
import com.milicaradovanovic.daon.repository.GateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FlightService {
    private FlightRepository flightRepository;
    private GateRepository gateRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository, GateRepository gateRepository) {
        this.flightRepository = flightRepository;
        this.gateRepository = gateRepository;
    }

    public Optional<FlightEntity> getFlightByFlightNumber(long flightNumber) {
        return this.flightRepository
                .findByFlightNumber((int) flightNumber);
    }

    @Transactional(rollbackOn = Exception.class)
    public void updateFlightWithAvailableGate(FlightEntity flightEntity, GateEntity gateEntity, int statusId) {
        flightEntity.setGateByGateId(gateEntity);
        this.flightRepository.save(flightEntity);

        // change the gate status
        gateEntity.setStatus((byte) statusId);
        this.gateRepository.save(gateEntity);
    }
}
