package com.proyectofinal.car.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyectofinal.car.enums.StatusCar;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int carYear;

    @Column(nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusCar status;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonBackReference
    private Branch branch;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Rental> listRentals;

    public Long getCarId() {
        return carId;
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

    public void setCarYear(int year) {
        this.carYear = year;
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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch_id) {
        this.branch = branch_id;
    }

    public List<Rental> getListRentals() {
        return listRentals;
    }

    public void setListRentals(List<Rental> rentals) {
        this.listRentals = rentals;
    }
}
