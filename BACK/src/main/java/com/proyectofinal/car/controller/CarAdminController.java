package com.proyectofinal.car.controller;

import com.proyectofinal.car.dto.CarDetailsDTO;
import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.dto.CarRegisterDTO;
import com.proyectofinal.car.model.Car;
import com.proyectofinal.car.repository.CarRepository;
import com.proyectofinal.car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class CarAdminController {

    @Autowired
    private CarService carService;
    @Autowired
    private CarRepository carRepository;

    // Obtiene por filtros para Usuario
    @GetMapping("/search")
    public ResponseEntity<Page<CarPreviewDTO>> searchAvailableCarsForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) Integer carYear,
            @RequestParam(required = false) Long client_id
    ){
        Page<CarPreviewDTO> result = carService.searchAvailableCarsForAdmin(page,size,sortBy,direction,brand,model,branch,carYear, client_id);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/newcar")
    public ResponseEntity<?> createANewCar(@RequestBody CarRegisterDTO car, @Valid BindingResult result) {

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }

        CarRegisterDTO createdCar = carService.createCarFromDTO(car);


        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
    }
}
