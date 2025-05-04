package com.proyectofinal.car.repository;

import com.proyectofinal.car.enums.StatusRental;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import com.proyectofinal.car.model.Rental;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class RentalRepositoryTest {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void findAll() {
        List<Rental> rentals = rentalRepository.findAll();
        assertThat(rentals).isNotNull();
        assertThat(rentals).isNotEmpty();
        assertThat(rentals.size()).isEqualTo(6);
    }

    @Test
    void testFindByClienteId() {
        List<Rental> rentals = rentalRepository.findByClientId(101L);
        assertThat(rentals).hasSize(1);
        assertThat(rentals.get(0).getCar().getLicensePlate()).isEqualTo("123-ABC");
    }

    @Test
    void testFindByStatus() {
        List<Rental> activeRentals = rentalRepository.findByStatus(StatusRental.ACTIVE);
        assertThat(activeRentals).hasSize(3);
    }

    @Test
    void testFindByStartDateBetween() {
        LocalDateTime from = LocalDate.of(2025, 5, 1).atStartOfDay();
        LocalDateTime to = LocalDate.of(2025, 5, 4).atStartOfDay();
        List<Rental> rentals = rentalRepository.findByStartDateBetween(from, to);
        assertThat(rentals).hasSize(3);
    }

    @Test
    void testFindByBranchFrom() {
        Branch branch = branchRepository.findById(1L).orElseThrow();
        List<Rental> rentals = rentalRepository.findByBranchFrom(branch);
        assertThat(rentals).hasSize(3); // rentals con branchFrom_id = 1
    }

    @Test
    void testFindByCar() {
        Car car = carRepository.findById(1L).orElseThrow();
        List<Rental> rentals = rentalRepository.findByCar(car);
        assertThat(rentals).hasSize(1);
        assertThat(rentals.get(0).getClientId()).isEqualTo(101L);
    }

    @Test
    void testFindRentalsActiveOnDate() {
        LocalDateTime date = LocalDate.of(2025, 5, 6).atStartOfDay(); // Este día está dentro de varios rentals activos
        List<Rental> rentals = rentalRepository.findRentalsActiveOnDate(date);
        assertThat(rentals).hasSizeGreaterThanOrEqualTo(4);
    }

    @Test
    void testFindOverlappingRentals() {
        Car car = carRepository.findById(1L).orElseThrow();
        LocalDateTime newStart = LocalDate.of(2025, 5, 4).atStartOfDay();
        LocalDateTime newEnd = LocalDate.of(2025, 5, 10).atStartOfDay();
        List<Rental> overlapping = rentalRepository.findOverlappingRentals(car, newStart, newEnd);
        assertThat(overlapping).hasSize(1);
        assertThat(overlapping.get(0).getClientId()).isEqualTo(101L);
    }
}
