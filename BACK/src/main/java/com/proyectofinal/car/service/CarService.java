package com.proyectofinal.car.service;

import com.proyectofinal.car.dto.*;
import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.exception.*;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import com.proyectofinal.car.repository.BranchRepository;
import com.proyectofinal.car.repository.CarRepository;
import com.proyectofinal.car.util.CarSpecifications;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final BranchRepository branchRepository;

    public CarService(CarRepository carRepository, BranchRepository branchRepository) {
        this.carRepository = carRepository;
        this.branchRepository = branchRepository;
    }

    private Pageable buildPageable(int page, int size, String sortBy, String direction) {
        if (sortBy != null && !sortBy.isEmpty()) {
            String safeDirection = (direction != null) ? direction : "asc";
            Sort sort = safeDirection.equalsIgnoreCase("desc")
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

    public Page<CarPreviewDTO> searchAvailableCarsForUser(
            int page, int size, String sortBy,
            String direction, String brand,
            String model, String branch, Integer carYear) {

        Long client_id = null;
        Pageable pageable = buildPageable(page, size, sortBy, direction);

        Specification<Car> spec = CarSpecifications.filterBy(brand, model, branch, carYear, StatusCar.AVAILABLE, client_id);

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


    // Method for Admin

    public Page<CarPreviewDTO> searchAvailableCarsForAdmin(
            int page, int size, String sortBy, String direction, String brand,
            String model, String branch, Integer carYear, StatusCar statusCar, Long client_id) {

        Pageable pageable = buildPageable(page, size, sortBy, direction);

        Specification<Car> spec = CarSpecifications.filterBy(brand, model, branch, carYear, statusCar, client_id);

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

    public void seachCarByLicensePlate(String licensePlate) {
        if (carRepository.findCarByLicensePlate(licensePlate).isPresent()) {
            throw new CarNotFoundException("Car with license plate " + licensePlate + " not found");
        }
    }

    public CarRegisterDTO createCarFromDTO(CarRegisterDTO carDTO) {
        seachCarByLicensePlate(carDTO.getLicensePlate());

        Branch branch = branchRepository.findById(carDTO.getBranch())
                .orElseThrow(() -> new NoCarsFoundByBrandException("No branch found"));


        Car newCar = new Car();
        newCar.setBrand(carDTO.getBrand());
        newCar.setModel(carDTO.getModel());
        newCar.setLicensePlate(carDTO.getLicensePlate());
        newCar.setCarYear(carDTO.getCarYear());
        newCar.setStatus(carDTO.getStatusCar());
        newCar.setBranch(branch);

        Car savedCar = carRepository.save(newCar);

        CarRegisterDTO responseDto = new CarRegisterDTO();
        responseDto.setBrand(savedCar.getBrand());
        responseDto.setModel(savedCar.getModel());
        responseDto.setCarYear(savedCar.getCarYear());
        responseDto.setLicensePlate(savedCar.getLicensePlate());
        responseDto.setStatusCar(savedCar.getStatus());
        responseDto.setBranch(savedCar.getBranch().getBranchId());

        return responseDto;
    }

    public CarDetailsDTO updateACar(Long id, CarUpdateDTO carUpdate) {
        Optional<Car> car = carRepository.findById(id);

        if(car.isEmpty()) {
            throw new CarNotFoundException("Car with id " + id + " not found");
        }

        Car existingCar = car.get();

        if (carUpdate.getBrand() != null) {
            existingCar.setBrand(carUpdate.getBrand());
        }

        if (carUpdate.getModel() != null) {
            existingCar.setModel(carUpdate.getModel());
        }

        if (carUpdate.getCarYear() != null) {
            existingCar.setCarYear(carUpdate.getCarYear());
        }

        if (carUpdate.getLicensePlate() != null) {
            existingCar.setLicensePlate(carUpdate.getLicensePlate());
        }

        if (carUpdate.getBranchId() != null) {
            Branch branch = branchRepository.findById(carUpdate.getBranchId())
                            .orElseThrow( () -> new BranchNotFoundException("Branch not found"));
            existingCar.setBranch(branch);
        }

        if (carUpdate.getStatusCar() != null) {
            existingCar.setStatus(carUpdate.getStatusCar());
        }

        carRepository.save(existingCar);

        CarBranchDetailsDTO branchupdate = new CarBranchDetailsDTO(
                existingCar.getBranch().getBranchId(),
                existingCar.getBranch().getName(),
                existingCar.getBranch().getAddress(),
                existingCar.getBranch().getCity(),
                existingCar.getBranch().getPhone()
        );

        CarDetailsDTO carAlreadyUpdate = new CarDetailsDTO();
        carAlreadyUpdate.setBrand(existingCar.getBrand());
        carAlreadyUpdate.setModel(existingCar.getModel());
        carAlreadyUpdate.setCarYear(existingCar.getCarYear());
        carAlreadyUpdate.setLicensePlate(existingCar.getLicensePlate());
        carAlreadyUpdate.setBranch(branchupdate);

        return carAlreadyUpdate;
    }

    public void deleteCar(Long id) {
        Optional<Car> car = carRepository.findById(id);
        if(car.isEmpty()) {
            throw new CarNotFoundException("Car with id " + id + " not found");
        }

        carRepository.delete(car.get());
    }
}
