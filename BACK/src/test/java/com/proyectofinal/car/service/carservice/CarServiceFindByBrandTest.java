package com.proyectofinal.car.service.carservice;

import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.exception.CarNotFoundException;
import com.proyectofinal.car.exception.NoCarsFoundByBrandException;
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
public class CarServiceFindByBrandTest {

    @Autowired
    private CarService carService;

    private String badBrand = "BMW";
    private String goodBrand = "Honda";

    @Test
    void carService_testFindCarByBrand() {
        Page<CarPreviewDTO> result = null;

        try {
            result = carService.searchAvailableCars(0, 10 , null, null, null, goodBrand, null);
        } catch (CarNotFoundException e) {
            fail("Exception was thrown " + e.getMessage());
        }

        assertThat(result.getTotalElements()).isEqualTo(3);

        assertThat(result.getContent()).allMatch(e -> e.getBrand().equals(goodBrand));
    }

    @Test
    void carService_shouldReturnExceptionWhenNoCarsFoundByBrand() {
        assertThrows(CarNotFoundException.class, () ->
                carService.searchAvailableCars(0, 10 , null, null, badBrand, null, null));
    }

    @Test
    void carService_shouldReturnExceptionWhenNoCarsFoundByBrand_withMessage() {
        CarNotFoundException exc = assertThrows(CarNotFoundException.class, () ->
                carService.searchAvailableCars(0, 10 , null, null, badBrand, null, null));
        assertThat(exc.getMessage()).isEqualTo("Cars not found");
    }
}
