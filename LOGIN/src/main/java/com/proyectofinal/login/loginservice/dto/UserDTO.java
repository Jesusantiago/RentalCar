package com.proyectofinal.login.loginservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UserDTO {
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String name;

    @NotBlank(message = "El apellido no puede estar vacío.")
    private String lastName;

    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "Formato de email inválido.")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    private String password;

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    private String userName;

    @NotBlank(message = "El tipo de licencia no puede estar vacío.")
    private String licenseType;

    @NotNull(message = "La fecha de nacimiento no puede estar vacía.")
    private LocalDate dateOfBirth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
