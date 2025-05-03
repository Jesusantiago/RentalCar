package com.proyectofinal.car.repository;

import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BranchRepository branchRepository;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        branchRepository.deleteAll();

        Branch branch1 = new Branch();
        branch1.setName("AutoCon");
        branch1.setAddress("Av. Principal");
        branch1.setCity("Merida");
        branch1.setPhone(123456789);
        branchRepository.save(branch1);

        Branch branch2 = new Branch();
        branch2.setName("CarMax");
        branch2.setAddress("Calle Secundaria");
        branch2.setCity("Caracas");
        branch2.setPhone(987654321);
        branchRepository.save(branch2);

        Car car1 = new Car();
        car1.setBrand("Toyota");
        car1.setModel("Supra");
        car1.setCarYear(2020);
        car1.setLicensePlate("123-ABC");
        car1.setStatus(StatusCar.AVAILABLE);
        car1.setBranch(branch1);

        Car car2 = new Car();
        car2.setBrand("Honda");
        car2.setModel("Civic");
        car2.setCarYear(2018);
        car2.setLicensePlate("456-DEF");
        car2.setStatus(StatusCar.MAINTENANCE);
        car2.setBranch(branch2);

        Car car3 = new Car();
        car3.setBrand("Toyota");
        car3.setModel("Focus");
        car3.setCarYear(2019);
        car3.setLicensePlate("789-GHI");
        car3.setStatus(StatusCar.AVAILABLE);
        car3.setBranch(branch1);

        carRepository.saveAll(List.of(car1, car2, car3));

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
        car.setCarYear(2020);
        car.setLicensePlate("123-XYZ");
        car.setStatus(StatusCar.AVAILABLE);
        car.setBranch(branch1);
        carRepository.save(car);

        assertThat(car).isNotNull();
        assertThat(car.getCarId()).isNotNull();
        assertThat(car.getBrand()).isEqualTo("Toyota");
        assertThat(car.getModel()).isEqualTo("Yaris");
        assertThat(car.getCarYear()).isEqualTo(2020);
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
        Optional<Car> carOptional = carRepository.findCarByLicensePlate("123-ABC");
        assertThat(carOptional).isPresent();

        Car car = carOptional.get();
        car.setStatus(StatusCar.RENTAL);
        carRepository.save(car);

        assertThat(car.getStatus()).isEqualTo(StatusCar.RENTAL);
    }

    @Test
    void deleteCar() {
        Optional<Car> carOptional = carRepository.findCarByLicensePlate("123-ABC");
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
        Page<Car> result = carRepository.findAllByBrand("Toyota", pageable);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().size()).isEqualTo(2);

    }
}



//        Branch b2 = new Branch();
//        b2.setBranchId(2L);
//        b2.setName("MiAuto");
//        b2.setAddress("Av. Secundaria");
//        b2.setCity("Madrid");
//        b2.setPhone(987654321);
//        branchs.add(b2);

//
//        Rental r = new Rental();
//        r.setStartDate(ldt[0]);
//        r.setEndDate(ldt[1]);
//        r.setPrice(BigDecimal.valueOf(100));
//        r.setStatus(StatusRental.ACTIVE);
//        r.setClienteId(1L);
//        r.setBranchFrom(branchs.get(0));
//        r.setBranchTo(branchs.get(1));
//        r.setCar(c);



//@BeforeEach
//public void setUp() {
//    branchRepository.deleteAll();
//    carRepository.deleteAll();
//
//    Branch b1 = new Branch();
//    b1.setName("AutoCon");
//    b1.setAddress("Av. Principal");
//    b1.setCity("Merida");
//    b1.setPhone(123456789);
//    branchSaved = branchRepository.save(b1);
//
//    Car c = new Car();
//    c.setBrand("Toyota");
//    c.setModel("Supra");
//    c.setCarYear(2020);
//    c.setLicensePlate("123-ABC");
//    c.setStatus(StatusCar.AVAILABLE);
//    c.setBranch(branchSaved);
//    carSaved = carRepository.save(c);
//}