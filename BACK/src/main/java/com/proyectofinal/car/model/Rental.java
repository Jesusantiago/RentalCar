package com.proyectofinal.car.model;

import com.proyectofinal.car.enums.StatusRental;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusRental status;

    @ManyToOne
    @JoinColumn(name ="car_id", nullable = false)
    private Car car;

    private Long clientId;

    @ManyToOne
    @JoinColumn(name = "branch_from_id")
    private Branch branchFrom;

    @ManyToOne
    @JoinColumn(name = "branch_to_id")
    private Branch branchTo;

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public StatusRental getStatus() {
        return status;
    }

    public void setStatus(StatusRental status) {
        this.status = status;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clienteId) {
        this.clientId = clienteId;
    }

    public Branch getBranchFrom() {
        return branchFrom;
    }

    public void setBranchFrom(Branch branchFrom) {
        this.branchFrom = branchFrom;
    }

    public Branch getBranchTo() {
        return branchTo;
    }

    public void setBranchTo(Branch branchTo) {
        this.branchTo = branchTo;
    }
}

