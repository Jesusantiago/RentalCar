package com.proyectofinal.car.dto.car;

import com.proyectofinal.car.enums.StatusCar;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CarRegisterDTO {

    @NotBlank (message = "Este mensaje no puede estar vació.")
    private String brand;

    @NotBlank (message = "Este mensaje no puede estar vacío.")
    private String model;

    @Positive (message = "Este mensaje no puede estar vacío.")
    private int carYear;

    @NotBlank (message = "Este mensaje no puede estar vacío.")
    private String licensePlate;

    @NotNull (message = "Este mensaje no puede estar vacío.")
    private StatusCar statusCar;

    @NotBlank (message = "Este mensaje no puede estar vacío.")
    private Long branch;

    //GETTERS AND SETTERS

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCarYear() {
        return carYear;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public StatusCar getStatusCar() {
        return statusCar;
    }

    public void setStatusCar(StatusCar statusCar) {
        this.statusCar = statusCar;
    }

    public Long getBranch() {
        return branch;
    }

    public void setBranch(Long branch) {
        this.branch = branch;
    }
}
