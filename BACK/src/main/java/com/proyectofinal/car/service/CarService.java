package com.proyectofinal.car.service;

import com.proyectofinal.car.dto.CarBranchDetailsDTO;
import com.proyectofinal.car.dto.CarDetailsDTO;
import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import com.proyectofinal.car.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Page<CarPreviewDTO> getAvailableCars(int page, int size, String sortBy, String direction) {
        Pageable pageable;

        if (sortBy != null && !sortBy.equals("")) {
            Sort sort = direction.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }

        return carRepository.findAllByStatus(StatusCar.AVAILABLE, pageable)
            .map(car ->
                new CarPreviewDTO(
                    car.getCarId(),
                    car.getModel(),
                    car.getBrand(),
                    car.getBranch().getCity(),
                    car.getBranch().getName()
                )
            );


    }

    public CarDetailsDTO getCarById(Long id) {
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) return null;

        Branch branchCar = car.getBranch();
        CarBranchDetailsDTO dataBranchForACar = new CarBranchDetailsDTO(
                branchCar.getBranchId(),
                branchCar.getName(),
                branchCar.getAddress(),
                branchCar.getCity(),
                branchCar.getPhone()
        );
        return new CarDetailsDTO(
                car.getCarId(),
                car.getBrand(),
                car.getModel(),
                car.getCarYear(),
                car.getLicensePlate(),
                dataBranchForACar
        );
    }

}
