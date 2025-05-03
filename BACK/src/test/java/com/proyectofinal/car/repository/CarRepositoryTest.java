package com.proyectofinal.car.repository;

import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class CarRepositoryTest {

    @Autowired
    private  CarRepository carRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Test
    void findAll() {
        carRepository.findAll().forEach(car -> System.out.println(">>" + car.getLicensePlate()));

    }

    @Test
    void returnACarByItsId() {
        Optional<Car> carOptional = carRepository.findCarByLicensePlate("123-ABC");
        assertThat(carOptional).isPresent();

        Car car = carOptional.get();
        assertThat(car.getCarId()).isNotNull();
        assertThat(car.getBrand()).isEqualTo("Toyota");
    }

    @Test
    void saveACarInDbWithRepository() {
        Branch branch1 = new Branch();
        branch1.setName("AutoCon");
        branch1.setAddress("Av. Principal");
        branch1.setCity("Merida");
        branch1.setPhone(123456789);
        branchRepository.save(branch1);

        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Yaris");
        car.setCarYear(2021);
        car.setLicensePlate("123-XYZ");
        car.setStatus(StatusCar.AVAILABLE);
        car.setBranch(branch1);
        carRepository.save(car);

        assertThat(car).isNotNull();
        assertThat(car.getCarId()).isNotNull();
        assertThat(car.getBrand()).isEqualTo("Toyota");
        assertThat(car.getModel()).isEqualTo("Yaris");
        assertThat(car.getCarYear()).isEqualTo(2021);
        assertThat(car.getLicensePlate()).isEqualTo("123-XYZ");
        assertThat(car.getStatus()).isEqualTo(StatusCar.AVAILABLE);
        assertThat(car.getBranch()).isEqualTo(branch1);
    }

    @Test
    void returnABranchByCarId() {
        Optional<Car> carOptional = carRepository.findCarByLicensePlate("123-ABC");
        assertThat(carOptional).isPresent();

        Car car = carOptional.get();
        assertThat(car.getCarId()).isNotNull();
        assertThat(car.getBranch()).isNotNull();
        assertThat(car.getBranch().getName()).isEqualTo("AutoCon");
    }

    @Test
    void updateStatusCar() {
        Optional<Car> carOptional = carRepository.findCarByLicensePlate("458-GHI");
        assertThat(carOptional).isPresent();

        Car car = carOptional.get();
        car.setStatus(StatusCar.RENTAL);
        carRepository.save(car);

        assertThat(car.getStatus()).isEqualTo(StatusCar.RENTAL);
    }

    @Test
    void deleteCar() {
        Optional<Car> carOptional = carRepository.findCarByLicensePlate("789-GHI");
        assertThat(carOptional).isPresent();

        Car car = carOptional.get();
        carRepository.delete(car);
        Optional<Car> carDeleted = carRepository.findById(car.getCarId());

        assertThat(carDeleted).isNotPresent();
    }

    @Test
    void returnACarByLicensePlate() {
        Optional<Car> carOptional = carRepository.findCarByLicensePlate("123-ABC");
        assertThat(carOptional).isPresent();

        Car car = carOptional.get();
        assertThat(car).isNotNull();
        assertThat(car.getLicensePlate()).isEqualTo("123-ABC");
        assertThat(car.getModel()).isEqualTo("Supra");

    }

    @Test
    void returnAllCarsByBranch() {
        Optional<Car> carOptional = carRepository.findCarByLicensePlate("123-ABC");
        assertThat(carOptional).isPresent();

        Car car = carOptional.get();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Car> result = carRepository.findCarsByBranch(car.getBranch(), pageable);
        System.out.println(result.getContent().size());
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().get(0).getBrand()).isEqualTo("Toyota");
    }

    @Test
    void returnAllCarsByStatus() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Car> result = carRepository.findAllByStatus(StatusCar.AVAILABLE, pageable);
        System.out.println(result.getTotalElements());

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().get(0).getModel()).isEqualTo("Supra");

    }

    @Test
    void returnAllCarsByBranchAndStatus() {
        Optional<Car> carOptional = carRepository.findCarByLicensePlate("123-ABC");
        assertThat(carOptional).isPresent();

        Pageable pageable = PageRequest.of(0, 10);
        Page<Car> cars = carRepository.findAllByBranchAndStatus(carOptional.get().getBranch(), StatusCar.AVAILABLE, pageable);

        assertThat(cars.getContent()).isNotEmpty();
        assertThat(cars.getContent().size()).isEqualTo(2);
    }

    @Test
    void returnAllCarsByBrand() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Car> result = carRepository.findAllByBrand("Honda", pageable);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().size()).isEqualTo(4);

    }

    @Test
    void returnAllCarsByModel() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Car> result = carRepository.findAllByModel("Supra", pageable);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().size()).isEqualTo(2);
    }

    @Test
    void returnAllCarsByYear() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Car> result = carRepository.findAllByCarYear(2020, pageable);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().size()).isEqualTo(4);
    }

    @Test
    void returnAllCarsByBranchAndModel() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Car> result = carRepository.findAllByBrandAndModel("Toyota", "Supra", pageable);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().size()).isEqualTo(2);
    }

    @Test
    void returnAllByBranchAndBrandAndModel() {
        Optional<Car> carOptional = carRepository.findCarByLicensePlate("123-ABC");
        assertThat(carOptional).isPresent();

        Pageable pageable = PageRequest.of(0, 10);
        Page<Car> result = carRepository.findAllByBranchAndBrandAndModel(carOptional.get().getBranch(), "Toyota", "Supra", pageable);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    void deleteCarByLicensePlate() {
        carRepository.deleteCarByLicensePlate("791-KLM");
        Optional<Car> carOptional = carRepository.findCarByLicensePlate("791-KLM");
        assertThat(carOptional).isNotPresent();

    }
}
