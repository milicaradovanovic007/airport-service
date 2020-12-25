package com.milicaradovanovic.daon.dto;

public class FlightDTO {
    private Integer flightNumber;

    private String Airline;

    private GateDTO gate;

    public Integer getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return Airline;
    }

    public void setAirline(String airline) {
        Airline = airline;
    }

    public GateDTO getGate() {
        return gate;
    }

    public void setGate(GateDTO gate) {
        this.gate = gate;
    }
}
