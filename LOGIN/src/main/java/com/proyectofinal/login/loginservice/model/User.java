package com.proyectofinal.login.loginservice.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String licenseType;

    @Column()
    private LocalDate dateOfBirth;

    /* GETTERS AND SETTERS */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String apellido) {
        this.lastName = apellido;
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

    public void setUserName(String usuary) {
        this.userName = usuary;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String tipoLicencia) {
        this.licenseType = tipoLicencia;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate fechaNacimiento) {
        this.dateOfBirth = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "userses{" +
                "id=" + id +
                ", nombre='" + name + '\'' +
                ", apellido='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tipoLicencia='" + licenseType + '\'' +
                ", fechaNacimiento=" + dateOfBirth +
                '}';
    }
}
