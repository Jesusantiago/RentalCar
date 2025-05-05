package com.proyectofinal.car.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<String> handleCarNotFound(CarNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NoAvailableCarsException.class)
    public ResponseEntity<String> handleNoAvailableCars(NoAvailableCarsException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NoCarsFoundByModelException.class)
    public ResponseEntity<String> handleNofoundCarsByModel(NoCarsFoundByModelException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NoCarsFoundByBrandException.class)
    public ResponseEntity<String> handleNotFoundCarsByBrand(NoCarsFoundByBrandException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NoCarsFoundByCarYearException.class)
    public ResponseEntity<String> handleNotFoundCarsByCarYear(NoCarsFoundByCarYearException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
