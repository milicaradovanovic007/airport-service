package com.milicaradovanovic.daon.service;

import com.milicaradovanovic.daon.entity.FlightEntity;
import com.milicaradovanovic.daon.entity.GateEntity;
import com.milicaradovanovic.daon.repository.FlightRepository;
import com.milicaradovanovic.daon.repository.GateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Optional;

@Service
public class GateService {
    private GateRepository gateRepository;
    private FlightRepository flightRepository;

    @Autowired
    public GateService(GateRepository gateRepository, FlightRepository flightRepository) {
        this.gateRepository = gateRepository;
        this.flightRepository = flightRepository;
    }

    public Optional<GateEntity> getGateById(int gateId) {
        return this.gateRepository
                .findById(gateId);
    }

    public Object changeAvailability(int gateId) {

        Optional<GateEntity> optionalGateEntity = this.gateRepository.findById(gateId);
        if (optionalGateEntity.isEmpty()) {
            return null;
        }

        GateEntity gateEntity = optionalGateEntity.get();

        if (gateEntity.getStatus() == (byte) 0) {
            // check if there is a flight with assigned gate

            Collection<FlightEntity> flightEntities = this.flightRepository.findAllByGateByGateId(gateEntity);
            if (flightEntities.size() != 0) {
                // we cannot update this gate

                return false;
            }

            gateEntity.setStatus((byte) 1);
            this.gateRepository.save(gateEntity);

            return true;
        } else {
            // gate already available

            return false;
        }
    }

    public Collection<GateEntity> getAvailableGatesAtTime(int statusId) {
        LocalTime from = LocalTime.now(ZoneId.of("UTC"));
        LocalTime to = LocalTime.now(ZoneId.of("UTC"));

        return this.gateRepository
                .findAllByStatusIsNotAndAvailableTimeStartBeforeAndAvailableTimeEndAfter
                        ((byte) statusId, Time.valueOf(from), Time.valueOf(to));
    }

    public GateEntity changeAvailabilityTime(int gateId, LocalTime start, LocalTime end) {

        Optional<GateEntity> optionalGateEntity = this.gateRepository.findById(gateId);
        if (optionalGateEntity.isEmpty()) {
            return null;
        }

        GateEntity gateEntity = optionalGateEntity.get();
        gateEntity.setAvailableTimeStart(Time.valueOf(start));
        gateEntity.setAvailableTimeEnd(Time.valueOf(end));

        return this.gateRepository.save(gateEntity);
    }

    public Optional<GateEntity> getFirstAvailableGate(int statusId, boolean isExtra) {

        Collection<GateEntity> availableGates;
        if (isExtra) {
            LocalTime from = LocalTime.now(ZoneId.of("UTC"));
            LocalTime to = LocalTime.now(ZoneId.of("UTC"));

            availableGates = this.gateRepository
                    .findAllByStatusIsNotAndAvailableTimeStartBeforeAndAvailableTimeEndAfter((byte) statusId,
                            Time.valueOf(from), Time.valueOf(to));
        } else {
            availableGates = this.gateRepository
                    .findAllByStatusIsNot((byte) statusId);
        }

        if (availableGates.size() == 0) {
            return Optional.empty();
        }

        return availableGates.stream().findFirst();

    }
}
