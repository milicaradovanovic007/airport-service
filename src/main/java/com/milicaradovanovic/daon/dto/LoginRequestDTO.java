package com.milicaradovanovic.daon.dto;

import javax.validation.constraints.NotNull;

public class LoginRequestDTO {
    @NotNull
    private String email;

    @NotNull
    private String password;


    public LoginRequestDTO(@NotNull String email, @NotNull String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
