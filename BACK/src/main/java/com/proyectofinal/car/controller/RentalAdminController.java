package com.proyectofinal.car.controller;

import com.proyectofinal.car.dto.rental.RentalRequestDTO;
import com.proyectofinal.car.dto.rental.RentalResponseDTO;
import com.proyectofinal.car.model.Rental;
import com.proyectofinal.car.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class RentalAdminController {

    @Autowired
    private RentalService rentalService;

    @GetMapping("/rental/{id}")
    public ResponseEntity<RentalResponseDTO> getRentalById(@PathVariable Long id) {
        try {
            RentalResponseDTO dto = rentalService.getRentalDTOById(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/rental")
    public ResponseEntity<List<RentalResponseDTO>> getAllRentals() {
        List<RentalResponseDTO> rentals = rentalService.getAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @PostMapping("/rental")
    public ResponseEntity<Rental> createRental(@RequestBody RentalRequestDTO request) {
        try {
            Rental rental = rentalService.createRental(request);
            return new ResponseEntity<>(rental, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/rental/{id}")
    public ResponseEntity<Rental> updateRental(
            @PathVariable Long id,
            @RequestBody RentalRequestDTO request) {
        try {
            Rental updatedRental = rentalService.updateRental(id, request);
            return new ResponseEntity<>(updatedRental, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/rental/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        try {
            rentalService.deleteRental(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found si no existe
        }
    }
}

