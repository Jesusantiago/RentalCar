package com.proyectofinal.car.service;

import com.proyectofinal.car.dto.CarBranchDetailsDTO;
import com.proyectofinal.car.dto.CarDetailsDTO;
import com.proyectofinal.car.dto.CarPreviewDTO;
import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.exception.*;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import com.proyectofinal.car.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Page<CarPreviewDTO> findAllByModel(int page, int size, String sortBy, String direction, String model) {
        Pageable pageable  = buildPageable(page, size, sortBy, direction);

        Page<Car> cars = carRepository.findAllByModel(model, pageable);

        if (cars.isEmpty()){
            throw new NoCarsFoundByModelException("Car with model " + model + " not found");
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

    public Page<CarPreviewDTO> findAllByBrand(int page, int size, String sortBy, String direction, String brand) {
        Pageable pageable = buildPageable(page, size, sortBy, direction);

        Page<Car> carsBrand = carRepository.findAllByBrand(brand, pageable);

        if (carsBrand.isEmpty()){
            throw new NoCarsFoundByBrandException("Cars with brand " + brand + " no found");
        }

        return carsBrand.map(car ->
                new CarPreviewDTO(
                        car.getCarId(),
                        car.getModel(),
                        car.getBrand(),
                        car.getBranch().getCity(),
                        car.getBranch().getName()
                )
        );
    }

    public Page<CarPreviewDTO> findAllByCarYear(int page, int size, String sortBy, String direction, int carYear) {
        Pageable pageable = buildPageable(page, size, sortBy, direction);

        Page<Car> carsByCarYear = carRepository.findAllByCarYear(carYear, pageable);

        if (carsByCarYear.isEmpty()){
            throw new NoCarsFoundByCarYearException("Cars not found by year" + carYear);
        }

        return carsByCarYear.map(car ->
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
