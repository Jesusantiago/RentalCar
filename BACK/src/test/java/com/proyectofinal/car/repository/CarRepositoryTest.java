package com.proyectofinal.car.repository;

import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CarRepositoryTest {

    LocalDateTime[] ldt = new LocalDateTime[]{LocalDateTime.now(), LocalDateTime.now().plusDays(1)};

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BranchRepository branchRepository;

    private Branch branchSaved;
    private Car carSaved;

    @BeforeEach
    public void setUp() {
        branchRepository.deleteAll();
        carRepository.deleteAll();

        Branch b1 = new Branch();
        b1.setName("AutoCon");
        b1.setAddress("Av. Principal");
        b1.setCity("Merida");
        b1.setPhone(123456789);
        branchSaved = branchRepository.save(b1);

        Car c = new Car();
        c.setBrand("Toyota");
        c.setModel("Supra");
        c.setCarYear(2020);
        c.setLicensePlate("123-ABC");
        c.setStatus(StatusCar.AVAILABLE);
        c.setBranch(branchSaved);
        carSaved = carRepository.save(c);
    }

    @Test
    void returnACarByItsId() {
        assertThat(carSaved).isNotNull();
        assertThat(carSaved.getCarId()).isNotNull();
    }

    @Test
    void saveACarInDbWithRepository() {

        assertThat(carSaved).isNotNull();
        assertThat(carSaved.getCarId()).isNotNull();
        assertThat(carSaved.getBrand()).isEqualTo("Toyota");
        assertThat(carSaved.getModel()).isEqualTo("Supra");
        assertThat(carSaved.getCarYear()).isEqualTo(2020);
        assertThat(carSaved.getLicensePlate()).isEqualTo("123-ABC");
        assertThat(carSaved.getStatus()).isEqualTo(StatusCar.AVAILABLE);
        assertThat(carSaved.getBranch()).isEqualTo(branchSaved);
    }

    @Test
    void returnABranchByCarId() {
        assertThat(carSaved).isNotNull();
        assertThat(carSaved.getCarId()).isNotNull();
        assertThat(carSaved.getBranch()).isNotNull();
        assertThat(carSaved.getBranch().getName()).isEqualTo("AutoCon");
    }

    @Test
    void updateStatusCar() {
        carSaved.setStatus(StatusCar.RENTAL);
        Car carUpdated = carRepository.save(carSaved);

        assertThat(carUpdated.getStatus()).isEqualTo(StatusCar.RENTAL);
    }

    @Test
    void deleteCar() {
        carRepository.delete(carSaved);
        Optional<Car> carDeleted = carRepository.findById(carSaved.getCarId());

        assertThat(carDeleted).isNotPresent();
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