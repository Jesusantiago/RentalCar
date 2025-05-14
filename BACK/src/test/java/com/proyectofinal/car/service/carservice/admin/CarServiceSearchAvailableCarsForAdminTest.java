package com.proyectofinal.car.service.carservice.admin;

import com.proyectofinal.car.dto.car.CarPreviewDTO;
import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.exception.CarNotFoundException;
import com.proyectofinal.car.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
public class CarServiceSearchAvailableCarsForAdminTest {

    @Autowired
    private CarService carService;

    @Test
    void shouldReturnThreeFordCarsWithoutExtraFilters() {
        Page<CarPreviewDTO> cars = carService.searchAvailableCarsForAdmin(
                0, 10, null, null, "Ford",
                null, null, null, null, null
        );

        assertThat(cars.getTotalElements()).isEqualTo(3);
    }

    @Test
    void shouldReturnTwoToyotaSuprasSortedByYear() {
        Page<CarPreviewDTO> cars = carService.searchAvailableCarsForAdmin(
                0, 10, "carYear", null, "Toyota", "Supra",
                null, null, null, null
        );

        assertThat(cars.getTotalElements()).isEqualTo(2);
    }

    @Test
    void shouldReturnThreeAvailableHondaCars() {
        Page<CarPreviewDTO> cars = carService.searchAvailableCarsForAdmin(
                0, 10, null, null, "Honda", null,
                null, null, StatusCar.AVAILABLE, null
        );

        assertThat(cars.getTotalElements()).isEqualTo(3);
    }

    @Test
    void shouldThrowExceptionWhenNoCarsFound() {

        assertThrows(CarNotFoundException.class, () ->
                carService.searchAvailableCarsForAdmin(0, 10, null,
                        null, "Tesla", null,
                null, null, StatusCar.AVAILABLE, null));
    }
}
