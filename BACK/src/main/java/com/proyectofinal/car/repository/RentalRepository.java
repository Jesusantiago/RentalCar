package com.proyectofinal.car.repository;

import com.proyectofinal.car.enums.StatusRental;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import com.proyectofinal.car.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByClientId(Long cliendId);
    List<Rental> findByStatus(StatusRental status);
    List<Rental> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Rental> findByBranchFrom(Branch from);
    List<Rental> findByCar(Car car);

    @Query("SELECT r FROM  Rental r Where :date BETWEEN r.startDate AND r.endDate")
    List<Rental> findRentalsActiveOnDate(@Param("date") LocalDateTime date);

    @Query("SELECT r FROM Rental r WHERE r.car = :car AND r.startDate < :endDate AND r.endDate > :startDate")
    List<Rental> findOverlappingRentals(
            @Param("car") Car car,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}

