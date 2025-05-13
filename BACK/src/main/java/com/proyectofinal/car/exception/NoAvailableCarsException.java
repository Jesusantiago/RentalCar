package com.proyectofinal.car.exception;


public class NoAvailableCarsException extends RuntimeException {

    public NoAvailableCarsException(String message) {
      super(message);
    }
}
