package com.proyectofinal.car.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branch_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String adrress;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private int phone;


    @OneToMany( mappedBy = "branchId", cascade = CascadeType.ALL)
    private List<Car> cars;

    //GETTERS AND SETTERS

    public Long getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Long branch_id) {
        this.branch_id = branch_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdrress() {
        return adrress;
    }

    public void setAdrress(String adrress) {
        this.adrress = adrress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
