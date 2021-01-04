package com.milicaradovanovic.daon.controller;

import com.milicaradovanovic.daon.dto.GateDTO;
import com.milicaradovanovic.daon.dto.GateTimeDTO;
import com.milicaradovanovic.daon.dto.ResponseDTO;
import com.milicaradovanovic.daon.dto.StatusEnum;
import com.milicaradovanovic.daon.entity.AirportUserEntity;
import com.milicaradovanovic.daon.entity.GateEntity;
import com.milicaradovanovic.daon.service.AirportUserService;
import com.milicaradovanovic.daon.service.GateService;
import com.milicaradovanovic.daon.util.DTOConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/gate")
public class GateController {

    private AirportUserService airportUserService;
    private GateService gateService;
    private DTOConverter converter;

    public GateController(AirportUserService airportUserService, GateService gateService, DTOConverter converter) {
        this.airportUserService = airportUserService;
        this.gateService = gateService;
        this.converter = converter;
    }

    @GetMapping(value = "/{gateId}/info")
    public ResponseEntity<ResponseDTO> getGateInformation(@PathVariable(value = "gateId") int gateId,
                                                          Authentication authentication) {

//        AirportUserEntity airportUser = this.airportUserService.getCurrentUser(authentication);
        Optional<GateEntity> optionalGateEntity = this.gateService.getGateById(gateId);

        if (optionalGateEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO<>(true, StatusEnum.RESOURCE_NOT_FOUND, null));
        }

        GateEntity gateEntity = optionalGateEntity.get();

        GateDTO gateDTO = this.converter.toGateDTO(gateEntity);

        HashMap<String, Object> response = new HashMap<>();
        response.put("available", gateDTO.getAvailable());
        response.put("message", gateDTO.getAvailable() ? "Gate is available." : "Gate is not available.");

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(StatusEnum.RESOURCE_FOUND,
                response));
    }

    @PatchMapping(value = "/{gateId}/status")
    public ResponseEntity<ResponseDTO> updateGateStatus(@PathVariable(value = "gateId") int gateId,
                                                        Authentication authentication) {
//        AirportUserEntity airportUser = this.airportUserService.getCurrentUser(authentication);

        Object response = this.gateService.changeAvailability(gateId);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO<>(true, StatusEnum.RESOURCE_NOT_FOUND, null));
        }

        if (response.equals(false)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO<>(true, StatusEnum.CANNOT_UPDATE_GATE, null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(StatusEnum.RESOURCE_UPDATED,
                        null));
    }

    @GetMapping(value = "/available-gates")
    public ResponseEntity<ResponseDTO> getGateInformationAtTime(Authentication authentication) {

//        AirportUserEntity airportUser = this.airportUserService.getCurrentUser(authentication);
        Collection<GateEntity> gateEntityCollection = this.gateService.getAvailableGatesAtTime(0);

        if (gateEntityCollection.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO<>(StatusEnum.RESOURCE_FOUND, new ArrayList<>()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(StatusEnum.RESOURCE_FOUND,
                this.converter.toGateDTOs(gateEntityCollection)));
    }

    @PatchMapping(value = "/{gateId}/time")
    public ResponseEntity<ResponseDTO> updateGateAvailabilityTime(@Valid @RequestBody GateTimeDTO gateTimeDTO,
                                                                  @PathVariable(value = "gateId") int gateId,
                                                                  Authentication authentication) {

        AirportUserEntity airportUser = this.airportUserService.getCurrentUser(authentication);

        if (airportUser.getIsAdmin() == 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ResponseDTO<>(true, StatusEnum.FORBIDDEN_ACCESS, null));
        }


        GateEntity gateEntity = this.gateService.changeAvailabilityTime(gateId, gateTimeDTO.getAvailableTimeStart(),
                gateTimeDTO.getAvailableTimeEnd());

        if (gateEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO<>(true, StatusEnum.RESOURCE_NOT_FOUND, null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(StatusEnum.RESOURCE_UPDATED,
                        null));
    }
}
