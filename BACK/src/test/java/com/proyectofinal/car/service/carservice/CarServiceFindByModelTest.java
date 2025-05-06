package com.proyectofinal.car.service.carservice;

import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.exception.CarNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
public class CarServiceFindByModelTest {

    @Autowired
    private CarService carService;

    private String goodModel = "Supra";
    private String badModel = "Cronos";

    @Test
    void carService_findCarByModel() {
        Page<CarPreviewDTO> result = null;

        try{
            result = carService.searchAvailableCars(0, 10, null, null, goodModel, null, null);
        } catch (CarNotFoundException e) {
            fail("Exception was thrown" + e.getMessage());
        }

        assertThat(result.getTotalElements()).isEqualTo(1);

        assertThat(result.getContent()).allMatch(e -> e.getModel().equals("Supra"));
        assertThat(result.getContent()).allMatch(e -> e.getBrand().equals("Toyota"));
    }

    @Test
    void carService_shouldReturnExceptionWhenNoCarsFoundByModel() {
        assertThrows(CarNotFoundException.class, () -> {
            carService.searchAvailableCars(0, 10, null, null, badModel, null, null);
        });
    }

    @Test
    void carService_shouldReturnExceptionWhenNoCarsFoundByBrand_WithMessage() {
        CarNotFoundException exc = assertThrows(CarNotFoundException.class, () ->
                carService.searchAvailableCars(0, 10, null, null, badModel, null, null));
        assertThat(exc.getMessage()).isEqualTo("Cars not found");
    }

    @Test
    void carService_shouldReturnCarsSortedByBrandDesc() {
        Page<CarPreviewDTO> result =  carService.searchAvailableCars(0, 10, null, null, goodModel, null, null);

        List<CarPreviewDTO> content = result.getContent();
        assertThat(content).isSortedAccordingTo(Comparator.comparing(CarPreviewDTO::getBrand).reversed());
    }


}
