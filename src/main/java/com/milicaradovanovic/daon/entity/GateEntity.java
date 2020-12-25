package com.milicaradovanovic.daon.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "gate", schema = "mysql", catalog = "")
public class GateEntity {
    private int id;
    private String name;
    private byte status;
    private Time availableTimeStart;
    private Time availableTimeEnd;
    private Collection<FlightEntity> flightsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "available_time_start", nullable = true)
    public Time getAvailableTimeStart() {
        return availableTimeStart;
    }

    public void setAvailableTimeStart(Time availableTimeStart) {
        this.availableTimeStart = availableTimeStart;
    }

    @Basic
    @Column(name = "available_time_end", nullable = true)
    public Time getAvailableTimeEnd() {
        return availableTimeEnd;
    }

    public void setAvailableTimeEnd(Time availableTimeEnd) {
        this.availableTimeEnd = availableTimeEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GateEntity that = (GateEntity) o;
        return id == that.id && status == that.status && Objects.equals(name, that.name) && Objects.equals(availableTimeStart, that.availableTimeStart) && Objects.equals(availableTimeEnd, that.availableTimeEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, availableTimeStart, availableTimeEnd);
    }

    @OneToMany(mappedBy = "gateByGateId")
    public Collection<FlightEntity> getFlightsById() {
        return flightsById;
    }

    public void setFlightsById(Collection<FlightEntity> flightsById) {
        this.flightsById = flightsById;
    }
}
