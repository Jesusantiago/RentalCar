package com.proyectofinal.car.service;

import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.exception.NoCarsFoundByBrandException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
public class CarServiceFindCarByBrandTest {

    @Autowired
    private CarService carService;

    private String badBrand = "BMW";
    private String goodBrand = "Honda";

    @Test
    void carService_testFindCarByBrand() {
        Page<CarPreviewDTO> result = assertDoesNotThrow(() ->
                carService.findAllByBrand(0, 10 , null, null, goodBrand));

        assertThat(result.getTotalElements()).isEqualTo(4);

        assertThat(result.map(car ->
                car.getBrand() == goodBrand));
    }

    @Test
    void carService_shouldReturnExceptionWhenNoCarsFoundByBrand() {
        assertThrows(NoCarsFoundByBrandException.class, () ->
                carService.findAllByBrand(0, 10 , null, null, badBrand));
    }

    @Test
    void carService_shouldReturnExceptionWhenNoCarsFoundByBrand_withMessage() {
        NoCarsFoundByBrandException exc = assertThrows(NoCarsFoundByBrandException.class, () ->
                carService.findAllByBrand(0, 10 , null, null, badBrand));
        assertThat(exc.getMessage()).isEqualTo("Cars with brand " + badBrand + " no found");
    }
}
