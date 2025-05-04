package com.proyectofinal.car.controller;

import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(defaultValue = "brand") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
            ) {
        Page<CarPreviewDTO> availableCars = carService.getAvailableCars(page,size,sortBy,direction);
        return ResponseEntity.ok(availableCars);
    }

}
