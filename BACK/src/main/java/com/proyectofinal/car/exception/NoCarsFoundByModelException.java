package com.proyectofinal.car.exception;

public class NoCarsFoundByModelException extends RuntimeException {
    public NoCarsFoundByModelException(String message) {
        super(message);
    }
}
