package com.proyectofinal.car.exception;

public class NoCarsFoundByCarYearException extends RuntimeException {
    public NoCarsFoundByCarYearException(String message) {
        super(message);
    }
}
