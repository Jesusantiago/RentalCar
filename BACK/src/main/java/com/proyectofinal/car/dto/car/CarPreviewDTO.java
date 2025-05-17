package com.proyectofinal.car.dto.car;

import com.proyectofinal.car.enums.StatusCar;

public class CarPreviewDTO {

    private Long id;
    private String model;
    private String brand;
    private String branchCity;
    private String branchName;
    private StatusCar statusCar;

    public CarPreviewDTO(Long id, String model, String brand, String branchCity, String branchName, StatusCar statusCar) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.branchCity = branchCity;
        this.branchName = branchName;
        this.statusCar = statusCar;
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public String getBranchCity() {
        return branchCity;
    }

    public String getBranchName() {
        return branchName;
    }

    public StatusCar getStatusCar() {
        return statusCar;
    }

    public void setStatusCar(StatusCar statusCar) {
        this.statusCar = statusCar;
    }

}
