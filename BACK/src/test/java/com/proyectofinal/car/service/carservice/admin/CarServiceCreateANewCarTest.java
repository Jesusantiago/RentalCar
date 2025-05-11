package com.proyectofinal.car.service.carservice.admin;

import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.dto.CarRegisterDTO;
import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.exception.CarNotFoundException;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.repository.BranchRepository;
import com.proyectofinal.car.repository.CarRepository;
import com.proyectofinal.car.service.CarService;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class CarServiceCreateANewCarTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BranchRepository branchRepository;

    private Branch testBranch;

    @BeforeEach
    void setUp(){
        testBranch = carRepository.findById(6L).get().getBranch();
    }

    @Test
    void createNewCar() {
        CarRegisterDTO dto = new CarRegisterDTO();
        dto.setBrand("Tesla");
        dto.setModel("Model Y");
        dto.setCarYear(2025);
        dto.setLicensePlate("MNO-123");
        dto.setStatusCar(StatusCar.AVAILABLE);
        dto.setBranch(1L);

        CarRegisterDTO createdCar = carService.createCarFromDTO(dto);

        assertNotNull(createdCar);
        assertEquals(createdCar.getBrand(), "Tesla");
        assertEquals(createdCar.getModel(), "Model Y");
        assertEquals(createdCar.getCarYear(), 2025);
        assertEquals(createdCar.getLicensePlate(), "MNO-123");
        assertEquals(createdCar.getStatusCar(), StatusCar.AVAILABLE);
        assertEquals(createdCar.getBranch(), 1L);

        Long carIdForDelete = carRepository.findCarByLicensePlate("MNO-123").get().getCarId();
        carRepository.deleteById(carIdForDelete);
    }

    @Test
    void shouldSearchANewCar() {
        Page<CarPreviewDTO> carExist =  carService.searchAvailableCarsForUser(
                0,10,null, null, "Honda", null,null, null
        );
        CarPreviewDTO carDTO = carExist.getContent().get(0);
    }

    @Test
    void shouldThrowExceptionWhenLicensePlateExists(){
        CarRegisterDTO duplicateCar = new CarRegisterDTO();
        duplicateCar.setBrand("Tesla");
        duplicateCar.setModel("Model X");
        duplicateCar.setCarYear(2025);
        duplicateCar.setLicensePlate("123-ABC");
        duplicateCar.setStatusCar(StatusCar.AVAILABLE);
        duplicateCar.setBranch(1L);

        assertThrows(CarNotFoundException.class, () -> carService.createCarFromDTO(duplicateCar));
    }

}
