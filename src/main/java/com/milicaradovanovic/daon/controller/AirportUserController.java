package com.milicaradovanovic.daon.controller;

import com.milicaradovanovic.daon.dto.LoginRequestDTO;
import com.milicaradovanovic.daon.dto.LoginResponseDTO;
import com.milicaradovanovic.daon.service.AirportUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AirportUserController {

    private AirportUserService airportUserService;

    @Autowired
    public AirportUserController(AirportUserService airportUserService) {
        this.airportUserService = airportUserService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDTO loginRequestDto) {
        HashMap<String, Object> tokens = this.airportUserService.login(loginRequestDto.getEmail(),
                loginRequestDto.getPassword())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.UNAUTHORIZED, "Bad Credentials"));

        LoginResponseDTO loginResponse = new LoginResponseDTO((String) tokens.get("access"));
        return ResponseEntity.ok(loginResponse);
    }
}
