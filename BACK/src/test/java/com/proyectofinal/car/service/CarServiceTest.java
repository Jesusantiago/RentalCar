package com.proyectofinal.car.service;

import com.proyectofinal.car.dto.CarDetailsDTO;
import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.exception.CarNotFoundException;
import com.proyectofinal.car.exception.NoAvailableCarsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarService carService;
    private Executable CarNotFoundException;

    @Test
    void getAvailableCars_shouldNotBeEmpty() {
        Page<CarPreviewDTO> result = carService.getAvailableCars(0,10, null, null);
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    void getAvailableCars_shouldReturnListOfCars() {
        Page<CarPreviewDTO> result = carService.getAvailableCars(0,10, null, null);
        assertThat(result.getContent().size()).isEqualTo(8);
    }

    @Test
    void getAvailableCars_checkDataWithCarDTO(){
        Page<CarPreviewDTO> result = carService.getAvailableCars(0,10, null, null);
        assertThat(result.getContent().get(0).getModel()).isEqualTo("Supra");
        assertThat(result.getContent().get(0).getBrand()).isEqualTo("Toyota");
        assertThat(result.getContent().get(0).getBranchCity()).isEqualTo("Merida");
        assertThat(result.getContent().get(0).getBranchName()).isEqualTo("AutoCon");
    }

    @Test
    void getAvailableCars__shouldReturnEmptyPage_WhenPageNumberIsTooHigh(){
        assertThrows(NoAvailableCarsException.class, () -> carService.getAvailableCars(1,10, null, null));
    }

    @Test
    void getAvailableCars_checkDataWithSort(){
        Page<CarPreviewDTO> result = carService.getAvailableCars(0, 10, "model", "asc" );
        List<String> actualModels = result.getContent()
                .stream()
                .map(CarPreviewDTO::getModel)
                .collect(Collectors.toList());

        List<String> expectedSortedModels = new ArrayList<>(actualModels);
        expectedSortedModels.sort(Comparator.naturalOrder());

        assertThat(actualModels).isEqualTo(expectedSortedModels);
    }

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
