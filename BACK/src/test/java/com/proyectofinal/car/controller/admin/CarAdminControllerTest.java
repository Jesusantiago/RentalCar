package com.proyectofinal.car.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectofinal.car.dto.car.CarRegisterDTO;
import com.proyectofinal.car.dto.car.CarUpdateDTO;
import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import com.proyectofinal.car.repository.BranchRepository;
import com.proyectofinal.car.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ActiveProfiles("test")
@SpringBootTest(properties = "spring.datasource.initialize=false")
@AutoConfigureMockMvc
public class CarAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BranchRepository branchRepository;

    Long carTest(){
        Branch branchTest = new Branch();
        branchTest.setName("Test");
        branchTest.setAddress("Av Prueba");
        branchTest.setCity("Buenos Aires");
        branchTest.setPhone("1234567890");
        branchRepository.save(branchTest);

        Car car = new Car();
        car.setBrand("Renault");
        car.setModel("Logan");
        car.setCarYear(2020);
        car.setLicensePlate("HJK-546");
        car.setStatus(StatusCar.AVAILABLE);
        car.setBranch(branchTest);
        carRepository.save(car);

        return car.getCarId();
    }

    CarUpdateDTO updatedCarDTO() {
        CarUpdateDTO dto = new CarUpdateDTO();
        dto.setBrand("Peugeot");
        dto.setModel("208");
        dto.setCarYear(2022);
        dto.setLicensePlate("XYZ-999");
        dto.setStatusCar(StatusCar.RENTAL);
        return dto;
    }

    CarRegisterDTO carRegisterTest() {
        CarRegisterDTO car = new CarRegisterDTO();
        car.setBrand("Bugatti");
        car.setModel("Beiron");
        car.setCarYear(2025);
        car.setLicensePlate("MNO-742");
        car.setStatusCar(StatusCar.RENTAL);
        car.setBranch(1L);

        return car;
    }

    CarRegisterDTO carRepeatTest() {
        CarRegisterDTO car = new CarRegisterDTO();
        car.setBrand("Bugatti");
        car.setModel("Beiron");
        car.setCarYear(2025);
        car.setLicensePlate("123-ABC");
        car.setStatusCar(StatusCar.AVAILABLE);
        car.setBranch(1L);
        return car;
    }

    @Test
    void getTotalCars_returns200AndCarsList() throws Exception {
        mockMvc.perform(get("/admin/search")
            .param("page", "0")
            .param("size", "10")
            .param("sort", "id")
            .param("direction", "des"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content.length()").value(10));
    }

    @Test
    void getTotalCarsByBrandAndModel_returns200AndCarsList() throws Exception {
        mockMvc.perform(get("/admin/search")
            .param("page", "0")
            .param("size", "10")
            .param("sort", "id")
            .param("direction", "des")
            .param("brand", "Toyota")
            .param("model", "Supra"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void getTotalCarsByCarYear_returns200AndCarsList() throws Exception {
        mockMvc.perform(get("/admin/search")
            .param("page", "0")
            .param("size", "10")
            .param("carYear", "2020"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content.length()").value(4));
    }

    @Test
    void getTotalCarsByStatus_returns200AndCarsList() throws Exception {
        mockMvc.perform(get("/admin/search")
            .param("page", "0")
            .param("size", "10")
            .param("statusCar", StatusCar.MAINTENANCE.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void postANewCar_returns201() throws Exception {
        mockMvc.perform(post("/admin/newcar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(carRegisterTest())))
            .andExpect(status().isCreated());
        Long cardId = carRepository.findCarByLicensePlate("MNO-742").get().getCarId();
        carRepository.deleteById(cardId);
    }

    @Test
    void postANewCarWhitRepeatLicensePlate_returns404() throws Exception {
        mockMvc.perform(post("/admin/newcar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(carRepeatTest())))
            .andExpect(status().isNotFound());
    }

    @Test
    void updateACar_returns204() throws Exception {
        Long cardId = carTest();

        mockMvc.perform(put("/admin/car/{id}", cardId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCarDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Peugeot"))
                .andExpect(jsonPath("$.model").value("208"))
                .andExpect(jsonPath("$.licensePlate").value("XYZ-999"));

        carRepository.deleteById(cardId);
        Long branchId = branchRepository.findByPhone("1234567890").get().getBranchId();
        branchRepository.deleteById(branchId);
    }

    @Test
    void deleteCar_returns204() throws Exception {
        Long carId = carTest();

        mockMvc.perform(delete("/admin/car/{id}", carId))
                .andExpect(status().isNoContent());

        Long branchId = branchRepository.findByPhone("1234567890").get().getBranchId();
        branchRepository.deleteById(branchId);
    }



}
