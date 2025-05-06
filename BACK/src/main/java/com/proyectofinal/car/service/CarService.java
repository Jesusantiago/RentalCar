package com.proyectofinal.car.service;

import com.proyectofinal.car.dto.CarBranchDetailsDTO;
import com.proyectofinal.car.dto.CarDetailsDTO;
import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.exception.*;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import com.proyectofinal.car.repository.CarRepository;
import com.proyectofinal.car.util.CarSpecifications;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    private Pageable buildPageable(int page, int size, String sortBy, String direction) {
        if (sortBy != null && !sortBy.isEmpty()) {
            Sort sort = direction.equalsIgnoreCase("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
            return PageRequest.of(page, size, sort);
        }
        return PageRequest.of(page, size);
    }

    public Page<CarPreviewDTO> getAvailableCars(int page, int size, String sortBy, String direction) {
        Pageable pageable = buildPageable(page, size, sortBy, direction);

        Page<Car> cars = carRepository.findAllByStatus(StatusCar.AVAILABLE, pageable);

        if (cars.isEmpty()){
            throw new NoAvailableCarsException("Car not found");
        }

        return cars.map(car ->
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
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car with id " + id + " not found"));
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

    public Page<CarPreviewDTO> searchAvailableCars(
            int page, int size, String sortBy,
            String direction, String brand,
            String model, String branch, Integer carYear) {

        Pageable pageable = buildPageable(page, size, sortBy, direction);

        Specification<Car> spec = CarSpecifications.filterBy(brand, model, branch, carYear, StatusCar.AVAILABLE);

        Page<Car> cars = carRepository.findAll(spec, pageable);

        if (cars.isEmpty()) {
            throw new CarNotFoundException("Cars not found");
        }

        List<CarPreviewDTO> dtoList = cars.getContent().stream()
                .map(car -> new CarPreviewDTO(
                        car.getCarId(),
                        car.getModel(),
                        car.getBrand(),
                        car.getBranch().getCity(),
                        car.getBranch().getName()
                ))
                .toList();

        return new PageImpl<>(dtoList, pageable, cars.getTotalElements());
    }

}
