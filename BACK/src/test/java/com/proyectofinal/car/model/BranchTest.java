package com.proyectofinal.car.model;

import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.enums.StatusRental;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BranchTest {

    @Test
    void newBranch() {
        List<Car> cars = new ArrayList<>();
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Machito");
        car.setCarYear(2020);
        car.setLicensePlate("MPD345");
        car.setStatus(StatusCar.AVAILABLE);
        cars.add(car);

        Branch b = new Branch();
        b.setName("AutoCon");
        b.setAddress("Av. Principal");
        b.setCity("Merida");
        b.setPhone("123456789");
        b.setCars(cars);

        car.setBranch(b);

        List<Rental> rentals = new ArrayList<>();

        Rental rental = new Rental();
        rental.setStartDate(LocalDateTime.now());
        rental.setPrice(BigDecimal.valueOf(100));
        rental.setStatus(StatusRental.ACTIVE);
        rental.setCar(car);

        rentals.add(rental);

        b.setRentalsFrom(rentals);
        b.setRentalsTo(rentals);

        assertThat("AutoCon").isEqualTo(b.getName());
        assertThat("Av. Principal").isEqualTo(b.getAddress());
        assertThat("Merida").isEqualTo(b.getCity());
        assertThat("123456789").isEqualTo(b.getPhone());

        assertThat(b.getCars()).isNotNull();
        assertThat("Toyota").isEqualTo(b.getCars().get(0).getBrand());

        assertThat(b.getRentalsFrom()).isNotNull();
        assertThat(BigDecimal.valueOf(100)).isEqualTo(b.getRentalsFrom().get(0).getPrice());

        assertThat(b.getRentalsTo()).isNotNull();
        assertThat(BigDecimal.valueOf(100)).isEqualTo(b.getRentalsTo().get(0).getPrice());
    }
}
