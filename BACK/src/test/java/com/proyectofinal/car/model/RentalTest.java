package com.proyectofinal.car.model;

import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.enums.StatusRental;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
public class RentalTest {

    @Test
    void RentalTest() {
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Machito");
        car.setCarYear(2020);
        car.setLicensePlate("MPD345");
        car.setStatus(StatusCar.AVAILABLE);

        Branch b1 = new Branch();
        b1.setName("AutoCon");
        b1.setAddress("Av. Principal");
        b1.setCity("Merida");
        b1.setPhone(123456789);

        Branch b2 = new Branch();
        b2.setName("MiAuto");
        b2.setAddress("Av. Secundaria");
        b2.setCity("Madrid");
        b2.setPhone(987654321);

        car.setBranch(b1);
        LocalDateTime dateToday = LocalDateTime.now();
        LocalDateTime dateTomorrow = LocalDateTime.now().plusDays(1);

        Rental r = new Rental();
        r.setRentalId(1L);
        r.setStartDate(dateToday);
        r.setEndDate(dateTomorrow);
        r.setPrice(BigDecimal.valueOf(100));
        r.setStatus(StatusRental.ACTIVE);
        r.setClientId(1L);
        r.setCar(car);
        r.setBranchFrom(b1);
        r.setBranchTo(b2);

        assertThat(r.getRentalId()).isEqualTo(1L);
        assertThat(r.getStartDate()).isEqualTo(dateToday);
        assertThat(r.getEndDate()).isEqualTo(dateTomorrow);
        assertThat(r.getStatus()).isEqualTo(StatusRental.ACTIVE);
        assertThat(r.getClientId()).isEqualTo(1L);
        assertThat(r.getCar()).isNotNull();
        assertThat(r.getCar().getBrand()).isEqualTo("Toyota");
        assertThat(r.getBranchFrom()).isNotNull();
        assertThat(r.getBranchFrom().getName()).isEqualTo("AutoCon");
        assertThat(r.getBranchTo()).isNotNull();
        assertThat(r.getBranchTo().getName()).isEqualTo("MiAuto");

    }
}
