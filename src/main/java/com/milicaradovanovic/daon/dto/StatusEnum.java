package com.milicaradovanovic.daon.dto;

public enum StatusEnum {
    OK(2000, "Success"),
    RESOURCE_UPDATED(1000, "Resource updated successfully"),
    RESOURCE_FOUND(1001, "Resource found"),
    NO_GATE_ASSIGNED(1002, "There is no gate assigned for this flight"),

    RESOURCE_NOT_FOUND(4000, "Resource not found"),
    FORBIDDEN_ACCESS(4001, "Access is forbidden, You need to be an administrator"),
    METHOD_UNSUPPORTED(4002, "Method not supported"),
    BAD_CREDENTIALS(4003, "Bad credentials"),
    BODY_VALIDATION_ERROR(4004, "Invalid parameters in request body"),
    NO_AVAILABLE_GATES(4005, "There is no available gates"),
    GATE_ALREADY_ASSIGNED(4006, "This flight already has a gate assigned"),
    QUERY_VALIDATION_ERROR(4007, "Invalid query parameters in URL"),
    CANNOT_UPDATE_GATE(4008, "Gate cannot be updated");

    private int code;
    private String message;

    StatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
