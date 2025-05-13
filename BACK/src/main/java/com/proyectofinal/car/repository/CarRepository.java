package com.proyectofinal.car.repository;

import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
    Optional<Car> findCarByLicensePlate(String licensePlate);
    Page<Car> findCarsByBranch(Branch branch, Pageable pageable);
    Page<Car> findAllByStatus(StatusCar status, Pageable pageable);
    Page<Car> findAllByBranchAndStatus(Branch branch, StatusCar status, Pageable pageable);
    void deleteCarByLicensePlate(String licensePlate);

}
