package com.proyectofinal.car.service.carservice.admin;

import com.proyectofinal.car.dto.CarDetailsDTO;
import com.proyectofinal.car.dto.CarUpdateDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
public class CarServiceUpdateACarTest {

    @Autowired
    private CarService carService;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BranchRepository branchRepository;

    private CarUpdateDTO CarUpdate(){
        CarUpdateDTO carUpdateDTO = new CarUpdateDTO();
        carUpdateDTO.setBrand("Fiat");
        carUpdateDTO.setModel("Cronos");
        carUpdateDTO.setCarYear(2020);
        carUpdateDTO.setLicensePlate("EFG-159");
        carUpdateDTO.setBranchId(1l);
        carUpdateDTO.setStatusCar(StatusCar.RENTAL);

        return carUpdateDTO;
    }

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
    void updateCar() {
        Long carId = savedCar();

        CarDetailsDTO updatedCar = carService.updateACar(carId, CarUpdate());

        assertNotNull(updatedCar);

        Car newCar = carRepository.findById(carId).get();
        assertThat(newCar.getBrand()).isEqualTo("Fiat");
        assertThat(newCar.getModel()).isEqualTo("Cronos");
        assertThat(newCar.getCarYear()).isEqualTo(2020);
        assertThat(newCar.getLicensePlate()).isEqualTo("EFG-159");
        assertThat(newCar.getBranch().getBranchId()).isEqualTo(1l);
        assertThat(newCar.getStatus() == StatusCar.RENTAL).isTrue();

        carService.deleteCar(carId);
    }

    @Test
    void updateCarNotFound() {
        assertThrows(CarNotFoundException.class, () -> carService.updateACar(99l, CarUpdate()));
    }

    @Test
    void updateCarInParts(){
        Long carId = savedCar();


        CarUpdateDTO anotherCar = new CarUpdateDTO();
        anotherCar.setBrand("Bugatti");

        CarDetailsDTO updatedCar = carService.updateACar(carId, anotherCar);
        assertNotNull(updatedCar);
        Car newCar = carRepository.findById(carId).get();
        assertThat(newCar.getBrand()).isEqualTo("Bugatti");
        assertThat(newCar.getModel()).isEqualTo("Cruce");
        carService.deleteCar(carId);
    }
}
