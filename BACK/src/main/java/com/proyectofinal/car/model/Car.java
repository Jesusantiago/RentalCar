package com.proyectofinal.car.model;

import com.proyectofinal.car.enums.StatusCar;
import jakarta.persistence.*;

@Entity
@Table
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long car_id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private StatusCar status;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch_id;

    // GETTERS AND SETTERS

    public Long getCar_id() {
        return car_id;
    }

    public void setCar_id(Long car_id) {
        this.car_id = car_id;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public StatusCar getStatus() {
        return status;
    }

    public void setStatus(StatusCar status) {
        this.status = status;
    }

    public Branch getBranchId() {
        return branch_id;
    }

    public void setBranchId(Branch branchId) {
        this.branch_id = branchId;
    }
}
