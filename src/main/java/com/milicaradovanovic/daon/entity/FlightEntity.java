package com.milicaradovanovic.daon.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "flight", schema = "mysql", catalog = "")
public class FlightEntity {
    private int flightNumber;
    private String airline;
    private GateEntity gateByGateId;

    @Id
    @Column(name = "flight_number", nullable = false)
    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Basic
    @Column(name = "airline", nullable = false, length = 50)
    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightEntity that = (FlightEntity) o;
        return flightNumber == that.flightNumber && Objects.equals(airline, that.airline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, airline);
    }

    @ManyToOne
    @JoinColumn(name = "gate_id", referencedColumnName = "id", nullable = false)
    public GateEntity getGateByGateId() {
        return gateByGateId;
    }

    public void setGateByGateId(GateEntity gateByGateId) {
        this.gateByGateId = gateByGateId;
    }
}
