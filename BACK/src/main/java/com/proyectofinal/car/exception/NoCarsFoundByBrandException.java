package com.proyectofinal.car.exception;

public class NoCarsFoundByBrandException extends RuntimeException {
    public NoCarsFoundByBrandException(String message) {
        super(message);
    }
}
