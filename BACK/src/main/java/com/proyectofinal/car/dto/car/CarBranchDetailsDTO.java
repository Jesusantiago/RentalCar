package com.proyectofinal.car.dto.car;

public class CarBranchDetailsDTO {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String phone;

    public CarBranchDetailsDTO(Long id, String name, String address, String city, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

}
