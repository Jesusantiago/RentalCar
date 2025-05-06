package com.proyectofinal.car.service.carservice;


import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.exception.CarNotFoundException;
import com.proyectofinal.car.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
public class CarServiceFindByCarYearTest {

    @Autowired
    private CarService carService;

    private int badYear = 2000;
    private int goodYear = 2020;

    @Test
    void carService_findByCarYear() {
        Page<CarPreviewDTO> result = null;

        try {
            result = carService.searchAvailableCars(0, 10 , null, null, null, null, null, goodYear);
        } catch (CarNotFoundException e) {
            fail("Exception was thrown: " + e.getMessage());
        }

        assertThat(result.getTotalElements()).isEqualTo(4);
    }

    @Test
    void carService_shouldReturnExceptionWhenNoCarsFoundByCarYear() {
        assertThrows(CarNotFoundException.class, () -> {
            carService.searchAvailableCars(0, 10 , null, null, null, null, null, badYear);
        });
    }

    @Test
    void carService_shouldReturnExceptionWhenNoCarsFoundByYear_withMessage() {
        CarNotFoundException exc = assertThrows(CarNotFoundException.class, () ->
                carService.searchAvailableCars(0, 10 , null, null, null, null, null, badYear));
        assertThat(exc.getMessage()).isEqualTo("Cars not found");
    }
}
