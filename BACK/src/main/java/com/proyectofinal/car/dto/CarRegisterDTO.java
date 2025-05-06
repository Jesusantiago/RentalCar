package com.proyectofinal.car.dto;

import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.model.Branch;
import jakarta.validation.constraints.NotBlank;

public class CarRegisterDTO {

    @NotBlank (message = "Este mensaje no puede estar vació.")
    private String brand;

    @NotBlank (message = "Este mensaje no puede estar vacío.")
    private String model;

    @NotBlank (message = "Este mensaje no puede estar vacío.")
    private int carYear;

    @NotBlank (message = "Este mensaje no puede estar vacío.")
    private String licensePlate;

    @NotBlank (message = "Este mensaje no puede estar vacío.")
    private StatusCar statusCar;

    @NotBlank (message = "Este mensaje no puede estar vacío.")
    private Branch branch;

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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
