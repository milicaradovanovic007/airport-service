package com.milicaradovanovic.daon.util;

import com.milicaradovanovic.daon.dto.FlightDTO;
import com.milicaradovanovic.daon.dto.GateDTO;
import com.milicaradovanovic.daon.entity.FlightEntity;
import com.milicaradovanovic.daon.entity.GateEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class DTOConverter {

    private ModelMapper modelMapper;

    @Autowired
    public DTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FlightDTO toFlightDTO(FlightEntity flightEntity) {
        FlightDTO flightDTO = modelMapper.map(flightEntity, FlightDTO.class);

        GateDTO gateDTO = this.toGateDTO(flightEntity.getGateByGateId());
        flightDTO.setGate(gateDTO);

        return flightDTO;
    }

    public GateDTO toGateDTO(GateEntity gateEntity) {
        GateDTO gateDTO = this.modelMapper.map(gateEntity, GateDTO.class);

        gateDTO.setAvailable(gateDTO.getStatus().equals("1"));

        return gateDTO;
    }

    public List<GateDTO> toGateDTOs(Collection<GateEntity> gateEntities) {
        List<GateDTO> gateDTOS = new ArrayList<>();

        gateEntities.forEach(gateEntity -> {
            gateDTOS.add(this.toGateDTO(gateEntity));
        });

        return gateDTOS;
    }
}
