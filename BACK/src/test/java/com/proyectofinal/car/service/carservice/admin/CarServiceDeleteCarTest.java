package com.proyectofinal.car.service.carservice.admin;

import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.exception.CarNotFoundException;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import com.proyectofinal.car.repository.BranchRepository;
import com.proyectofinal.car.repository.CarRepository;
import com.proyectofinal.car.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
public class CarServiceDeleteCarTest {

    @Autowired
    private CarService carService;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CarRepository carRepository;

    private Long savedCar(){
        Branch branch = branchRepository.getById(1l);

        Car car = new Car();
        car.setBrand("Chevrolet");
        car.setModel("Cruce");
        car.setCarYear(2015);
        car.setLicensePlate("EFG-753");
        car.setStatus(StatusCar.AVAILABLE);
        car.setBranch(branch);
        Car carReady = carRepository.save(car);
        return carReady.getCarId();
    }

    @Test
    void deleteCar() {
        Long carId = savedCar();
        carService.deleteCar(carId);
        Optional<Car> carDeleted = carRepository.findById(carId);

        assertThat(carDeleted.isEmpty()).isTrue();



    }

    @Test
    void deleteCarNotFound() {
        assertThrows(CarNotFoundException.class, () -> carService.deleteCar(999L));
    }
}
