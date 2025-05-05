package com.proyectofinal.car.service.carservice;

import com.proyectofinal.car.dto.CarDetailsDTO;
import com.proyectofinal.car.exception.CarNotFoundException;
import com.proyectofinal.car.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
public class CarServiceFindByIdTest {

    @Autowired
    private CarService carService;

    @Test
    void getCarById_noShouldNullOrEmpty() {
        CarDetailsDTO car = carService.getCarById(1L);
        assertThat(car).isNotNull();
    }

    @Test
    void getCarById_shouldFailTest(){
        assertThrows(CarNotFoundException.class, () -> carService.getCarById(99L));
    }
    @Test
    void getCarById_shouldFailTest_WithMessage(){
        Long id = 99L;
        CarNotFoundException exc = assertThrows(CarNotFoundException.class, () -> carService.getCarById(id));
        assertEquals(exc.getMessage(), "Car with id " + id + " not found");
    }

    @Test
    void getCarById_shouldReturnCar() {
        CarDetailsDTO car = carService.getCarById(1L);
        assertThat(car).isNotNull();
        assertThat(car.getBrand()).isEqualTo("Toyota");
        assertThat(car.getModel()).isEqualTo("Supra");
        assertThat(car.getCarYear()).isEqualTo(2020);
        assertThat(car.getLicensePlate()).isEqualTo("123-ABC");
        assertThat(car.getBranch().getName()).isEqualTo("AutoCon");
        assertThat(car.getBranch().getAddress()).isEqualTo("Av. Principal 123");
        assertThat(car.getBranch().getPhone()).isEqualTo("123456789");
    }

}
