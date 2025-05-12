package com.proyectofinal.login.loginservice.dto;

public class JsonResponseDTO {

    private String token;

    public JsonResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
