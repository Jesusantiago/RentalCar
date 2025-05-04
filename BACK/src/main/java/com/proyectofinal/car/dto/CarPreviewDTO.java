package com.proyectofinal.car.dto;

public class CarPreviewDTO {

    private Long id;
    private String model;
    private String brand;
    private String branchCity;
    private String branchName;

    public CarPreviewDTO(Long id, String model, String brand, String branchCity, String branchName) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.branchCity = branchCity;
        this.branchName = branchName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBranchCity() {
        return branchCity;
    }

    public void setBranchCity(String branchCity) {
        this.branchCity = branchCity;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
