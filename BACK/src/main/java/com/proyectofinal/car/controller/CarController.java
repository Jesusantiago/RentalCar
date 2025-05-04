package com.proyectofinal.car.controller;

import com.proyectofinal.car.dto.CarDetailsDTO;
import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/available")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<Page<CarPreviewDTO>> getAvailableCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String direction
            ) {
        Page<CarPreviewDTO> availableCars = carService.getAvailableCars(page,size,sortBy,direction);
        return ResponseEntity.ok(availableCars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDetailsDTO> getCarById(@PathVariable("id") Long id) {
        CarDetailsDTO result = carService.getCarById(id);
        return ResponseEntity.ok(result);
    }

}
