package com.proyectofinal.login.loginservice.dto;

public class LoginResponseDTO {

    private String token;
    private Long id;
    private String username;
    private String LicenseType;

    public LoginResponseDTO(String token, Long id, String username, String licenseType) {
        this.token = token;
        this.id = id;
        this.username = username;
        LicenseType = licenseType;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLicenseType() {
        return LicenseType;
    }
}
