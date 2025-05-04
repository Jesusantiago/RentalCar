package com.proyectofinal.car.service;

import com.proyectofinal.car.dto.CarPreviewDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarService carService;

    @Test
    void getAvailableCars_shouldNotBeEmpty() {
        Page<CarPreviewDTO> result = carService.getAvailableCars(0,10);
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    void getAvailableCars_shouldReturnListOfCars() {
        Page<CarPreviewDTO> result = carService.getAvailableCars(0,10);
        assertThat(result.getContent().size()).isEqualTo(8);
    }

    @Test
    void getAvailableCars_CheckDataWithCarDTO(){
        Page<CarPreviewDTO> result = carService.getAvailableCars(0,10);
        assertThat(result.getContent().get(0).getModel()).isEqualTo("Supra");
        assertThat(result.getContent().get(0).getBrand()).isEqualTo("Toyota");
        assertThat(result.getContent().get(0).getBranchCity()).isEqualTo("Merida");
        assertThat(result.getContent().get(0).getBranchName()).isEqualTo("AutoCon");
    }

    @Test
    void getAvailableCars__ShouldReturnEmptyPage_WhenPageNumberIsTooHigh(){
        Page<CarPreviewDTO> result = carService.getAvailableCars(1,10);
        assertThat(result).isEmpty();
    }


}
