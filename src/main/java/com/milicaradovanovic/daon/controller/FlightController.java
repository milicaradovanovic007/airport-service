package com.milicaradovanovic.daon.controller;

import com.milicaradovanovic.daon.dto.FlightDTO;
import com.milicaradovanovic.daon.dto.ResponseDTO;
import com.milicaradovanovic.daon.dto.StatusEnum;
import com.milicaradovanovic.daon.entity.FlightEntity;
import com.milicaradovanovic.daon.entity.GateEntity;
import com.milicaradovanovic.daon.service.AirportUserService;
import com.milicaradovanovic.daon.service.FlightService;
import com.milicaradovanovic.daon.service.GateService;
import com.milicaradovanovic.daon.util.DTOConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/flight")
@Valid
public class FlightController {

    private AirportUserService airportUserService;
    private FlightService flightService;
    private GateService gateService;
    private DTOConverter converter;

    public FlightController(AirportUserService airportUserService, FlightService flightService, GateService gateService,
                            DTOConverter converter) {
        this.airportUserService = airportUserService;
        this.flightService = flightService;
        this.gateService = gateService;
        this.converter = converter;
    }

    @GetMapping(value = "/{flightNumber}/assign-gate")
    public ResponseEntity<ResponseDTO> assignGateToFlight(@PathVariable(value = "flightNumber") long flightNumber,
                                                          @NotNull(message = "isExtra is required")
                                                          @RequestParam(name = "isExtra") boolean isExtra,
                                                          Authentication authentication) {

//        AirportUserEntity airportUser = this.airportUserService.getCurrentUser(authentication);
        Optional<FlightEntity> optionalFlightEntity = this.flightService.getFlightByFlightNumber(flightNumber);

        if (optionalFlightEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO<>(true, StatusEnum.RESOURCE_NOT_FOUND, null));
        }

        FlightEntity flightEntity = optionalFlightEntity.get();

        // assuming that the flight cannot change the gate (can be undone)
        if (flightEntity.getGateByGateId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO<>(true, StatusEnum.GATE_ALREADY_ASSIGNED, null));
        }

        Optional<GateEntity> optionalGateEntity = this.gateService.getFirstAvailableGate(0, isExtra);

        if (optionalGateEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO<>(StatusEnum.NO_AVAILABLE_GATES, null));
        }

        GateEntity gateEntity = optionalGateEntity.get();

        // update flight information
        this.flightService.updateFlightWithAvailableGate(flightEntity, gateEntity, 0);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(StatusEnum.RESOURCE_UPDATED,
                null));
    }

    @GetMapping(value = "/{flightNumber}/gate-info")
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
