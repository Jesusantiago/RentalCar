package com.proyectofinal.car.service;

import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.exception.NoCarsFoundByModelException;
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
public class CarServiceFindCarByModelTest {

    @Autowired
    private CarService carService;

    @Test
    void carService_findCarByModel() {
        Page<CarPreviewDTO> result =  assertDoesNotThrow(() ->
                carService.findAllByModel(0, 10, null, null, "Supra"));

        assertThat(result.getTotalElements()).isEqualTo(2);

        CarPreviewDTO car1 = result.getContent().get(0);
        CarPreviewDTO car2 = result.getContent().get(1);

        assertThat(car1.getModel()).isEqualTo("Supra");
        assertThat(car2.getModel()).isEqualTo("Supra");
        assertThat(car1.getBrand()).isEqualTo("Toyota");
        assertThat(car2.getBrand()).isEqualTo("Toyota");
    }

    @Test
    void carService_shouldReturnExceptionWhenNoCarsFoundByModel() {
        assertThrows(NoCarsFoundByModelException.class, () -> {
            carService.findAllByModel(0, 10, null, null, "Cronos");
        });
    }

    @Test
    void carService_shouldReturnExceptionWhenNoCarsFoundByBrand_WithMessage() {
        String model = "Cronos";
        NoCarsFoundByModelException exc = assertThrows(NoCarsFoundByModelException.class, () ->
                carService.findAllByModel(0, 10, null, null, model));
        assertThat(exc.getMessage()).isEqualTo("Car with model " + model + " not found");
    }

    @Test
    void carService_shouldReturnCarsSortedByBrandDesc() {
        Page<CarPreviewDTO> result = carService.findAllByModel(0, 10, "licensePlate", "desc", "Supra");

        List<CarPreviewDTO> content = result.getContent();
        assertThat(content).isSortedAccordingTo(Comparator.comparing(CarPreviewDTO::getBrand).reversed());
    }


}
