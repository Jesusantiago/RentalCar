package com.proyectofinal.car.service;

import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.model.Car;
import com.proyectofinal.car.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Page<CarPreviewDTO> getAvailableCars(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Car> availableCars = carRepository.findAllByStatus(StatusCar.AVAILABLE, pageable);

        return availableCars.map(car ->
                new CarPreviewDTO(
                        car.getCarId(),
                        car.getModel(),
                        car.getBrand(),
                        car.getBranch().getCity(),
                        car.getBranch().getName()
                )
        );
    }

}
