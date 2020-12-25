package com.milicaradovanovic.daon.controller;

import com.milicaradovanovic.daon.dto.FlightDTO;
import com.milicaradovanovic.daon.dto.ResponseDTO;
import com.milicaradovanovic.daon.dto.StatusEnum;
import com.milicaradovanovic.daon.entity.FlightEntity;
import com.milicaradovanovic.daon.service.AirportUserService;
import com.milicaradovanovic.daon.service.FlightService;
import com.milicaradovanovic.daon.util.DTOConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private AirportUserService airportUserService;
    private FlightService flightService;
    private DTOConverter converter;

    public FlightController(AirportUserService airportUserService, FlightService flightService, DTOConverter converter) {
        this.airportUserService = airportUserService;
        this.flightService = flightService;
        this.converter = converter;
    }

    @GetMapping(value = "/{flightNumber}/assigned-gate")
    public ResponseEntity<ResponseDTO> getGateInformationForFlight(@PathVariable(value = "flightNumber") long flightNumber,
                                                            Authentication authentication) {

//        AirportUserEntity airportUser = this.airportUserService.getCurrentUser(authentication);
        Optional<FlightEntity> optionalFlightEntity = this.flightService.getFlightByFlightNumber(flightNumber);

        if (optionalFlightEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO<>(true, StatusEnum.RESOURCE_NOT_FOUND, null));
        }

        FlightEntity flightEntity = optionalFlightEntity.get();

        FlightDTO flightDTO = this.converter.toFlightDTO(flightEntity);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(StatusEnum.RESOURCE_FOUND,
                flightDTO.getGate()));
    }
}
