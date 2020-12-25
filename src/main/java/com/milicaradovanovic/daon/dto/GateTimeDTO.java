package com.milicaradovanovic.daon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public class GateTimeDTO {

//    @NotNull
    @NotNull
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime availableTimeStart;

    @NotNull
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime availableTimeEnd;

    public LocalTime getAvailableTimeStart() {
        return availableTimeStart;
    }

    public void setAvailableTimeStart(LocalTime availableTimeStart) {
        this.availableTimeStart = availableTimeStart;
    }

    public LocalTime getAvailableTimeEnd() {
        return availableTimeEnd;
    }

    public void setAvailableTimeEnd(LocalTime availableTimeEnd) {
        this.availableTimeEnd = availableTimeEnd;
    }
}
