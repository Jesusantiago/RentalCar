package com.proyectofinal.car.dto.rental;

import com.proyectofinal.car.enums.StatusRental;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RentalRequestDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private StatusRental status;
    private Long carId;
    private Long clientId;

    public RentalRequestDTO(LocalDateTime startDate, LocalDateTime endDate, BigDecimal price, StatusRental status, Long carId, Long clientId, Long branchFromId, Long branchToId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.status = status;
        this.carId = carId;
        this.clientId = clientId;
        this.branchFromId = branchFromId;
        this.branchToId = branchToId;
    }

    private Long branchFromId;
    private Long branchToId;

    public RentalRequestDTO() {}

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

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getBranchFromId() {
        return branchFromId;
    }

    public void setBranchFromId(Long branchFromId) {
        this.branchFromId = branchFromId;
    }

    public Long getBranchToId() {
        return branchToId;
    }

    public void setBranchToId(Long branchToId) {
        this.branchToId = branchToId;
    }
}
