package com.proyectofinal.car.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Car> cars;

    @OneToMany(mappedBy = "branchFrom")
//    @JsonManagedReference
    private List<Rental> rentalsFrom;

    @OneToMany(mappedBy = "branchTo")
//    @JsonManagedReference
    private List<Rental> rentalsTo;

//GETTERS AND SETTERS

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branch_id) {
        this.branchId = branch_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adrress) {
        this.address = adrress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Rental> getRentalsFrom() {
        return rentalsFrom;
    }

    public void setRentalsFrom(List<Rental> rentalsFrom) {
        this.rentalsFrom = rentalsFrom;
    }

    public List<Rental> getRentalsTo() {
        return rentalsTo;
    }

    public void setRentalsTo(List<Rental> rentalsTo) {
        this.rentalsTo = rentalsTo;
    }
}
