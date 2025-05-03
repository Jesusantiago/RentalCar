package com.proyectofinal.car.model;

import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.enums.StatusRental;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CarTest {

    @Test
    void createNewCar(){
        StatusCar statusCar = StatusCar.AVAILABLE;
        StatusRental statusRental = StatusRental.ACTIVE;

        Branch branch = new Branch();
        branch.setName("AutoCon");
        branch.setAddress("Av principal");
        branch.setCity("Merida");
        branch.setPhone(123456789);

        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Machito");
        car.setCarYear(2020);
        car.setLicensePlate("MPD345");
        car.setStatus(statusCar);
        car.setBranch(branch);

        List<Rental> rentals = new ArrayList<>();
        Rental rental = new Rental();
        rental.setStartDate(LocalDateTime.now());
        rental.setPrice(BigDecimal.valueOf(100));
        rental.setStatus(statusRental);
        rental.setCar(car);

        rentals.add(rental);

        car.setListRentals(rentals);

        assertThat("Toyota").isEqualTo(car.getBrand());
        assertThat("Machito").isEqualTo(car.getModel());
        assertThat(2020).isEqualTo(car.getYear());
        assertThat("MPD345").isEqualTo(car.getLicensePlate());
        assertThat(car.getStatus()).isEqualTo(statusCar);

        assertThat(car.getBranch()).isNotNull();
        assertThat("AutoCon").isEqualTo(car.getBranch().getName());

        assertThat(car.getListRentals()).hasSize(1);
        assertThat(car).isEqualTo(car.getListRentals().get(0).getCar());
    }
}
