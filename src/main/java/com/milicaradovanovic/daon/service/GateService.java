package com.milicaradovanovic.daon.service;

import com.milicaradovanovic.daon.entity.GateEntity;
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

    @Autowired
    public GateService(GateRepository gateRepository) {
        this.gateRepository = gateRepository;
    }

    public Optional<GateEntity> getGateById(int gateId) {
        return this.gateRepository
                .findById(gateId);
    }

    public GateEntity changeAvailability(int gateId) {

        Optional<GateEntity> optionalGateEntity = this.gateRepository.findById(gateId);
        if (optionalGateEntity.isEmpty()) {
            return null;
        }

        GateEntity gateEntity = optionalGateEntity.get();
        gateEntity.setStatus(gateEntity.getStatus() == (byte) 1 ? (byte) 0 : (byte) 1);

        return this.gateRepository.save(gateEntity);
    }

    public Collection<GateEntity> getAvailableGatesAtTime() {
        LocalTime from = LocalTime.now(ZoneId.of("UTC"));
        LocalTime to = LocalTime.now(ZoneId.of("UTC"));

        return this.gateRepository
                .findAllByAvailableTimeStartBeforeAndAvailableTimeEndAfter(Time.valueOf(from), Time.valueOf(to));
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
}
