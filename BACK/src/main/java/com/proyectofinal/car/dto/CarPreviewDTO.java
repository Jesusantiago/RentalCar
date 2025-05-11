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

}
