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

    // Obtiene todos los autos disponibles (PANTALLA PRINCIPAL)
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

    // Obtiene los datos de un Car buscado por un ID (CAR Details)
    @GetMapping("/{id}")
    public ResponseEntity<CarDetailsDTO> getCarById(@PathVariable("id") Long id) {
        CarDetailsDTO result = carService.getCarById(id);
        return ResponseEntity.ok(result);
    }

    // Obtiene por filtros para Usuario
    @GetMapping("/search")
    public ResponseEntity<Page<CarPreviewDTO>> searchAvailableCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) Integer carYear
    ){
        Page<CarPreviewDTO> result = carService.searchAvailableCars(page,size,sortBy,direction,brand,model,branch,carYear);
        return ResponseEntity.ok(result);
    }

}
