package com.proyectofinal.car.service.carservice;

import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.exception.NoCarsFoundByModelException;
import com.proyectofinal.car.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
public class CarServiceFindByModelTest {

    @Autowired
    private CarService carService;

    @Test
    void carService_findCarByModel() {
        Page<CarPreviewDTO> result = null;

        try{
            result = carService.findAllByModel(0, 10, null, null, "Supra");
        } catch (NoCarsFoundByModelException e) {
            fail("Exception was thrown" + e.getMessage());
        }

        assertThat(result.getTotalElements()).isEqualTo(2);

        assertThat(result.getContent()).allMatch(e -> e.getModel().equals("Supra"));
        assertThat(result.getContent()).allMatch(e -> e.getBrand().equals("Toyota"));
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
