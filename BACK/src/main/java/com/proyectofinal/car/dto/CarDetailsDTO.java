package com.proyectofinal.car.dto;

import com.proyectofinal.car.dto.CarBranchDetailsDTO;

public class CarDetailsDTO {

    private Long id;
    private String brand;
    private String model;
    private int carYear;
    private String licensePlate;
    private CarBranchDetailsDTO branch;

    public CarDetailsDTO(Long id, String brand, String model, int carYear, String licensePlate, CarBranchDetailsDTO branch) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.carYear = carYear;
        this.licensePlate = licensePlate;
        this.branch = branch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public CarBranchDetailsDTO getBranch() {
        return branch;
    }

    public void setBranch(CarBranchDetailsDTO branch) {
        this.branch = branch;
    }
}
