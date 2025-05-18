package com.proyectofinal.car.controller;

import com.proyectofinal.car.dto.car.CarDetailsDTO;
import com.proyectofinal.car.dto.car.CarPreviewDTO;
import com.proyectofinal.car.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/available")
public class CarUserController {

    private final CarService carService;

    public CarUserController(CarService carService) {
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


    @GetMapping("/search")
    public ResponseEntity<Page<CarPreviewDTO>> searchAvailableCarsForUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) Integer carYear
    ){
        Page<CarPreviewDTO> result = carService.searchAvailableCarsForUser(page,size,sortBy,direction,brand,model,branch,carYear);
        return ResponseEntity.ok(result);
    }

}
